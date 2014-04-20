package com.example.helixmobile;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.ProgressDialog;
import android.util.Log;

import com.example.model.Artiklis;

public class TipoviSync {
	
		//String sqlQuery = "SELECT * FROM sifri where naziv like '%TEST%'";   // TODO take from input field in configuration view   opshto_layout.xml
		//String pateka = "Data Source=192.168.1.49\\MSSQLEXPRESS;Initial Catalog=SLOGAAAA; User ID=sa;Password=zonel333; Integrated Security=false";  //TODO take from input field in configuration view   opshto_layout.xml
		//String urlToService = "http://192.168.1.49:81/hlservice.asmx";   //TODO take from input field from opshto_layout.xml
			
		 private String pateka= "Data Source=192.168.1.44\\ZSQL,1433;Initial Catalog=matka; User ID=sa;Password=zonel333; Integrated Security=false";
		 private String sqlQuery= "SELECT * FROM tipovi";
		
		 private static final String METHOD_NAME = "SELECT_DB";
		 private static final String SOAP_ACTION = "http://tempuri.org/SELECT_DB";
		 
		 private static final String NAMESPACE = "http://tempuri.org/";
		 private static final String URL = "http://192.168.1.49:81/hlservice.asmx";
		
		 
		 ArrayList<Artiklis> artiklis;
		 DBHelperClass database;
		 
		 ProgressDialog progress;
		 private SoapObject response = null;
		 
	public void getTipoviSync() {

		// **************** start settings build pateka string
		// *******************************
		// StringBuilder roadBuilder = new StringBuilder();
		// SharedPreferences sharedPreferences =
		// getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE);
		// String DataSource = sharedPreferences.getString("serverKey",
		// "192.168.1.44\\ZSQL,1433");
		// String InitialCatalog = sharedPreferences.getString("dbKey",
		// "matka");
		// String user = sharedPreferences.getString("userKey", "sa");
		// String pass = sharedPreferences.getString("passKey", "zonel333");
		// String hliveUser = sharedPreferences.getString("hliveKey", "false");
		// roadBuilder.append("Data Source=").append(DataSource).append(";")
		// .append("Initial Catalog=").append(InitialCatalog).append(";")
		// .append("User ID=").append(user).append(";")
		// .append("Password=").append(pass).append(";")
		// .append("Integrated Security=").append(hliveUser).append(";");
		//
		// pateka = roadBuilder.toString();
		// sqlQuery = sharedPreferences.getString("queryKey",
		// "SELECT * FROM sifri");
		//
		// Log.v("String Builder", pateka);
		// **************************** end settings
		// *********************************************

		// *****************************************************
		// 08 03 2014 from home adding the progress bar for fetching web
		// service:
//		final ProgressDialog dialog = ProgressDialog.show(this,
//				"Sinhronizacija",
//				"Prevzemanje na Artiklite od Centralnata DataBaza", true);
//		final Handler handler = new Handler() {
//			public void handleMessage(Message msg) {
//				dialog.dismiss();
//			}
//		};
		Thread checkUpdate = new Thread() {
			public void run() {
				//
				// LONG CALCULATION STARTING FROM HERE:
				//

				// *****************************************************
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

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(Request);

				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				artiklis = new ArrayList<Artiklis>();

				try {
					Log.v("In the try catch block :  ",
							"BEFORE ANY METHOD CALL.....");
					androidHttpTransport.call(SOAP_ACTION, envelope);
					response = (SoapObject) envelope.getResponse();
					Log.i("_________________________________",
							response.toString());
				} catch (Exception e) {

					Log.i("The response is: ", response.toString());
				}

				SoapObject results = ((SoapObject) ((SoapObject) response
						.getProperty(1)).getProperty(0));
				int resultsSize = results.getPropertyCount();
				Log.v("-----------",
						((SoapObject) ((SoapObject) response.getProperty(1))
								.getProperty(0)).getPropertyAsString(0) + "");

				for (int i = 0; i < resultsSize; i++) {
					if (((SoapObject) results.getProperty(i))
							.hasProperty("FIRMA")) {
						Artiklis artikli = new Artiklis();

						Integer _id = i;
						String firma = (((SoapObject) results.getProperty(i))
								.getProperty("FIRMA")).toString();
						String sifra = (((SoapObject) results.getProperty(i))
								.getProperty("SIFRA")).toString();
						String naziv = (((SoapObject) results.getProperty(i))
								.getProperty("NAZIV")).toString();
						String tarifa = (((SoapObject) results.getProperty(i))
								.getProperty("TARIFA")).toString();
						String em = (((SoapObject) results.getProperty(i))
								.getProperty("E_M")).toString();
						String ed_pr_cena = (((SoapObject) results
								.getProperty(i)).getProperty("ED_PR_CENA"))
								.toString();
						String pr_kol = (((SoapObject) results.getProperty(i))
								.getProperty("PR_KOL")).toString();
						String virt_kol = (((SoapObject) results.getProperty(i))
								.getProperty("VIRT_KOL")).toString();
						String virt_cena = (((SoapObject) results
								.getProperty(i)).getProperty("VIRT_CENA"))
								.toString();
						// String virt_em =
						// (((SoapObject)results.getProperty(i)).getProperty("VIRT_E_M")).toString();
						// String stran_ime =
						// (((SoapObject)results.getProperty(i)).getProperty("STRAN_IME")).toString();
						// String nab_cena =
						// (((SoapObject)results.getProperty(i)).getProperty("NABCENA")).toString();

						// String temp = (String) ((SoapObject)
						// results.getProperty(i)).getProperty("BRAND");
						// Log.i("Temporary loging just for checking: ", temp);

						// Log.v("DELIMITER ",
						// "+++++++++++++++++++++++++++++++++++++++++++++++++++++"
						// );
						// Log.v("The results object dobav is: ", firma );
						// Log.v("The results sifra is: ", sifra );
						// Log.v("The results naziv is: ", naziv );
						// Log.v("The results tarifa is: ",tarifa );
						// Log.v("The results edinecna merka is: ", em );
						// Log.v("The results edinecna prodazna cena is: ",
						// ed_pr_cena );
						// Log.v("DELIMITER ",
						// "+++++++++++++++++++++++++++++++++++++++++++++++++++++"
						// );

						artikli.setId(_id);
						artikli.setDobav(firma);
						artikli.setSifra(sifra);
						artikli.setNaziv(naziv);
						artikli.setPrkol(Double.parseDouble(pr_kol));
						artikli.setTarifa(tarifa);
						artikli.setEm(em);
						artikli.setEdprcena(Double.parseDouble(ed_pr_cena));
						artikli.setVirtkol(Double.parseDouble(virt_kol));
						artikli.setVirtcena(Double.parseDouble(virt_cena));
						// artikli.setVirtem( virt_em );
						// artikli.setStranski(Short.parseShort(stran_ime) );
						// artikli.setNabcena(Double.parseDouble(nab_cena));
						artiklis.add(artikli);
					}
				}
				database = new DBHelperClass(new Synchronization().getApplicationContext());
				database.checkDataBase(); // Addeth in home on 06032014 for
											// checking the state of db!
				database.openDataBase();
				for (int i = 0; i < artiklis.size(); i++) {
					database.insertObject(artiklis.get(i));
					// Log.i("Inside for loop ------------>",
					// artiklis.get(i).dobav );

				}
				database.close();
				// *****************************************************
				// end of copyed code from 180 line number to 266 line

//				handler.sendEmptyMessage(0);
//
//				callRegisterClassMethod();
				return;
			}
		};
		checkUpdate.start();
	}
}
