package com.example.helixmobile;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText korisnik;
	private EditText lozinka;
	//private Button ok;
	private Korisnik user = null;
	private DBHelperClass db;
	private UserSessionManager session; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        korisnik = (EditText) findViewById(R.id.user);
        lozinka = (EditText) findViewById(R.id.password);
        user = new Korisnik();
        
        session = new UserSessionManager(getApplicationContext());

        
        Log.v("The_values_created_for_user: " , user.getUser() + " || " + user.getPassword());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
		System.exit(0);
	}
    
    public void clickIzlezMain(View view) {
    	System.exit(0);
    }
    
	public void onClick(View view)  {
		
		String username = korisnik.getText().toString();
        String password = lozinka.getText().toString();
		
		try {
			if(korisnik != null) {
	    		db = new DBHelperClass(this);
	    		if(!db.checkDataBase()) {
						db.createDataBase();
					
	    		} else {
	    			db.openDataBase();
	    		}
			}
    		user.setUser(username);
    		user.setPassword(password);
    		//Log.v("The_values_created_for_user_afterClick: " , user.getUser() + " || " + user.getPassword());
    		boolean registered = db.getVerificationFor(user.getUser().toLowerCase(),user.getPassword());
    		if(registered) {
    			session.createUserLoginSession(""+username,"Zonel");
    			
    			Intent i = new Intent(getApplicationContext(), RegisterSuccessfull.class);
    	    	startActivity(i);
    	    	this.finish();
    	    	
    		} else {
    			LayoutInflater inflater = getLayoutInflater();
    			View layout = inflater.inflate(R.layout.custom_toast,
    			                               (ViewGroup) findViewById(R.id.toast_layout_root));
    			TextView text = (TextView) layout.findViewById(R.id.text);
    			String toastMessage = "Vashite podatoci za logiranje vo HELIX sistemot " +
    					"\nne se tochni.\nVe molime vnesete ispravni Korisnik i Lozinka";
    			text.setText(toastMessage);
    			Toast toast = new Toast(getApplicationContext());
    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    			toast.setDuration(Toast.LENGTH_LONG);
    			toast.setView(layout);
    			toast.show();
    			
    		}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
	    }finally {
			db.close();
		}
    } 
	
	public void copyDB(View view) {
		DBHelperClass dbCreate = new DBHelperClass(getApplicationContext());
		try {
			dbCreate.copyDataBase();
			Toast.makeText(getApplicationContext(), "Iskopirano!", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "Ne kopirano!\n", Toast.LENGTH_LONG).show();
		}
	}
}
