package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Predstava;

public interface PredstavaRepository extends JpaRepository<Predstava, Integer>{
	
	//List<Predstava> findByZanr(String zanr);
}
