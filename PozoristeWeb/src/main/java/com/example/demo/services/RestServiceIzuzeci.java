package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MestoDTO;
import com.example.demo.dto.PredstavaDTO;
import com.example.demo.repositories.MestoRepository;
import com.example.demo.repositories.PredstavaRepository;
import com.example.demo.repositories.ReziserRepository;
import com.example.demo.repositories.ScenaRepository;

import model.Mesto;
import model.Predstava;
import model.Reziser;

@Service
public class RestServiceIzuzeci {

	@Autowired
	PredstavaRepository pr;

	@Autowired
	ReziserRepository rr;

	@Autowired
	ScenaRepository sr;
	
	@Autowired
	MestoRepository mr;
	
	public Reziser getReziser(String ime, String prezime) {
		if (rr.findByImeAndPrezime(ime, prezime) == null)
			return null;
		return rr.findByImeAndPrezime(ime, prezime);
	}

	public List<PredstavaDTO> getPredstave(String ime, String prezime) {
		List<Predstava> predstave = pr.findByReziser(rr.findByImeAndPrezime(ime, prezime));
		if (predstave.size() == 0)
			return null;
		List<PredstavaDTO> dtoPredstave = new ArrayList<>();
		for (Predstava p : predstave) {
			PredstavaDTO dto = new PredstavaDTO();
			BeanUtils.copyProperties(p, dto);
			dtoPredstave.add(dto);
		}
		return dtoPredstave;
	}
	
	public boolean getScena(String naziv) {
		if (sr.findByNaziv(naziv) == null)
			return false;
		return true;
	}
	
	public Integer saveMesto(MestoDTO dto) {
		Mesto novoMesto = new Mesto();
		novoMesto.setScena(sr.findByNaziv(dto.getNazivScene()));
		novoMesto.setSekcija(dto.getSekcija());
		novoMesto.setRed(dto.getRed());
		novoMesto.setBroj(dto.getBroj());
		novoMesto = mr.save(novoMesto);
		return novoMesto.getIdMesto();
	}
}
