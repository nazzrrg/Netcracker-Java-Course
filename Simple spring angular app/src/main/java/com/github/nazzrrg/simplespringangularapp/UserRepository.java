package com.github.nazzrrg.simplespringangularapp;

import com.github.nazzrrg.simplespringangularapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
