package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Scena;

public interface ScenaRepository extends JpaRepository<Scena, Integer>{

	Scena findByNaziv(String naziv);
}
