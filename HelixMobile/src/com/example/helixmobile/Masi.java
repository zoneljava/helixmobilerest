package com.example.helixmobile;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.model.Artiklis;
import com.example.model.Masis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

public class Masi extends Activity {
	
	ExpandableListView expandMasi;
	List<String> masi;
	HashMap<String, ArrayList<String>> masiCollections;
	EditText ed;
	MyExpandableListAdapter myExpandableListViewAdapter;
	ProgressDialog pDialog;
	List<Artiklis> artikliList;
	ArrayList<String> paragoniStringArrayList;
	
	// ************ SOAP VALUES ***********************
		private static final String METHOD_NAME = "orderByAndroid";
		private static final String ARTIKLI_METHOD_NAME = "SELECT_DB";
		private static final String SOAP_ACTION = "http://tempuri.org/orderByAndroid";
		private static final String ARTIKLI_SOAP_ACTION = "http://tempuri.org/SELECT_DB";
		private static final String NAMESPACE = "http://tempuri.org/";
		private static final String URL = "http://192.168.1.49:81/hlservice.asmx";
		private static final String MASI_QUERY = "SELECT * FROM PARAGONI WHERE ZATVOREN<>'F'";
//		private static String GROUP_ARTIKLI_MASI_QUERY = 
//	"select a.PARAG_ID,a.SIFRA,s.NAZIV,a.CENA,a.KOLICINA,s.FIRMA from artikli as a,sifri as s " +
//															"where a.PARAG_ID='%s' and a.SIFRA=s.SIFRA and s.firma='101'";
		private String pateka = 
	"Data Source=192.168.1.44\\ZSQL,1433;Initial Catalog=MATKA; User ID=sa;Password=zonel333; Integrated Security=false";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masi_lay);
		
		ed = (EditText) findViewById(R.id.masa_search_inpt);
		
		Bundle bunI = getIntent().getBundleExtra("masiData");
		ArrayList<String> masi = new ArrayList<String>();
		String paragoni = new String();
		ArrayList<String> masiData = new ArrayList<String>();
		masi = bunI.getStringArrayList("masi");
		//Log.v("MASI BUNDLE :  ",  "BUNDLE value : " + masi + "");
		//Bundle bunI = getIntent().getBundleExtra("masiData");
		//paragoni = bunI.getString("paragoniid");
		paragoni = getIntent().getStringExtra("paragon");
		//Log.v("PARAGONI BUNDLE :  ",  "BUNDLE value : " + Arrays.asList(paragoni));
		String[] paragoniArr = paragoni.split(",");
		//Log.v("PARAGONI BUNDLE :  ",  "BUNDLE value : " + Arrays.asList(paragoniArr));
//		for (int i = 0; i < masi.size(); i++) {
//			Log.v("BUNDLE :  ",  "BUNDLE value : " + bun.getString("masa:"+i));
//			masi.add(masi.get(i));
//		}
		
		
		
		masiCollections = new HashMap<String, ArrayList<String>>();
		//data for extended masa view:
		//ArrayList<String> paragoniData = (ArrayList<String>) Arrays.asList(paragoniArr);
		//masiData.add("1");
		paragoniStringArrayList = new ArrayList<String>();
		ArrayList<String> paragoniTempList = null;
		for (int i = 0; i < masi.size(); i++) {
			GetArtikliData artikliDataReq = new GetArtikliData();
			artikliDataReq.execute(paragoniArr[i]);
			//masi.add("Broj na Masa: " + masi.get(i));
			try {
				paragoniTempList = new ArrayList<String>();
				ArrayList temp = artikliDataReq.get();
				for (int j = 0; j < temp.size(); j++) {
					paragoniTempList.add((String) temp.get(j));
				}
				
				paragoniStringArrayList.addAll( temp);
				masiCollections.put(masi.get(i), paragoniTempList);
//				Log.v("TEMP ARTIKLI DATA REQ OBJ IS:", masiCollections+"");
//				Log.v("TEMP ARTIKLI DATA REQ OBJ IS:", temp+"");
//				Log.v("INSIDE SOAP REQUEST:", paragoniStringArrayList + "");
				paragoniStringArrayList.clear();
			} catch (InterruptedException e) {
				// Eat the exception
			} catch (ExecutionException e) {
				// Eat the exception
			}
			//masiCollections.put(masi.get(i), paragoniStringArrayList);
			//Log.v("IN LOOP:  ",  Arrays.asList(paragoniStringArrayList) + "");
		}
//		Log.v("COLLECTION OF MASI IS:  ",  masiCollections + "");
		expandMasi = (ExpandableListView) findViewById(R.id.expandableListView1);
		myExpandableListViewAdapter = new MyExpandableListAdapter(this,masi,masiCollections);
		expandMasi.setAdapter(myExpandableListViewAdapter);
		if(pDialog.isShowing()) {
			pDialog.dismiss();
		}
		
		expandMasi.setOnGroupClickListener(new OnGroupClickListener() {
			int previousGroup = -1;
			int sizeOfGroup = expandMasi.getChildCount();

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
				if(groupPosition != previousGroup) {
					
					expandMasi.collapseGroup(previousGroup);
				} 
				Log.v("IN LOOP:  ",  "GROUP POSITION: " + groupPosition + "   PREVIOS GROUP: " + previousGroup);
	            previousGroup = groupPosition;
	            expandMasi.setSelectedGroup(sizeOfGroup);
				return false;
			}
		});
	}
	
	public void clickIzlez(View view) {
		Intent backToCreateOrder = new Intent(getApplicationContext(),StartClass.class);
		startActivity(backToCreateOrder);
		this.finish();
	}
	
