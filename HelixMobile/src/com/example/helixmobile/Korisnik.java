package com.example.helixmobile;

public class Korisnik {
	
	private String user;
	private String password;
	
	private String id;
	private String sifra;
	private String naziv;
	private String privilegija;
	
	public Korisnik(String user,String password) {
		this.user = user;
		this.password = password;
	}
	public Korisnik() {}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getPrivilegija() {
		return privilegija;
	}
	public void setPrivilegija(String privilegija) {
		this.privilegija = privilegija;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
