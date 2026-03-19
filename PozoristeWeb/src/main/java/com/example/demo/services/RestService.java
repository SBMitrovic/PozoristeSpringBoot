package com.example.demo.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.IzvodjenjeDTO;
import com.example.demo.repositories.IzvodjenjeRepository;
import com.example.demo.repositories.PredstavaRepository;
import com.example.demo.repositories.ScenaRepository;

import model.Izvodjenje;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class RestService {

	@Autowired
	IzvodjenjeRepository ir;
	
	@Autowired
	ScenaRepository sr;
	
	@Autowired
	PredstavaRepository pr;

	public byte[] kreirajIzvestaj(String naziv, Date datum) throws JRException, IOException {
		System.out.println("Broj izvodjenja: " + ir.getIzvodjenja(naziv, datum).size());
		if (ir.getIzvodjenja(naziv, datum).size() > 0) {
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ir.getIzvodjenja(naziv, datum));
			InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/izvestajIzvodjenja.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("datumP", datum);
			params.put("scena", naziv);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
			inputStream.close();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
			exporter.exportReport();
			return byteArrayOutputStream.toByteArray();
		}
		return null;
	}
	
	public Integer saveIzvodjenje(IzvodjenjeDTO i, Date datum) {
		try {
		Izvodjenje novoIzvodjenje = new Izvodjenje();
		novoIzvodjenje.setDatum(datum);
		novoIzvodjenje.setScena(sr.findByNaziv(i.getNazivScene()));
		novoIzvodjenje.setPredstava(pr.findByNaziv(i.getNazivPredstave()));
		Izvodjenje sacuvano = ir.save(novoIzvodjenje);
		return sacuvano.getIdIzvodjenje();
		} catch(Exception e) {
			return -1;
		}
		
	}
}
