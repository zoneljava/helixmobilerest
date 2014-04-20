package com.example.helixmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Generalii extends Activity {
	
	private EditText firma;
	private EditText adresa;
	private EditText ziroSmetka;
	private EditText broj;
	private EditText telefonFaks;
	
	private EditText prodav;
	private EditText kasa;
	private EditText metodname;
	private EditText namespace;
	private EditText url;
	private EditText soapAction;
	
	private SharedPreferences sharedPreferences;
	
	private String GENERALIIPREFS = "GeneraliiPrefs";
	
	private String FIRMA = "firmaKey";
	private String ADDRESS = "addressKey";
	private String ZIRO = "ziroKey";
	private String BROJ = "brojKey";
	private String PHONE = "telKey";
	
	private String PRODAV = "prodavKey";
	private String KASA = "kasaKey";
	private String METHOD_NAME = "metodKey";
	private String NAMESPACE = "namespaceKey";
	private String URL = "urlKey";
	private String SOAP_ACTION = "soapKey";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.generalii_layout);
		
		firma = (EditText) findViewById(R.id.firma_inp_str);
		adresa = (EditText) findViewById(R.id.adresa_inp_str);
		ziroSmetka = (EditText) findViewById(R.id.smetka_inp_str);
		broj = (EditText) findViewById(R.id.broj_inp_str);
		telefonFaks = (EditText) findViewById(R.id.telefon_inp_str);
		
		prodav = (EditText) findViewById(R.id.masa_search_inpt);
		kasa = (EditText) findViewById(R.id.editText2);
		metodname = (EditText) findViewById(R.id.editText3);
		namespace = (EditText) findViewById(R.id.editText6);
		url = (EditText) findViewById(R.id.editText5);
		soapAction = (EditText) findViewById(R.id.editText7);
		
		sharedPreferences = getSharedPreferences(GENERALIIPREFS,	Context.MODE_PRIVATE);

		if (sharedPreferences.contains(FIRMA)) {
			firma.setText(sharedPreferences.getString(FIRMA, ""));

		}
		if (sharedPreferences.contains(ADDRESS)) {
			adresa.setText(sharedPreferences.getString(ADDRESS, ""));

		}
		if (sharedPreferences.contains(ZIRO)) {
			ziroSmetka.setText(sharedPreferences.getString(ZIRO, ""));

		}
		if (sharedPreferences.contains(BROJ)) {
			broj.setText(sharedPreferences.getString(BROJ, ""));

		}
		if (sharedPreferences.contains(PHONE)) {
			telefonFaks.setText(sharedPreferences.getString(PHONE, ""));

		}
		//***********
		if (sharedPreferences.contains(PRODAV)) {
			prodav.setText(sharedPreferences.getString(PRODAV, ""));

		}
		if (sharedPreferences.contains(KASA)) {
			kasa.setText(sharedPreferences.getString(KASA, ""));

		}
		if (sharedPreferences.contains(METHOD_NAME)) {
			metodname.setText(sharedPreferences.getString(METHOD_NAME, ""));

		}
		if (sharedPreferences.contains(NAMESPACE)) {
			namespace.setText(sharedPreferences.getString(NAMESPACE, ""));

		}
		if (sharedPreferences.contains(URL)) {
			url.setText(sharedPreferences.getString(URL, ""));

		}
		if (sharedPreferences.contains(SOAP_ACTION)) {
			soapAction.setText(sharedPreferences.getString(SOAP_ACTION, ""));

		}
	}
	
	public void voRedClick(View view) {
		
		Editor editor = sharedPreferences.edit();
		editor.putString(FIRMA, firma.getText().toString());
		editor.putString(ADDRESS, adresa.getText().toString());
		editor.putString(ZIRO, ziroSmetka.getText().toString());
		editor.putString(BROJ, broj.getText().toString());
		editor.putString(PHONE, telefonFaks.getText().toString());
		
		editor.putString(PRODAV, prodav.getText().toString());
		editor.putString(KASA, kasa.getText().toString());
		editor.putString(METHOD_NAME, metodname.getText().toString());
		editor.putString(NAMESPACE, namespace.getText().toString());
		editor.putString(URL, url.getText().toString());
		editor.putString(SOAP_ACTION, soapAction.getText().toString());
		editor.commit(); 
		startActivity(new Intent(getApplicationContext(),Podesuvanja.class));
		this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	public void clickIzlez(View view) {
		Intent regSuccess = new Intent(getApplicationContext(),RegisterSuccessfull.class);
		startActivity(regSuccess);
		this.finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent regSuccess = new Intent(getApplicationContext(),RegisterSuccessfull.class);
		startActivity(regSuccess);
		this.finish();
	}
	
	
	
}
