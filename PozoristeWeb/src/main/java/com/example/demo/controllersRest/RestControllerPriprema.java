package com.example.demo.controllersRest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GlumacDTO;
import com.example.demo.exceptions.EmptyFieldsException;
import com.example.demo.exceptions.InvalidLengthException;
import com.example.demo.exceptions.NotfoundException;
import com.example.demo.exceptions.SavingErrorException;
import com.example.demo.services.PripremaService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("api/priprema/")
public class RestControllerPriprema {
	
	@Autowired
	PripremaService service;

	//Zadatak a)
	@GetMapping("getPredstave")
	public ResponseEntity<?> getPredstave(){
		return ResponseEntity.status(HttpStatus.OK).body(service.getPredstave());
	}
	
	//Zadatak b)
	@PostMapping("saveGlumac")
	public ResponseEntity<?> saveGlumac(GlumacDTO glumac){
		if (glumac.getIme() == "" || glumac.getPrezime() == "" || glumac.getJmbg() == "") {
			throw new EmptyFieldsException("Nijedno polje ne sme biti prazno.");
		}
		if (glumac.getJmbg().length() != 13)
			throw new InvalidLengthException("Nevalidna dužina atributa JMBG.\nDužina JMBG-a mora biti 13.", glumac.getJmbg().length());
		Integer idNovogGlumca = service.saveGlumac(glumac);
		if (idNovogGlumca == -1)
			throw new SavingErrorException("Greška prilikom čuvanja glumca u bazu.");
		return ResponseEntity.ok("Uspešno sačuvan novi glumac! Id glumca je: "+idNovogGlumca);
		
	}
	
	//Zadatak c)
	@GetMapping("izvestajIzvodjenja")
	public ResponseEntity<?> getReport(String zanr) throws JRException, IOException {
		if (!service.existZanr(zanr))
			throw new NotfoundException("Ne postoji u bazi uneti zanr. ", zanr);
        	byte[] izvestaj = service.kreirajIzvestaj(zanr);
        	if (izvestaj == null) { 
        		throw new NotfoundException("Nema nijedno izvodjenje za uneti zanr.", zanr);
        	}
        	HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.parseMediaType("application/pdf"));
	        headers.setContentDispositionFormData("attachment", "izvodjenjaZaZanr.pdf");
	        return ResponseEntity.ok()
	            .headers(headers)
	            .body(izvestaj);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleNotFoundZException(RuntimeException e) {
		return ResponseEntity.badRequest().body("Greška: " + e.getMessage());
	}
}