private class GetArtikliData extends AsyncTask<String, Void, ArrayList<String>> {
		
		ArrayList<Artiklis> artikliList = null;
		ArrayList<String> artikliArrayList = new ArrayList<String>();
		
		
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
			// Showing progress dialog
            pDialog = new ProgressDialog(Masi.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();
		}

		

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			
			String ARTIKLI_QUERY = "SELECT * FROM ARTIKLI WHERE PARAG_ID='";
			
			String GROUP_ARTIKLI_MASI_QUERY = 
					"select a.PARAG_ID,a.SIFRA,s.NAZIV,a.CENA,a.KOLICINA,s.FIRMA from artikli as a,sifri as s " +
														"where a.PARAG_ID='%s' and a.SIFRA=s.SIFRA and s.firma='101'";
			
			
			artikliList = new ArrayList<Artiklis>();
			artikliArrayList = new ArrayList<String>();
			//masiListStrings = new ArrayList<String>();
			ARTIKLI_QUERY += params[0] + "'";
			String t = "".format(GROUP_ARTIKLI_MASI_QUERY,params[0]);
			Log.v("QUERY is :  ", t);
			SoapObject Request = new SoapObject(NAMESPACE, ARTIKLI_METHOD_NAME);
			
			PropertyInfo pi1 = new PropertyInfo();
			pi1.setName("sql");
			pi1.setValue(t);
			pi1.setType(String.class);
			Request.addProperty(pi1);

			PropertyInfo pi2 = new PropertyInfo();
			pi2.setName("pateka");
			pi2.setValue(pateka);
			pi2.setType(String.class);
			Request.addProperty(pi2);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.implicitTypes = true;
			envelope.setAddAdornments(false);
			// envelope.implicitTypes = true;
			envelope.setOutputSoapObject(Request);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				Log.v("In the try catch block :  ", "BEFORE MASA METHOD DATA GETTING CALL.....");
				androidHttpTransport.call(ARTIKLI_SOAP_ACTION, envelope);
				//Log.v("In the final after getting request value :  ", " response is: " +envelope.getResponse() + "");
//				Log.v("In the final after getting request value :  ", 
//						//((SoapObject)
//								((SoapObject)
//										((SoapObject)
//												((SoapObject)
//														envelope.getResponse()).getProperty(1)
//														
//														).getProperty(0)
//														//).getAttribute("Table1")
//														).getProperty(0)
//														//).getProperty(0) 
//																	+ "");
				Artiklis artikliObj = null;
				SoapObject temp =// (SoapObject) 
						//(
								(SoapObject)
									(
										(SoapObject)
											((SoapObject) 
													envelope.getResponse()
											).getProperty(1)
									).getProperty(0);
								//).getProperty(0);
//				Log.w("The object from request is: ", temp.getPropertyCount()+"");
//				Log.w("The object from request is: ", temp +"");
//				// getting the data from SOAP object
				for (int i = 0; i < temp.getPropertyCount(); i++) {
					
					SoapObject tempI = (SoapObject) temp.getProperty(i);
//					Log.w("The object from request inside FOOR LOOP is: ", tempI +"");
					String sifra = tempI.getProperty(1).toString();
					String naziv = tempI.getProperty(2).toString();
					String prkol = tempI.getProperty(4).toString();
					
//					String paragId = tempI.getProperty(2).toString();
//					String datum = tempI.getProperty(3).toString();
//					String vreme = tempI.getProperty(4).toString();
//					String vraboten = tempI.getProperty(5).toString();
//					String zatvoren = tempI.getProperty(6).toString();
//					String tipPlakanje = tempI.getProperty(7).toString();
					String iznos = tempI.getProperty(3).toString();
//					String firma = tempI.getProperty(9).toString();
//					String prodav = tempI.getProperty(10).toString();
//					String zatProm = tempI.getProperty(11).toString();
//					String masa = tempI.getProperty(12).toString();
//					String napl = tempI.getProperty(13).toString();
//					String zakl = tempI.getProperty(14).toString();
//					String zanosenje = tempI.getProperty(15).toString();
//					String guid = tempI.getProperty(16).toString();
//					String flag = tempI.getProperty(17).toString();
					
					artikliObj = new Artiklis();
					
					if(artikliObj != null) {
						//setting the data to artikli object
						artikliObj.setSifra(sifra);
						artikliObj.setNaziv(naziv);
						artikliObj.setPrkol(Double.parseDouble(prkol));
						artikliObj.setEdprcena(Double.parseDouble(iznos));
//						Log.w("The MASI object from request is: ", artikliObj.toString());
						
						//artikliList.add(artikliObj);
						artikliArrayList.add(artikliObj.toString());
						//Log.w("The ARTIKLI object from request is: ",artikliArrayList + "");
						//masiListStrings.add(masiObj.getMasa());
						//bundle.putString("masa:" + i, masiObj.getMasa().toString());
						
					} 
					
					//Log.w("The MASI object from request is: ", Arrays.asList(artikliObj) + "");
					
				}
			} catch(Exception e) {
				// for now just eat the value...
				Log.v("In the final after getting request value :  ",  "IN the EXCEPTION PART OF THE METOD CALL....");
			}
			pDialog.dismiss();
			return artikliArrayList;
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
//			Log.v("AFTER ASYNC IS DONE........... ",  "SYNC CALL IS DONE.........................................");
			pDialog.dismiss();
			//artikliArrayList.clear();
			//paragoniStringArrayList=result;
		}
		
	}
	

}
