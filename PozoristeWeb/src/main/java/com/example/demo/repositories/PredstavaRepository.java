package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Predstava;
import model.Reziser;

public interface PredstavaRepository extends JpaRepository<Predstava, Integer> {

	@Query("select p from Predstava p where p.reziser.idReziser =:idR")
	List<Predstava> findByReziser(@Param("idR")Integer id);
	
	Predstava findByNaziv(String naziv);
	
	List<Predstava> findByReziser(Reziser r);
}
