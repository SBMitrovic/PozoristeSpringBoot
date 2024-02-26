package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Posetilac;

public interface PosjetiocRepository extends JpaRepository<Posetilac, Integer>{

	
	Posetilac findByIme(String ime);

}
