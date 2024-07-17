package com.cloudNext2024.cloudNext2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudNext2024.cloudNext2024.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
