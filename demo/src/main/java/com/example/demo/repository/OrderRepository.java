package com.example.demo.repository;

import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderItem, Long> {


    List<OrderItem> findByUser(User user);
}
