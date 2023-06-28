package com.bhuban.user.service.repositories;

import com.bhuban.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>  {
}
