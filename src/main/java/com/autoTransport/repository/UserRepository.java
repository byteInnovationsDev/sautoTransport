package com.autoTransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autoTransport.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserId(String userId);
}

