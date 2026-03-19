package com.example.demo.dto;

public class PredstavaDTO {

	private String naziv;
	private Integer trajanje;
	private String opis;
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Integer getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(Integer trajanje) {
		this.trajanje = trajanje;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
}
