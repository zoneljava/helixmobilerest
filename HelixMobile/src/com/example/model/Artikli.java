package com.example.model;

public class Artikli {
	
	private String sifri;
	private String naziv;
	private Float kolicina;
	private String em;
	private String tarifa;
	private Float procent;
	private Float cena;
	private Float virt_kolicina;
	private Float virt_cena;
	private String virt_em;
	private Integer stranski;
	private Float nab_cena;
	private Integer id;
	private String tip1;
	private String opis;
	
	
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public String getTip1() {
		return tip1;
	}
	public void setTip1(String tip1) {
		this.tip1 = tip1;
	}
	public String getSifri() {
		return sifri;
	}
	public void setSifri(String sifri) {
		this.sifri = sifri;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Float getKolicina() {
		return kolicina;
	}
	public void setKolicina(Float kolicina) {
		this.kolicina = kolicina;
	}
	public String getEm() {
		return em;
	}
	public void setEm(String em) {
		this.em = em;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public Float getProcent() {
		return procent;
	}
	public void setProcent(Float procent) {
		this.procent = procent;
	}
	public Float getCena() {
		return cena;
	}
	public void setCena(Float cena) {
		this.cena = cena;
	}
	public Float getVirt_kolicina() {
		return virt_kolicina;
	}
	public void setVirt_kolicina(Float virt_kolicina) {
		this.virt_kolicina = virt_kolicina;
	}
	public Float getVirt_cena() {
		return virt_cena;
	}
	public void setVirt_cena(Float virt_cena) {
		this.virt_cena = virt_cena;
	}
	public String getVirt_em() {
		return virt_em;
	}
	public void setVirt_em(String virt_em) {
		this.virt_em = virt_em;
	}
	public Integer getStranski() {
		return stranski;
	}
	public void setStranski(Integer stranski) {
		this.stranski = stranski;
	}
	public Float getNab_cena() {
		return nab_cena;
	}
	public void setNab_cena(Float nab_cena) {
		this.nab_cena = nab_cena;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return sifri + " " + naziv + " " + kolicina +" " + cena + "";
	}
	
	
	
}
