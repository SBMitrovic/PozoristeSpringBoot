package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.Predstava;
import model.Zanr;
import model.ZanrPredstave;

public interface ZanrPredstaveRepo  extends JpaRepository<ZanrPredstave	, Integer>{
	
	@Query("SELECT zp FROM ZanrPredstave zp WHERE zp.predstava.idPredstava = :predID")
	ZanrPredstave pretragaPoPredstavi(@Param("predID")Integer p);
	
	
	@Query("SELECT zp FROM ZanrPredstave zp WHERE zp.zanr.idZanr = :idZanra")
	List<ZanrPredstave> pretragaPoZanru(@Param("idZanra") Integer z); 
}
