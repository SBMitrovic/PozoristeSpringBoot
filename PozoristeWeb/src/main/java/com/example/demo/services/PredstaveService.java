package com.example.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.IzvodjenjeRepository;
import com.example.demo.repositories.KartaRepository;
import com.example.demo.repositories.PredstavaRepository;
import com.example.demo.repositories.ReziserRepository;

import model.Predstava;
import model.Reziser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PredstaveService {
	
	@Autowired
	ReziserRepository rr;

	@Autowired
	PredstavaRepository pr;
	
	@Autowired
	KartaRepository kr;
	
	@Autowired
	IzvodjenjeRepository ir;
	
	public List<Reziser> getReziseri(){
		return rr.findAll();
	}
	
	public List<Predstava> getPredstave(Integer id){
		return pr.findByReziser(id);
	}
	
	public Reziser getReziser(Integer id){
		return rr.findById(id).get();
	}
	
	public String getNazivPredstave(Integer idP) {
		return pr.findById(idP).get().getNaziv();
	}
	
	public JasperPrint kreirajIzvestaj(Integer idPredstava) throws JRException, IOException {
		System.out.println("IdPredstave: "+idPredstava);
		System.out.println("Broj karata: "+kr.getKarteZaPredstavu(idPredstava).size());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(kr.getKarteZaPredstavu(idPredstava));
		InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/karteZaPredstavu.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("predstava", pr.findById(idPredstava).get().getNaziv());
		params.put("brojIzvodjenja", ir.brojIzvodjenja(idPredstava));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		inputStream.close();
		return jasperPrint;
	}

}
