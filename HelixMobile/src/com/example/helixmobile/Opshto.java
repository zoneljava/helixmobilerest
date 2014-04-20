package com.example.helixmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class Opshto extends Activity {
	
	private EditText serverIme;
	private EditText databazaIme;
	private EditText korisnik;
	private EditText password;
	private EditText hLiveUser;
	private EditText getQuery;
	
	private CheckBox jsonChbk;
	private CheckBox xmlChbk;
	private CheckBox soapChbk;
	
	public static final String MyPREFERENCES = "SettingsPrefs" ;
	
	public static final String ServerName = "serverKey"; 
	public static final String DatabazaName = "dbKey"; 
	public static final String UserName = "userKey"; 
	public static final String passwordVal = "passKey"; 
	public static final String HLIVEUSER = "hliveKey";
	public static final String getQueryString = "queryKey";
	
	public static final String jsonValue = "jsonKey";
	public static final String xmlValue = "xmlKey";
	public static final String soapValue = "soapKey";
	
	SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opshto_layout);
		// text edits
		serverIme = (EditText) findViewById(R.id.masa_search_inpt);
		databazaIme = (EditText) findViewById(R.id.editText2);
		korisnik = (EditText) findViewById(R.id.editText3);
		password = (EditText) findViewById(R.id.editText4);
		hLiveUser = (EditText) findViewById(R.id.editText5);
		getQuery = (EditText) findViewById(R.id.editText6);
		
		// check boxes
		jsonChbk = (CheckBox) findViewById(R.id.json_chbk);
		xmlChbk = (CheckBox) findViewById(R.id.xml_chbk);
		soapChbk = (CheckBox) findViewById(R.id.soap_chbk);
		
		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		
		// Check Box True False [ checked ] values:
		if (sharedpreferences.contains(jsonValue)) {
			jsonChbk.setChecked(sharedpreferences.getBoolean(jsonValue, false));
		}
		if (sharedpreferences.contains(xmlValue)) {
			xmlChbk.setChecked(sharedpreferences.getBoolean(xmlValue, false));
		}
		if (sharedpreferences.contains(soapValue)) {
			soapChbk.setChecked(sharedpreferences.getBoolean(soapValue, false));
		}
		
		//   Text Edit Fields Values:
		if (sharedpreferences.contains(ServerName)) {
			serverIme.setText(sharedpreferences.getString(ServerName, ""));

		}
		if (sharedpreferences.contains(DatabazaName)) {
			databazaIme.setText(sharedpreferences.getString(DatabazaName, ""));

		}
		if (sharedpreferences.contains(UserName)) {
			korisnik.setText(sharedpreferences.getString(UserName, ""));

		}
		if (sharedpreferences.contains(passwordVal)) {
			password.setText(sharedpreferences.getString(passwordVal, ""));

		}
		if (sharedpreferences.contains(HLIVEUSER)) {
			hLiveUser.setText(sharedpreferences.getString(HLIVEUSER, ""));

		}
		if (sharedpreferences.contains(getQueryString)) {
			getQuery.setText(sharedpreferences.getString(getQueryString, ""));

		}
		
		
	}
	
	public void voRedClick(View view) {
		
		
		Editor editor = sharedpreferences.edit();
		editor.putString(ServerName, serverIme.getText().toString());
		editor.putString(DatabazaName, databazaIme.getText().toString());
		editor.putString(UserName, korisnik.getText().toString());
		editor.putString(passwordVal, password.getText().toString());
		editor.putString(HLIVEUSER, hLiveUser.getText().toString());
		editor.putString(getQueryString, getQuery.getText().toString());
		
		editor.putBoolean(jsonValue, jsonChbk.isChecked());
		editor.putBoolean(xmlValue, xmlChbk.isChecked());
		editor.putBoolean(soapValue, soapChbk.isChecked());
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
		startActivity(new Intent(getApplicationContext(),RegisterSuccessfull.class));
		this.finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(),RegisterSuccessfull.class));
		this.finish();
	}
	
	
	
}
