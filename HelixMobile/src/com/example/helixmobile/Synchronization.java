package com.example.helixmobile;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;

import com.example.model.Artiklis;
import com.example.model.Tipovi;

public class Synchronization extends Activity {
												//  sifra like '1'
	//String sqlQuery = "SELECT * FROM sifri where naziv like '%TEST%'";   // TODO take from input field in configuration view   opshto_layout.xml
	//String pateka = "Data Source=192.168.1.49\\MSSQLEXPRESS;Initial Catalog=SLOGAAAA; User ID=sa;Password=zonel333; Integrated Security=false";  //TODO take from input field in configuration view   opshto_layout.xml
	//String urlToService = "http://192.168.1.49:81/hlservice.asmx";   //TODO take from input field from opshto_layout.xml
		
	 private String pateka= "Data Source=192.168.1.44\\ZSQL,1433;Initial Catalog=matka; User ID=sa;Password=zonel333; Integrated Security=false";
	 private String sqlQueryTipovi= "SELECT * FROM tipovi where firma='101'";
	 private String sqlQuery= "SELECT * FROM sifri where firma='101'";
	 private String sqlQueryKorisnici = "SELECT * FROM KORISNICI";
	
	 private static final String METHOD_NAME = "SELECT_DB";
	 private static final String SOAP_ACTION = "http://tempuri.org/SELECT_DB";
	 
	 private static final String NAMESPACE = "http://tempuri.org/";
	 private static final String URL = "http://192.168.1.49:81/hlservice.asmx";
	 //private static final String URL_tipovi = "http://192.168.1.49:81/hlservice.asmx";
	 
	 ArrayList<Artiklis> artiklis;
	 DBHelperClass database;
	 
	 ArrayList<Tipovi> tipovis;
	 ArrayList<Korisnik> korisnicis;
	 
	 ProgressDialog progress;
	 private SoapObject response = null;
	 SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sync_layout);
		
		//****************  start settings build pateka string *******************************
//		StringBuilder roadBuilder = new StringBuilder();
//		sharedPreferences = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE);
//		String DataSource = sharedPreferences.getString("serverKey", "192.168.1.49\\ZSQL,1433");
//		String InitialCatalog = sharedPreferences.getString("dbKey", "matka");
//		String user = sharedPreferences.getString("userKey", "sa");
//		String pass = sharedPreferences.getString("passKey", "zonel333");
//		String hliveUser = sharedPreferences.getString("hliveKey", "false");
//		roadBuilder.append("Data Source=").append(DataSource).append(";")
//					.append("Initial Catalog=").append(InitialCatalog).append(";")
//					.append("User ID=").append(user).append(";")
//					.append("Password=").append(pass).append(";")
//					.append("Integrated Security=").append(hliveUser).append(";");
//		
//		pateka = roadBuilder.toString();
//		sqlQuery = sharedPreferences.getString("queryKey", "SELECT * FROM sifri where firma='101'");
//		
//		Log.v("String Builder",	pateka);
		//****************************  end settings *********************************************
		
		
		//*****************************************************  This is for Artikli from SIFRI TAbela!!!!!!!!!!!!!!!!!!
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
		//
			   
			 //*****************************************************
			   SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
				
				PropertyInfo pi1 = new PropertyInfo();
		        pi1.setName("sql");
		        pi1.setValue(sqlQuery);
		        pi1.setType(String.class);
		        Request.addProperty(pi1);
		        
		        PropertyInfo pi2 = new PropertyInfo();
		        pi2.setName("pateka");
		        pi2.setValue(pateka);
		        pi2.setType(String.class);
		        Request.addProperty(pi2);
		        
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet = true;
		        envelope.setOutputSoapObject(Request);
		        
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		        artiklis = new ArrayList<Artiklis>();
		        
				try {
					Log.v("In the try catch block :  ",	"BEFORE ANY METHOD CALL.....");
					androidHttpTransport.call(SOAP_ACTION, envelope);
					response = (SoapObject) envelope.getResponse();
					Log.i("_________________________________", response.toString());
				} catch(Exception e) {
					
					Log.i("The response is: ", response.toString() );
				}
				
				SoapObject results = ((SoapObject)((SoapObject)response.getProperty(1)).getProperty(0));
				int resultsSize = results.getPropertyCount();
				Log.v("-----------", ((SoapObject)((SoapObject)response.getProperty(1)).getProperty(0)).getPropertyAsString(0) + "" );
				
				for(int i = 0; i < resultsSize ; i++) {
					if(((SoapObject)results.getProperty(i)).hasProperty("FIRMA")) {
						Artiklis artikli = new Artiklis();
						
						Integer _id = i;
						String firma = (((SoapObject)results.getProperty(i)).getProperty("FIRMA")).toString();
						String sifra =  (((SoapObject)results.getProperty(i)).getProperty("SIFRA")).toString();
						String naziv =  (((SoapObject)results.getProperty(i)).getProperty("NAZIV")).toString();
						String tarifa = (((SoapObject)results.getProperty(i)).getProperty("TARIFA")).toString();
						String em =   (((SoapObject)results.getProperty(i)).getProperty("E_M")).toString();
						String ed_pr_cena =(((SoapObject)results.getProperty(i)).getProperty("ED_PR_CENA")).toString();
						String pr_kol =  (((SoapObject)results.getProperty(i)).getProperty("PR_KOL")).toString();
						String virt_kol = ( ((SoapObject)results.getProperty(i)).getProperty("VIRT_KOL")).toString();
						String virt_cena = (((SoapObject)results.getProperty(i)).getProperty("VIRT_CENA")).toString();
						String tip1 =  (((SoapObject)results.getProperty(i)).getProperty("TIP1")).toString();
						
						artikli.setId(_id);
						artikli.setDobav(  firma  );
						artikli.setSifra(  sifra );
						artikli.setNaziv(  naziv );
						artikli.setPrkol(Double.parseDouble( pr_kol )   );
						artikli.setTarifa( tarifa );
						artikli.setEm(  em );
						artikli.setEdprcena(Double.parseDouble(ed_pr_cena) );
						artikli.setVirtkol( Double.parseDouble(virt_kol) );
						artikli.setVirtcena(Double.parseDouble(virt_cena) );
						
						// adeth on 07 04 2014 [ aquaring TIP1 field for writing to database to be general menu possible ]:
						artikli.setTip1(tip1);
						
						//artikli.setVirtem(  virt_em  );
						//artikli.setStranski(Short.parseShort(stran_ime) );
						//artikli.setNabcena(Double.parseDouble(nab_cena));
						artiklis.add(artikli);
					}
				}
				database = new DBHelperClass(getApplicationContext());
				database.checkDataBase();  // Addeth in home on 06032014 for checking the state of db!
				database.openDataBase();
				for(int i = 0; i < artiklis.size();i++) {
					database.insertObject(artiklis.get(i));
					//Log.i("Inside for loop ------------>", artiklis.get(i).dobav );
					
				}
				//database.close();
				//*****************************************************
			   // end of copyed code from 180 line number to 266 line
				
				//*****************************************************  This is for Tipovi from Paragoni Tabela!!!!!!!!!!!!!!!!!! 07 04 2014
				//*****************************************************
