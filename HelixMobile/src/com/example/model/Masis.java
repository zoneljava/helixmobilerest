package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ClipData.Item;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Masis 
		implements Serializable,Parcelable 
							{
	
	private String category;
	private ArrayList<Item> items;
	
	private String kasa;
	private String broj;
	private String paragId;
	private String datum;
	private String vreme;
	private String vraboten;
	private String zatvoren;
	private String tipPlakanje;
	private String iznos;
	private String firma;
	private String prodav;
	private String zatProm;
	private String masa;
	private String napl;
	private String zakl;
	private String zanosenje;
	private String guid;
	private String flag;
	
	public Masis(Parcel in) {
		category = in.readString();
	}
	
	
	public Masis() {}
	
	public String getKasa() {
		return kasa;
	}
	public void setKasa(String kasa) {
		this.kasa = kasa;
	}
	public String getBroj() {
		return broj;
	}
	public void setBroj(String broj) {
		this.broj = broj;
	}
	public String getParagId() {
		return paragId;
	}
	public void setParagId(String paragId) {
		this.paragId = paragId;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getVreme() {
		return vreme;
	}
	public void setVreme(String vreme) {
		this.vreme = vreme;
	}
	public String getVraboten() {
		return vraboten;
	}
	public void setVraboten(String vraboten) {
		this.vraboten = vraboten;
	}
	public String getZatvoren() {
		return zatvoren;
	}
	public void setZatvoren(String zatvoren) {
		this.zatvoren = zatvoren;
	}
	public String getTipPlakanje() {
		return tipPlakanje;
	}
	public void setTipPlakanje(String tipPlakanje) {
		this.tipPlakanje = tipPlakanje;
	}
	public String getIznos() {
		return iznos;
	}
	public void setIznos(String iznos) {
		this.iznos = iznos;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getProdav() {
		return prodav;
	}
	public void setProdav(String prodav) {
		this.prodav = prodav;
	}
	public String getZatProm() {
		return zatProm;
	}
	public void setZatProm(String zatProm) {
		this.zatProm = zatProm;
	}
	public String getMasa() {
		return masa;
	}
	public void setMasa(String masa) {
		this.masa = masa;
	}
	public String getNapl() {
		return napl;
	}
	public void setNapl(String napl) {
		this.napl = napl;
	}
	public String getZakl() {
		return zakl;
	}
	public void setZakl(String zakl) {
		this.zakl = zakl;
	}
	public String getZanosenje() {
		return zanosenje;
	}
	public void setZanosenje(String zanosenje) {
		this.zanosenje = zanosenje;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return new StringBuilder("kasa: " +getKasa()+", ").append("broj: " + getBroj() + ", ").append("paragId: " + getParagId() +", ")
				.append("datum: " + getDatum() + ", ").append("vreme: " + getVreme() + ", ").append("vraboten: " + getVraboten() + ", ")
				.append("zatvoren: " + getZatvoren() + ", ").append("tipNaPlakanje: " + getTipPlakanje() + ", ").append("iznos: " + getIznos() + ", ")
				.append("firma: " + getFirma() + ", ").append("prodav: " + getProdav() + ", ").append("zatProm: " + getZatProm() + ", ")
				.append("masa: " + getMasa() + ", ").append("napl: " + getNapl() + ", ").append("zakl: " + getZakl() + ", ").append("zaNosenje: " + getZanosenje() + ", ")
				.append("guid: " + getGuid() + ", ").append("flag: " + getFlag()).toString();
	}
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}
	
	public static final Parcelable.Creator<Masis> CREATOR = new Parcelable.Creator<Masis>() {
		public Masis createFromParcel(Parcel in) {
			return new Masis(in);
		}

		public Masis[] newArray(int size) {
			return new Masis[size];
		}
	};
	
	
}
