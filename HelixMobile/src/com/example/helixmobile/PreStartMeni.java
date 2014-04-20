package com.example.helixmobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PreStartMeni extends Activity {
	
	private Button sodr;
	private Button nov;
	private Button brishi;
	private Button izlez;
	private ListView masiList;
	private RadioButton masaSelekted;
	//private ListView naracanoPoMasaList;
	
	private ArrayList<String> masi = new ArrayList<String>();
	//private ArrayList<String> tempProduktiList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_lay_plus);
		
		//masi.add("MASA-1");masi.add("MASA-2");masi.add("MASA-3");
		
		HashMap<String,String> mData = new HashMap<String,String>();
		
		List<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
		
		//Make the size of masi array to be a variable taken from text input!!!!!!!!!! 
		for(int i = 0; i < 5;i++) {
			mData = new HashMap<String,String>();
			mData.put("MASA", "Masa br."+i );
			mData.put("BROJ", i+"" );
			data.add(mData);
		}
		
		String[] from = new String[] {"MASA","BROJ"};
		int[] to = new int[] {R.id.masi_, R.id.masi_br};
		
		
		masiList = (ListView) findViewById(R.id.masi_list);
		SimpleAdapter masiAdapt = new SimpleAdapter(this, data, R.layout.masibr_lay, from, to);
		//masiList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_checked,android.R.id.text1, masi));
		masiList.setAdapter(masiAdapt);
		masiList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		masaSelekted = (RadioButton)findViewById(R.id.masa_selekt);
		
		
//		naracanoPoMasaList = (ListView) findViewById(R.id.naracano_masa);
//		naracanoPoMasaList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, tempProduktiList));
		
		
	}

	public void clickSodr(View view) {
		Intent startActivity = new Intent(getApplicationContext(), StartClass.class);
		int count = masiList.getChildCount();
		
		// for loop across the items from masa list
		
		
		
		//String tempValue = getApplicationContext().
		//startActivity.putExtra("com.example.helixmobile.MasaBr",  );
		startActivity(startActivity);
	}

	public void clickNov(View view) {

	}

	public void clickBrishi(View view) {

	}

	public void clickIzlez(View view) {

	}
	
	public void ispratiOnClick(View view) {

	}
	
}
