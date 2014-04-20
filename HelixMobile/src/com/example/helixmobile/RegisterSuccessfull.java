package com.example.helixmobile;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.nsd.NsdManager.RegistrationListener;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterSuccessfull extends Activity {
	
//	private Button startButton; 
//	private Button syncButton; 
//	private Button optButton; 
	private DBHelperClass dbse;
	private Context thisVar;
	private UserSessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_success);

//		startButton = (Button) findViewById(R.id.btn_sett_start);
//		syncButton = (Button) findViewById(R.id.btn_sett_sync);
//		optButton = (Button) findViewById(R.id.btn_sett_opt);
		
		// referencing this activity context for Dialog Box
		thisVar = this;
		
		// referencing the current session for logged user ( vraboten )
		session = new UserSessionManager(getApplicationContext());
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		session.logoutUser();
		Intent myActivityBack = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(myActivityBack);
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	public void onStartClick(View view) {
		Intent startIntent = new Intent(getApplicationContext(), StartClass.class);
    	startActivity(startIntent);
    	this.finish();
	}

	public void onSyncClick(View view) {
		dbse = new DBHelperClass(getApplicationContext());
		dbse.checkDataBase();  //Changed at home for checking the state of db
		dbse.openDataBase();  // un comment this if is any kind of problems with sync!
		SQLiteDatabase openedDB = dbse.getOpenDatabase();
		openedDB.delete("ARTIKLI", null, null);
		dbse.close();
		// Start of chechking for data retriving
		SharedPreferences sharedPreferences = getSharedPreferences(
				"SettingsPrefs", Context.MODE_PRIVATE);
		boolean jsonValue = sharedPreferences.getBoolean("jsonKey", false);
		boolean xmlValue = sharedPreferences.getBoolean("xmlKey", false);
		boolean soapValue = sharedPreferences.getBoolean("soapKey", false);
		
		if(soapValue) {
			Log.d("SOAP","Inside soap true");
			Intent syncSoapIntent = new Intent(RegisterSuccessfull.this, Synchronization.class);
	    	startActivity(syncSoapIntent);
	    	this.finish();
		} else if(jsonValue) {
			Log.d("JSON","Inside json true");
			Intent syncJsonIntent = new Intent(RegisterSuccessfull.this, SyncJson.class);
	    	startActivity(syncJsonIntent);
	    	this.finish();
		}
//		else if(xmlValue) {
//			Intent syncXmlIntent = new Intent(RegisterSuccessfull.this, SyncXml.class);
//	    	startActivity(syncXmlIntent);
//	    	this.finish();
//		}
		else {
			Intent n = new Intent(RegisterSuccessfull.this, Podesuvanja.class);
	    	startActivity(n);
	    	this.finish();
		}
		
		
	}

	public void onOptionsClick(View view) {
		// Alert Dialog for setting quantity for artikl
		AlertDialog.Builder alert = new AlertDialog.Builder(thisVar);
		alert.setTitle("Najava za Sistemski podesuvanja");
		alert.setMessage("Vnesete User i Password");
		final EditText input = new EditText(thisVar);
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		alert.setView(input);
		// AlertDialog setting the positive OK button click listener
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// TODO First validate if the user and pass are valid Zonel credentials, then allow access...
				if(input.getText().toString().equalsIgnoreCase("zsonel")) {
					startActivity(new Intent(getApplicationContext(),Podesuvanja.class));
					RegisterSuccessfull.this.finish();
				} else {
					Toast.makeText(getApplicationContext(), 
							"Neuspeshno logiranje!\nObidete se povtorno", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		// AlertDialog setting the negative CANCEL button click listener
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Do nothing for now!!!!
					}
				});
		alert.show();

		
	}
	
	public void clickIzlez(View view) {
		session.logoutUser();
		Intent main = new Intent(getApplicationContext(), MainActivity.class);
    	startActivity(main);
    	this.finish();
	}

}
