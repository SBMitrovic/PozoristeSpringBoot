package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Ocena;

public interface OcenaRepository  extends JpaRepository<Ocena, Integer>{
	
	@Query("SELECT o FROM Ocena o WHERE o.ocena > 3 AND o.posetilac.idPosetilac = :idPos")
	public List<Ocena> izvodjenjaSaOcjenomVecomOdTri(@Param("idPos") Integer idPos);
}
