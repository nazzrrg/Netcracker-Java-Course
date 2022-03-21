package com.github.nazzrrg.simplespringangularapp.repo;

import com.github.nazzrrg.simplespringangularapp.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findById(Long id);
}
