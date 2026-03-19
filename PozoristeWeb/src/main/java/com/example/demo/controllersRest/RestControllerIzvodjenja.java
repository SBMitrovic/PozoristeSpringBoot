package com.example.demo.controllersRest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.IzvodjenjeDTO;
import com.example.demo.services.RestService;

@RestController
@RequestMapping("/api/restIzvodjenje/")
public class RestControllerIzvodjenja {
	
	@Autowired
	RestService service;
	
	@GetMapping("izvestajIzvodjenja")
	public ResponseEntity<?> getIzvestaj(String nazivScene, String datum){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false); //stroga provera da uneti string bude u formatu yyyy-MM-dd
        Date noviDatum;
        try {
        	noviDatum = dateFormat.parse(datum);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Neispravan format datuma");
        }
        try {
        	byte[] izvestaj = service.kreirajIzvestaj(nazivScene, noviDatum);
        	if (izvestaj == null) { //u slucaju da nema izvodjenja za unete parametre
        		return ResponseEntity.ok("Nema izvodjenja za unete parametre.");
        	}
        	HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.parseMediaType("application/pdf"));
	        headers.setContentDispositionFormData("attachment", "izvestajIzvodjenja.pdf");
	        return ResponseEntity.ok()
	            .headers(headers)
	            .body(izvestaj);
        } catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška prilikom generisanja izveštaja.");
        }
	}
	
	@PostMapping("saveIzvodjenje")
	public ResponseEntity<?> saveIzvodjenje(@RequestBody IzvodjenjeDTO izvodjenje){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false); //stroga provera da uneti string bude u formatu yyyy-MM-dd
        Date noviDatum;
        try {
        	noviDatum = dateFormat.parse(izvodjenje.getDatum());
        	Integer idIzvodjenje = service.saveIzvodjenje(izvodjenje, noviDatum);
        	if (idIzvodjenje == -1)
        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška pri čuvanju izvođenja!");
        	return ResponseEntity.ok("Uspešno sačuvano izvođenje! ID izvođenja: "+idIzvodjenje);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body("Neispravan format datuma");
        }
	}
	
	
	
}
