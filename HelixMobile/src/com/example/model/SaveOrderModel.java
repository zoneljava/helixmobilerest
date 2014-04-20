package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.example.helixmobile.DBHelperClass;

public class SaveOrderModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String masaBroj;
	private ArrayList<String> naracaniArtikli;
	//private Button btn;
	private Context context;
	
	public SaveOrderModel() {}
	
	
	public SaveOrderModel(String masaBroj2, ArrayList naracani,Context context) {
		super();
		this.masaBroj = masaBroj2;
		this.naracaniArtikli = naracani;
		this.context = context;
	}

	public String getMasaBroj() {
		return masaBroj;
	}
	
	public void setMasaBroj(String masaBroj) {
		this.masaBroj = masaBroj;
	}
	
	public ArrayList getNaracaniArtikli() {
		return naracaniArtikli;
	}
	
	public void setNaracaniArtikli(ArrayList naracaniArtikli) {
		this.naracaniArtikli = naracaniArtikli;
	}
	
	public void save() {
		DBHelperClass db = new DBHelperClass(context);
		db.openDataBase();
		db.insertNarackiNeisprateni(masaBroj, naracaniArtikli);
		db.close();
	}
	
	public HashMap<String,ArrayList<String>> getOrderedValues(String masa,Context context) {
		HashMap<String,ArrayList<String>> orderedArtikli = new HashMap<String,ArrayList<String>>();
		//ArrayList<String> artikliValues = new ArrayList<String>();
		DBHelperClass db = new DBHelperClass(context);
		db.openDataBase();
		if(db.pendingOrders(context)) {
			orderedArtikli = db.getOrderedArtikli(masa);
		}
		db.close();
		return orderedArtikli;
	}
}
