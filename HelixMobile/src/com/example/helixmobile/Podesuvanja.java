package com.example.helixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Podesuvanja extends Activity {
	
//	private Button opshtoBtn;
//	private Button generaliiBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.podesuvanja_layout);
		
//		opshtoBtn = (Button) findViewById(R.id.opshto_btn);
//		generaliiBtn = (Button) findViewById(R.id.generalii_btn);
//		izlezBtn = (Button) findViewById(R.id.izlez_btn_pod);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	
	public void onOpshtoClick(View view) {
		Intent opshtoActivity = new Intent(getApplicationContext(), Opshto.class);
		startActivity(opshtoActivity);
		finish();
	}
	
	public void onGeneraliiClick(View view) {
		Intent generaliiActivity = new Intent(getApplicationContext(), Generalii.class);
		startActivity(generaliiActivity);
		finish();
	}
	
	public void clickIzlezPod(View view) {
		Intent regActivity = new Intent(getApplicationContext(), RegisterSuccessfull.class);
		startActivity(regActivity);
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent regActivity = new Intent(getApplicationContext(), RegisterSuccessfull.class);
		startActivity(regActivity);
		finish();
	}
	
	
}
