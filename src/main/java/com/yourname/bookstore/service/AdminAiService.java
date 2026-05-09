package com.yourname.bookstore.service;

import com.yourname.bookstore.repository.BookRepository;
import com.yourname.bookstore.repository.OrderRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdminAiService {

    private final ChatClient chatClient;

    public AdminAiService(ChatClient.Builder builder) {
        // We configure the ChatClient with a system prompt to act as a data analyst
        this.chatClient = builder
                .defaultSystem("You are a helpful Data Analyst for the Bookstore. " +
                        "You have access to the bookstore's database via functions. " +
                        "Use these functions to answer administrative questions about sales, inventory, and orders. " +
                        "If you don't have a function for a specific request, inform the user about what you can do.")
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                // .functions("getInventoryStats", "getOrderStats") // Register functions here
                .call()
                .content();
    }

    @Configuration
    static class AiFunctions {

        @Bean
        @Description("Get statistics about the book inventory, including total books and low stock items")
        public Function<InventoryRequest, String> getInventoryStats(BookRepository bookRepository) {
            return request -> {
                long total = bookRepository.count();
                List<String> lowStock = bookRepository.findAll().stream()
                        .filter(b -> b.getQuantity() < 5)
                        .map(b -> b.getTitle() + " (" + b.getQuantity() + " left)")
                        .collect(Collectors.toList());

                return String.format("Total book titles: %d. Low stock items: %s", total, lowStock);
            };
        }

        @Bean
        @Description("Get statistics about orders and total revenue")
        public Function<OrderRequest, String> getOrderStats(OrderRepository orderRepository) {
            return request -> {
                long count = orderRepository.count();
                double revenue = orderRepository.findAll().stream()
                        .mapToDouble(o -> o.getTotalAmount())
                        .sum();
                return String.format("Total orders placed: %d. Total revenue: $%.2f", count, revenue);
            };
        }
    }

    // Request records for functions
    public record InventoryRequest(String query) {
    }

    public record OrderRequest(String query) {
    }
}
