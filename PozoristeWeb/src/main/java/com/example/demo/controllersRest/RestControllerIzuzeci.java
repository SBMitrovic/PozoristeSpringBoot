package com.example.demo.controllersRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MestoDTO;
import com.example.demo.dto.PredstavaDTO;
import com.example.demo.exceptions.EmptyFieldsException;
import com.example.demo.exceptions.NegativeNumberException;
import com.example.demo.exceptions.NotfoundException;
import com.example.demo.services.RestServiceIzuzeci;

@RestController
@RequestMapping("/api/pv10/")
public class RestControllerIzuzeci {

	@Autowired
	RestServiceIzuzeci service;
	
	@GetMapping("getPredstave")
	public ResponseEntity<List<PredstavaDTO>> getPredstave(String ime, String prezime){
		if (service.getReziser(ime, prezime) == null)
			throw new NotfoundException("Režiser nije pronađen u bazi!", "Ime: "+ime+"; Prezime: "+prezime);
		if (service.getPredstave(ime, prezime) == null)
			throw new NotfoundException("Režiser nema nijednu predstavu.", "Ime: "+ime+"; Prezime: "+prezime);
		return ResponseEntity.ok(service.getPredstave(ime, prezime));
	}
	
	@PostMapping("saveMesto")
	public ResponseEntity<String> saveMesto(MestoDTO mestoDTO){
		if (mestoDTO.getSekcija() == "" || mestoDTO.getNazivScene() == "")
			throw new EmptyFieldsException("Sekcija i naziv scene ne smeju biti prazni!");
		if (mestoDTO.getBroj() <= 0 || mestoDTO.getRed() <= 0)
			throw new NegativeNumberException("Mesto i red moraju biti pozitivni brojevi!");
		if (!service.getScena(mestoDTO.getNazivScene()))
			throw new NotfoundException("Ne postoji scena sa unetim nazivom.", mestoDTO.getNazivScene());
		return ResponseEntity.status(HttpStatus.OK).body("Novo mesto je uspešno sačuvano! ID mesta je: "+service.saveMesto(mestoDTO));
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleNotFoundZException(RuntimeException e) {
		return ResponseEntity.badRequest().body("Greška: " + e.getMessage());
	}
}
