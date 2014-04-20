package com.example.helixmobile;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListAdapter;

import com.example.model.Artiklis;
import com.example.service.ServiceHandler;

public class SyncJson extends Activity {
	
	//=================== JSON and XML calls from JAVA service back end ===================================
		// Progres Dialog
		private ProgressDialog pDialog;
		// URL service
		private static String url = "http://192.168.1.19:8080/RestTestApp2/webresources/sifri/1/50";
		// JSON Node names
	    private static final String TAG_SIFRI = "sifri";
	    private static final String TAG_ID = "id";
	    private static final String TAG_FIRMA = "firma";
	    private static final String TAG_SIFRA = "sifra";
	    private static final String TAG_NAZIV = "naziv";
	    private static final String TAG_PROD2 = "prod2";
	    private static final String TAG_TARIFA = "tarifa";
	    private static final String TAG_ED_PR_CENA = "edPrCena";
	    private static final String TAG_DOBAV = "dobav";
	    private static final String TAG_SIFSEK = "sifSek";
	    private static final String TAG_KOLICINA = "prKol";
	    
	    // contacts JSONArray
	    JSONArray sifri = null;

	    // Hashmap for ListView
	    ArrayList<HashMap<String, String>> sifriList;
	    ArrayList<Artiklis> artiklis;
	    // JSON - XML List Adapter
	    ListAdapter adapter;
	    
		//===================  End of implementation =========================================================
	    
	    ProgressDialog progress;
	    DBHelperClass database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//*****************************************************
				// 08 03 2014 from home adding the progress bar for fetching web service:
				final ProgressDialog dialog = ProgressDialog.show(this, "Sinhronizacija", "Prevzemanje na Artiklite od Centralnata DataBaza", true);
				final Handler handler = new Handler() {
				   public void handleMessage(Message msg) {
				      dialog.dismiss();
				      }
				   };
				Thread checkUpdate = new Thread() {  
				   public void run() {
				//
				//LONG CALCULATION STARTING FROM HERE:
				//********************************************************
					   ServiceHandler sc = new ServiceHandler();
						
						String jsonStr = sc.makeServiceCall(url, ServiceHandler.GET);
						
						String tempId = "";
						Log.d("Response: ", "> " + jsonStr);
						
						artiklis = new ArrayList<Artiklis>();
						 
						if(jsonStr != null) {
							 try {
								JSONArray jsonArr = new JSONArray(jsonStr);
								
								for (int i = 0; i < jsonArr.length(); i++) {
									JSONObject s = jsonArr.getJSONObject(i);
									
									Artiklis artikli = new Artiklis();
									
									String id = s.getString(TAG_ID);
			                        // log id
									tempId = id;

			                        String firma = s.getString(TAG_FIRMA);
			                        String sifra = s.getString(TAG_SIFRA);
			                        String naziv = s.getString(TAG_NAZIV);
			                        String prod2 = s.getString(TAG_PROD2);
			                        String tarifa = s.getString(TAG_TARIFA);
			                        String cena = s.getString(TAG_ED_PR_CENA);
			                        String dobav = s.getString(TAG_DOBAV);
			                        String sifSek = s.getString(TAG_SIFSEK);
			                        
			                        String em = s.getString("EM");
			                        String prKol = s.getString(TAG_KOLICINA);
			                        String virtKol = s.getString("virtKol");
			                        String virtCena = s.getString("virtCena");
			                        
			                        //HashMap<String, String> sifrii = new HashMap<String, String>();
			                        
			                        if(id!=null) artikli.setId(Integer.parseInt(id));
			                        else artikli.setId(Integer.parseInt("0"));
			                        
									if(firma!=null) artikli.setDobav(dobav );
									else artikli.setDobav("");
									
									if(sifra!=null) artikli.setSifra(  sifra );
									else artikli.setNaziv("");
									
									if(naziv!=null) artikli.setNaziv(  naziv );
									else artikli.setNaziv("");
									
									if(prod2!=null) artikli.setNaziv(  prod2 );
									else artikli.setNaziv("");
									
									if(prKol!=null) artikli.setPrkol(Double.parseDouble(prKol));//==============
									else artikli.setPrkol(Double.parseDouble("0"));
									
									if(tarifa!=null) artikli.setTarifa( tarifa );
									else artikli.setTarifa( "" );
									
									if(dobav!=null) artikli.setDobav( dobav );
									else artikli.setDobav( "" );
									
									if(sifSek!=null) artikli.setSifsek( sifSek );
									else artikli.setSifsek( "" );
										
									if(em!=null) artikli.setEm(  em );//============================
									else artikli.setEm("");
										
									if(cena!=null) artikli.setEdprcena(Double.parseDouble(cena) );
									else artikli.setEdprcena(Double.parseDouble("0") );
										
									if(virtKol!=null) artikli.setVirtkol( Double.parseDouble(virtKol) );//==========================
									else artikli.setVirtkol( Double.parseDouble("0") );
										
									if(virtCena!=null) artikli.setVirtcena(Double.parseDouble(virtCena) );//=======================
									else artikli.setVirtcena(Double.parseDouble("0") );
									
									artiklis.add(artikli);
								}
							} catch (JSONException e) {
								Log.e("INFORMACIJA", "Couldn't get data from "+ tempId + "\n message: " + e.getMessage());
							}
						} else {
			                Log.e("com.zoneli.helixmobile.service.ServiceHandler", "Couldn't get any data from the url");
			            }
						
						
						database = new DBHelperClass(getApplicationContext());
						database.checkDataBase();  // Addeth in home on 06032014 for checking the state of db!
						database.openDataBase();
						for(int i = 0; i < artiklis.size();i++) {
							database.insertObject(artiklis.get(i));
							//Log.i("Inside for loop ------------>", artiklis.get(i).dobav );
							
						}
						database.close();
						//*****************************************************
					   // end of copyed code from 180 line number to 266 line
					   
				      handler.sendEmptyMessage(0);
				      
				      callRegisterClassMethod();
				      return;
				      }
				   };
				checkUpdate.start();
					   
					   
			    //*****************************************************
				
				 
				
	}
	
	public void callRegisterClassMethod() {
		Intent syncIntent = new Intent(getApplicationContext(),RegisterSuccessfull.class);
		startActivity(syncIntent);
		this.finish();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}
	
	
	

}
