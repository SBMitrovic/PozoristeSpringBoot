package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Reziser;

public interface ReziserRepository extends JpaRepository<Reziser, Integer>{

	Reziser findByImeAndPrezime(String ime, String prezime);
}
