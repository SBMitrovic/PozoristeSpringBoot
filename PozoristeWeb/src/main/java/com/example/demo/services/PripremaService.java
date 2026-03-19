package com.example.demo.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GlumacDTO;
import com.example.demo.dto.PredstavaDTO;
import com.example.demo.repositories.GlumacRepository;
import com.example.demo.repositories.IzvodjenjeRepository;
import com.example.demo.repositories.PredstavaRepository;
import com.example.demo.repositories.ZanrRepository;

import model.Glumac;
import model.Predstava;
import model.Zanr;
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
public class PripremaService {
	
	@Autowired
	PredstavaRepository pr;
	
	@Autowired
	GlumacRepository gr;
	
	@Autowired
	ZanrRepository zr;
	
	@Autowired
	IzvodjenjeRepository ir;
	
	public List<PredstavaDTO> getPredstave(){
		List<Predstava> predstave = pr.findAll();
		List<PredstavaDTO> predstaveDTO = new LinkedList<>();
		for (Predstava p: predstave) {
			PredstavaDTO dto = new PredstavaDTO();
			BeanUtils.copyProperties(p, dto);
			predstaveDTO.add(dto);
		}
		return predstaveDTO;
	}
	
	public int saveGlumac(GlumacDTO glumac) {
		Glumac noviGlumac = new Glumac();
		noviGlumac.setIme(glumac.getIme());
		noviGlumac.setPrezime(glumac.getPrezime());
		noviGlumac.setJmbg(glumac.getJmbg());
		try {
			Glumac g = gr.save(noviGlumac);
			return g.getIdGlumac();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}	
	}
	
	public boolean existZanr(String zanr) {
		Zanr z = zr.findByNaziv(zanr);
		if (z != null)
			return true;
		return false;
	}

	public byte[] kreirajIzvestaj(String zanr) throws JRException, IOException {
		System.out.println("Broj izvodjenja: " + ir.getIzvodjenjaZanra(zanr).size());
		if (ir.getIzvodjenjaZanra(zanr).size() > 0) {
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ir.getIzvodjenjaZanra(zanr));
			InputStream inputStream = this.getClass().getResourceAsStream("/jasperreports/izvodjenjaZanra.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("zanr", zanr);
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
}
