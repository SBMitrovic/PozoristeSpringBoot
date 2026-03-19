package com.example.demo.controllers;

import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.PredstaveService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/predstave/")
public class PredstaveController {

	@Autowired
	PredstaveService service;
	
	@GetMapping("getReziseri")
	public String getZanrovi(HttpServletRequest request) {
		request.getSession().removeAttribute("reziser");
		request.getSession().removeAttribute("predstave");
		request.getSession().setAttribute("reziseri", service.getReziseri());
		return "prikazPredstava";
	}
	
	@GetMapping("getPredstave")
	public String getPredstave(@RequestParam("idReziser")Integer id, HttpServletRequest request) {
		request.getSession().setAttribute("predstave", service.getPredstave(id));
		request.getSession().setAttribute("reziser", service.getReziser(id));
		return "prikazPredstava";
	}
	
	@GetMapping("getIzvestaj")
	public void getIzvestaj(@RequestParam("idP")Integer idPredstava, HttpServletResponse response, Model m) {
		try {
			JasperPrint jasperReport = service.kreirajIzvestaj(idPredstava);
			response.setContentType("text/html");
			response.setContentType("application/x-download");
			response.addHeader("Content-disposition", "attachment; filename=karteZaPredstavu"+service.getNazivPredstave(idPredstava)+".pdf");
			OutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperReport, out);
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
