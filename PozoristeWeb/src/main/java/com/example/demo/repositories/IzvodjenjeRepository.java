package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Izvodjenje;

public interface IzvodjenjeRepository extends JpaRepository<Izvodjenje, Integer> {
	
	@Query("select count(i.idIzvodjenje) from Izvodjenje i where i.predstava.idPredstava =:id")
	Integer brojIzvodjenja(@Param("id")Integer idPredstava);
	
	
	@Query("select i from Izvodjenje i where i.scena.naziv =:naziv and i.datum >= :datum order by i.predstava.reziser.prezime")
	List<Izvodjenje> getIzvodjenja(@Param("naziv")String naziv, @Param("datum")Date d);
	
	@Query("select i from Izvodjenje i inner join i.predstava.zanrPredstaves zp "
			+ "where zp.zanr.naziv =:z and zp.predstava = i.predstava order by i.scena.idScena")
	List<Izvodjenje> getIzvodjenjaZanra(@Param("z") String zanr);
}
