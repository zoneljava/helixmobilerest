package com.example.model;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Tipovi implements KvmSerializable,java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7709245868071183160L;
	private Integer _id;
	private String firma;
	private String id1;
	private String id2;
	private String id3;
	private String id4;
	private String id5;
	private String nivo;
	private String opis;
	private String tipoviId;
	
	
	
	
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getId1() {
		return id1;
	}
	public void setId1(String id1) {
		this.id1 = id1;
	}
	public String getId2() {
		return id2;
	}
	public void setId2(String id2) {
		this.id2 = id2;
	}
	public String getId3() {
		return id3;
	}
	public void setId3(String id3) {
		this.id3 = id3;
	}
	public String getId4() {
		return id4;
	}
	public void setId4(String id4) {
		this.id4 = id4;
	}
	public String getId5() {
		return id5;
	}
	public void setId5(String id5) {
		this.id5 = id5;
	}
	public String getNivo() {
		return nivo;
	}
	public void setNivo(String nivo) {
		this.nivo = nivo;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getTipoviId() {
		return tipoviId;
	}
	public void setTipoviId(String tipoviId) {
		this.tipoviId = tipoviId;
	}
	
	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getPropertyCount() {
		return 9;
	}
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