//				   SoapObject RequestTip = new SoapObject(NAMESPACE, METHOD_NAME);
//					
//					PropertyInfo pi1T = new PropertyInfo();
//			        pi1T.setName("sql");
//			        pi1T.setValue(sqlQueryTipovi);
//			        pi1T.setType(String.class);
//			        Request.addProperty(pi1T);
//			        
//			        PropertyInfo pi2T = new PropertyInfo();
//			        pi2T.setName("pateka");
//			        pi2T.setValue(pateka);
//			        pi2T.setType(String.class);
//			        Request.addProperty(pi2T);
//			        
//			        SoapSerializationEnvelope envelopeTipovi = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//			        envelopeTipovi.dotNet = true;
//			        envelopeTipovi.setOutputSoapObject(RequestTip);
//			        
//			        HttpTransportSE androidHttpTransportTipovi = new HttpTransportSE(URL);
//			        tipovis = new ArrayList<Tipovi>();
//			        
//					try {
//						Log.v("In the try catch block :  ",	"BEFORE ANY METHOD CALL.....");
//						androidHttpTransportTipovi.call(SOAP_ACTION, envelope);
//						response = (SoapObject) envelope.getResponse();
//						Log.i("_________________________________", response.toString());
//					} catch(Exception e) {
//						
//						Log.i("The response is: ", response.toString() );
//					}
//					
//					SoapObject resultsTip = ((SoapObject)((SoapObject)response.getProperty(1)).getProperty(0));
//					int resultsSizeTip = resultsTip.getPropertyCount();
//					Log.v("-----------", ((SoapObject)((SoapObject)response.getProperty(1)).getProperty(0)).getPropertyAsString(0) + "" );
//					
//					for(int i = 0; i < resultsSizeTip ; i++) {
//						if(((SoapObject)results.getProperty(i)).hasProperty("KORISNIK")) {
//							Tipovi tipovi = new Tipovi();
//							
//							Integer _id = i;
//							String firmaTip = (((SoapObject)results.getProperty(i)).getProperty("FIRMA")).toString();
//							String id1 =  (((SoapObject)results.getProperty(i)).getProperty("ID1")).toString();
//							String id2 =  (((SoapObject)results.getProperty(i)).getProperty("ID2")).toString();
//							String id3 = (((SoapObject)results.getProperty(i)).getProperty("ID3")).toString();
//							String id4 =   (((SoapObject)results.getProperty(i)).getProperty("ID4")).toString();
//							String id5 =(((SoapObject)results.getProperty(i)).getProperty("ID5")).toString();
//							String nivo =  (((SoapObject)results.getProperty(i)).getProperty("NIVO")).toString();
//							String opis = ( ((SoapObject)results.getProperty(i)).getProperty("OPIS")).toString();
//							String tipoviId = (((SoapObject)results.getProperty(i)).getProperty("TIPOVI_ID")).toString();
//
//							
//							tipovi.set_id(_id);
//							tipovi.setFirma(  firmaTip  );
//							tipovi.setId1(id1);
//							tipovi.setId2(id2);
//							tipovi.setId3(id3);
//							tipovi.setId4(id4);
//							tipovi.setId5(id5);
//							tipovi.setNivo(nivo );
//							tipovi.setOpis(opis);
//							tipovi.setTipoviId(tipoviId);
//
//							tipovis.add(tipovi);
//						}
//					}
//					database = new DBHelperClass(getApplicationContext());
//					database.checkDataBase();  // Addeth in home on 06032014 for checking the state of db!
//					database.openDataBase();
//					for(int i = 0; i < artiklis.size();i++) {
//						database.insertTipovi(tipovis.get(i));
//						//Log.i("Inside for loop ------------>", artiklis.get(i).dobav );
//						
//					}
			    //******************************************************************************************************************   07 04 2014
				
				//*****************************************************  This is for KORISNICI !!!!!!!!!!!!!!!!!! 17 04 2014
				//*****************************************************
