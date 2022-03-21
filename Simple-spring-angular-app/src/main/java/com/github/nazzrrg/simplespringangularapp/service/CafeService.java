package com.github.nazzrrg.simplespringangularapp.service;

import com.github.nazzrrg.simplespringangularapp.model.Cafe;
import com.github.nazzrrg.simplespringangularapp.repo.CafeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CafeService {
    private final CafeRepository repository;

    public CafeService(CafeRepository repository) {
        this.repository = repository;
    }

    public boolean create(@RequestBody Cafe cafe) {
        if (!repository.existsById(cafe.getId())) {
            repository.save(cafe);
            return true;
        }
        return false;
    }
    public List<Cafe> getAll() {
        return (List<Cafe>) repository.findAll();
    }
    public Cafe getById(long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }
    public void delete(long id) {
        repository.deleteById(id);
    }
}
