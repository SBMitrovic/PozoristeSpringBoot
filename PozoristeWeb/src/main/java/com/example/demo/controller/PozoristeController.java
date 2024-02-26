package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.repository.OcenaRepository;
import com.example.demo.repository.PosjetiocRepository;
import com.example.demo.repository.PredstavaRepository;
import com.example.demo.repository.ZanrPredstaveRepo;
import com.example.demo.repository.ZanrRepository;

import model.Ocena;
import model.Posetilac;
import model.Predstava;
import model.Zanr;
import model.ZanrPredstave;

@Controller
@RequestMapping(value = "/controller")
public class PozoristeController {

	@Autowired
	PosjetiocRepository pRepo;

	@Autowired
	OcenaRepository oRepo;

	@Autowired
	PredstavaRepository predstavaRepo;

	@Autowired
	ZanrRepository zanrRepo;

	@Autowired
	ZanrPredstaveRepo zanrPredstaveRepo;

	@RequestMapping(value = "/getPosjetioci", method = RequestMethod.GET)
	public String getPosjetioci(HttpServletRequest request) {

		List<Posetilac> lista = pRepo.findAll();

		request.getSession().setAttribute("posjetioci", lista);
		return "prikaz/posjetioci";
	}

	@RequestMapping(value = "/getIzvodjenjaZaPosjetioca", method = RequestMethod.GET)
	public String getIzvodjenjaZaPosjetioca(HttpServletRequest request, String idPosjetioca) {
		Integer id = Integer.parseInt(idPosjetioca);
		List<Ocena> listaOcjena = oRepo.izvodjenjaSaOcjenomVecomOdTri(id);
		System.out.println("************************************************");
		if (listaOcjena == null) {
			System.out.println("lista je prazna");
		}
		System.out.println("************************************************");
		request.getSession().setAttribute("listaOcjena", listaOcjena);

		return "prikaz/posjetioci";
	}

	@ModelAttribute("posetilac")
	public Posetilac save() {
		return new Posetilac();
	}

	// KOMPLIKOVANIJE FUNKCIONALNOSTI I PRIMJERI SA @ModelAttribute anotacijom

	@RequestMapping(value = "/getPredstave", method = RequestMethod.GET)
	public String getPredstaveiZanrove(HttpServletRequest request) {
		List<Predstava> predstave = predstavaRepo.findAll();

		request.getSession().setAttribute("predstave", predstave);

		return "prikaz/predstave";
	}

	@RequestMapping(value = "/getZanrZaPredstavu", method = RequestMethod.GET)
	public String getPredstaveiZanrove(HttpServletRequest request, String idPredstave) {
		Integer id = Integer.parseInt(idPredstave);
		ZanrPredstave zanr = zanrPredstaveRepo.pretragaPoPredstavi(id);

		request.getSession().setAttribute("zanr", zanr);

		return "prikaz/predstave";
	}

	@RequestMapping(value="/getZanrovi", method = RequestMethod.GET)
	public String getZanrovi (HttpServletRequest request) {
		List<Zanr> zanrovi = zanrRepo.findAll();
		
		
		request.getSession().setAttribute("zanrovi",zanrovi);
		
		return "prikaz/zanrovi";
	}
	
	@RequestMapping(value="/getPredstavePoZanru", method = RequestMethod.GET)
	public String getPredstavePoZanru (HttpServletRequest request,String zanrID) {
		Integer id = Integer.parseInt(zanrID);
		List<ZanrPredstave> listaZP = zanrPredstaveRepo.pretragaPoZanru(id);
		
		request.getSession().setAttribute("listaZanrPredstava", listaZP);
		return "prikaz/zanrovi";
	}
	
	
/*
 * @RequestMapping(value="/predstavePoZanru", method = RequestMethod.GET) public
 * String getPredstavePoZanru (HttpServletRequest request,String zanr) {
 * List<Predstava> predstave = predstavaRepo.findByZanr(zanr);
 * 
 * request.getSession().setAttribute("predstave", predstave);
 * 
 * 
 * return "prikaz/pretragaPredstava"; }
 * 
 */
}