//				   SoapObject RequestTip = new SoapObject(NAMESPACE, METHOD_NAME);
//					
//					PropertyInfo pi1T = new PropertyInfo();
//			        pi1T.setName("sql");
//			        pi1T.setValue(sqlQueryKorisnici);
//			        pi1T.setType(String.class);
//			        Request.addProperty(pi1T);
//			        
//			        PropertyInfo pi2T = new PropertyInfo();
//			        pi2T.setName("pateka");
//			        pi2T.setValue(pateka);
//			        pi2T.setType(String.class);
//			        Request.addProperty(pi2T);
//			        
//			        SoapSerializationEnvelope envelopeTipovi = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//			        envelopeTipovi.dotNet = true;
//			        envelopeTipovi.setOutputSoapObject(RequestTip);
//			        
//			        HttpTransportSE androidHttpTransportTipovi = new HttpTransportSE(URL);
//			        korisnicis = new ArrayList<Korisnik>();
//			        
//					try {
//						Log.v("In the try catch block :  ",	"BEFORE ANY METHOD CALL.....");
//						androidHttpTransportTipovi.call(SOAP_ACTION, envelope);
//						response = (SoapObject) envelope.getResponse();
//						Log.i("_________________________________", response.toString());
//					} catch(Exception e) {
//						
//						Log.i("The response is: ", response.toString() );
//					}
//					
//					SoapObject resultsTip = ((SoapObject)((SoapObject)response.getProperty(1)).getProperty(0));
//					int resultsSizeTip = resultsTip.getPropertyCount();
//					Log.v("-----------", ((SoapObject)((SoapObject)response.getProperty(1)).getProperty(0)).getPropertyAsString(0) + "" );
//					
//					for(int i = 0; i < resultsSizeTip ; i++) {
//						if(((SoapObject)results.getProperty(i)).hasProperty("FIRMA")) {
//							Korisnik korisnik = new Korisnik();
//							
//							Integer _id = i;
//							String sifra = (((SoapObject)results.getProperty(i)).getProperty("BROJ")).toString();
//							String naziv =  (((SoapObject)results.getProperty(i)).getProperty("IME")).toString();
//							String user =  (((SoapObject)results.getProperty(i)).getProperty("KORISNIK")).toString();
//							String lozinka = (((SoapObject)results.getProperty(i)).getProperty("LOZINKA")).toString();
//							String privilegija =   (((SoapObject)results.getProperty(i)).getProperty("PRIVILEGIJA")).toString();
//
//							
//							korisnik.setId(_id);
//							korisnik.setSifra(sifra);
//							korisnik.setNaziv(naziv);
//							korisnik.setUser(user);
//							korisnik.setPassword(lozinka);
//							korisnik.setPrivilegija(privilegija);
//
//							korisnicis.add(korisnik);
//						}
//					}
//					database = new DBHelperClass(getApplicationContext());
//					database.checkDataBase();  // Addeth in home on 06032014 for checking the state of db!
//					database.openDataBase();
//					for(int i = 0; i < artiklis.size();i++) {
//						database.insertKorisnici(korisnicis.get(i));
//						//Log.i("Inside for loop ------------>", artiklis.get(i).dobav );
//						
//					}
			    //******************************************************************************************************************   07 04 2014
				  database.close(); 
			      handler.sendEmptyMessage(0);
			      callRegisterClassMethod();
			      return;
		      }
		   };
		checkUpdate.start();
		
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



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
}
