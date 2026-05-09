package com.yourname.bookstore.repository;

import com.yourname.bookstore.entity.Order;
import com.yourname.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
