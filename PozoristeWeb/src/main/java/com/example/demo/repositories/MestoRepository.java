package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Mesto;

public interface MestoRepository extends JpaRepository<Mesto, Integer> {

}
