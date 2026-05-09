package com.yourname.bookstore.service;

import com.yourname.bookstore.entity.Book;
import com.yourname.bookstore.entity.Order;
import com.yourname.bookstore.entity.OrderItem;
import com.yourname.bookstore.entity.User;
import com.yourname.bookstore.repository.BookRepository;
import com.yourname.bookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Order placeOrder(User user, Long bookId, Integer quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        // Update book quantity
        book.setQuantity(book.getQuantity() - quantity);
        bookRepository.save(book);

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status("COMPLETED")
                .totalAmount(book.getPrice() * quantity)
                .build();

        OrderItem item = OrderItem.builder()
                .order(order)
                .book(book)
                .quantity(quantity)
                .price(book.getPrice())
                .build();

        List<OrderItem> items = new ArrayList<>();
        items.add(item);
        order.setItems(items);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
