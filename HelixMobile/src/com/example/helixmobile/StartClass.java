package com.example.helixmobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Service1.Product;
import Service1.Service1;
import Service1.VectorProduct;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Artikli;
import com.example.model.Masis;
import com.example.model.SaveOrderModel;
import com.example.model.Tipovi;

public class StartClass extends Activity {

	
	// =================== JSON and XML calls from JAVA service back end
	// ===================================
	// Progres Dialog
	private ProgressDialog pDialog;
	// URL service
	private static String url = "http://192.168.0.103:8080/ZonelAFRestWS/webresources/sifri/1/50";
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
	// JSON - XML List Adapter
	ListAdapter adapter;
	// =================== End of implementation
	// =========================================================

	// ************************ Shared Preferencess Generalii Setings
	// ***************************
	// private SharedPreferences sharedPreferences;

	// ************ REAL VALUES ***********************
	private static final String METHOD_NAME = "orderByAndroid";
	private static final String METHOD_NAME_NEW = "orderByAndroidNew";
	private static final String MASI_METHOD_NAME = "SELECT_DB";	
	private static final String SOAP_ACTION = "http://tempuri.org/orderByAndroidNew";
	private static final String MASI_SOAP_ACTION = "http://tempuri.org/SELECT_DB";
	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = "http://192.168.1.167:81/Service1.asmx";
	private static final String MASI_QUERY = "SELECT * FROM PARAGONI WHERE ZATVOREN<>'F'";
	private static final String ARTIKLI_QUERY = "SELECT * FROM ARTIKLI WHERE PARAGONI_ID='";
	// private String pateka =
	// "Data Source=192.168.1.49;Initial Catalog=SLOGAAAA; User ID=sa;Password=zonel333; Integrated Security=false";
	private String pateka = "Data Source=192.168.1.44\\ZSQL,1433;Initial Catalog=MATKA; User ID=sa;Password=zonel333; Integrated Security=false";

	private TextView nazivProdukt;
	private TextView nazivProduktInList;
	private TextView quantityListTxt;
	// private EditText quantityInp;
	private EditText masaBr;
	private EditText searchArtInp;
	private Button sendButton;
	private TextView quantTextList;
	private DBHelperClass db;
	private ListView tempListView1;
	private ListView tempListView2;
	private ArrayList artikliNaracani = new ArrayList();

	private SimpleAdapter simpleCollectProduct2;
	private SimpleAdapter simpleCollectProduct1;

	private EditText searchArtikli;
	private EditText searchSifri;
	// private ArrayAdapter<String> searchAdapter;

	private EditText sumNum;

	private Button unOrderedBtn;
	private Context thisVar;
	private String quantity;
	private int price;
	private static int sum = 0;
	private static boolean b;

	private UserSessionManager session;
	private String vrabotenId;
	private String vrabotenName;
	
	private ProgressDialog dialog;
	private String opis = "";
	Typeface customTypeface;
	//private String[] menuCategories;
	private ArrayList<String> menuCategories;
	private ListView menuSliderCategories;
	private ArrayList menuSliderList;
	
	// for getting data from SOAP request shared preferences values...
	private String InitialCatalog = "";
	private String userI = "";
	private String pass = "";
	private String hliveUser = "";
	
	ArrayList<String> masiListStrings = null;
	Bundle bundle = new Bundle();
	private SlidingDrawer slider;
	
	final HashMap<String,Integer> quantMap = new HashMap<String,Integer>();
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_layout);
		
		

		// **************** start settings build pateka string
		// *******************************
		StringBuilder roadBuilder = new StringBuilder();
		SharedPreferences sharedPreferences = getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE);
		String DataSource = sharedPreferences.getString("serverKey","192.168.1.44\\ZSQL,1433");
		// sync settings
		SyncHelper syncHelperClass = new SyncHelper();
		
		boolean jsonValue = sharedPreferences.getBoolean("jsonValue", false);
		boolean xmlValue = sharedPreferences.getBoolean("xmlValue", false);
		boolean soapValue = sharedPreferences.getBoolean("soapValue", false);
		
		
		//quantTextList = (TextView) findViewById(R.id.kolicina_txt_r);
		searchArtInp = (EditText) findViewById(R.id.search_art_inp);
		sendButton = (Button) findViewById(R.id.button5);
		
		// Database referencing
		db = new DBHelperClass(getApplicationContext());
		db.checkDataBase(); // add at home on 06032014 to check the state of db!
		// db.openDataBase();

		// Setting the Slider Drawer menu items -  [ MAIN GROUPS... ] DEFAULT SETTING:
		slider = (SlidingDrawer) findViewById(R.id.SlidingDrawer1);
		int sizeTipovi = 0;
		sizeTipovi = db.sizeOfTipoviTable();
		//menuCategories = new String[sizeTipovi]; 
		menuCategories = new ArrayList<String>();
				//{	
//				"ALL",
//				"POJADOK",
//				"GLAVNO JADENJE",
//				"PIJALOK",
//				"DESERT",
//				"SPECIJALITET"
				//};
		ArrayList<Tipovi> tipoviData = new ArrayList<Tipovi>();
		tipoviData = db.getAllTipoviData("TIPOVI");
		Log.v("THE GROUP MENU:", Arrays.asList(tipoviData) + "");
		for (int i = 0; i < tipoviData.size() ; i++) {
//			if(i==0) {
//				menuCategories[i] = "SITE ARTIKLI";
//				break;
//			}
			//menuCategories[i] = ((Tipovi)tipoviData.get(i)).getTipoviId().toString();
			menuCategories.add(((Tipovi)tipoviData.get(i)).getTipoviId().toString());
		}
		menuCategories.add("Site");
		//db.getAllTipoviData("TIPOVI").toArray(menuCategories);
		menuSliderCategories = (ListView) findViewById(R.id.sliding_list_meni);
		menuSliderCategories.setAdapter(
				new ArrayAdapter<String>(
						//this,android.R.layout.simple_selectable_list_item ,android.R.id.text1, menuCategories 
						this,R.layout.menu_list_item ,R.id.menu_item_txt, menuCategories 
				));
		
//		menuSliderCategories.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//				Log.v("THE GROUP MENU:", view.getId() + "");
//				TextView t = (TextView) view.findViewById(R.id.menu_item_txt);
//				Log.v("THE TEXT ITEM:", t.getText().toString());
//				String textGroupString = t.getText().toString();
//				ArrayList tempGroupList = db.getAllGroupTipoviData(textGroupString);
//			}
//		});
		
		// Setting the Slider Drawer menu items -  [ MAIN GROUPS... ] DATA aquarired from TIPOVI tabel:
//		menuSliderList = new ArrayList(); menuSliderList.add("Celo Meni");
//		ArrayList getTipoviData = db.getAllTipoviData("TIPOVI");
//		
//		Log.v("MENU SLIDER LIST ---> :  ", Arrays.asList(menuSliderList) + "" );
//		
//		if(getTipoviData.size() > 0) {
//			for (int i = 0; i < getTipoviData.size(); i++) {
//				menuSliderList.add(getTipoviData.get(i));
//			}
//		}
//		
//		
//		menuSliderCategories = (ListView) findViewById(R.id.sliding_list_meni);
//		menuSliderCategories.setAdapter(new ArrayAdapter<String>(this,android.R.layout.list_content ,android.R.id.text1, menuSliderList ));
		
		
		// text settings
		if(soapValue) {
			InitialCatalog = sharedPreferences.getString("dbKey", "matka");
			userI = sharedPreferences.getString("userKey", "sa");
			pass = sharedPreferences.getString("passKey", "zonel333");
			hliveUser = sharedPreferences.getString("hliveKey", "false");
			// checked settings
			// boolean jsonValue = sharedPreferences.getBoolean("jsonValue", false);
			// boolean xmlValue = sharedPreferences.getBoolean("xmlValue", false);
			// boolean soapValue = sharedPreferences.getBoolean("soapValue", false);
	
			roadBuilder.append("Data Source=").append(DataSource).append(";")
					.append("Initial Catalog=").append(InitialCatalog).append(";")
					.append("User ID=").append(userI).append(";")
					.append("Password=").append(pass).append(";")
					.append("Integrated Security=").append(hliveUser).append(";");
	
			pateka = roadBuilder.toString();
		} else if(jsonValue) {
			InitialCatalog = sharedPreferences.getString("dbKey", "matka");
			userI = sharedPreferences.getString("userKey", "sa");
			pass = sharedPreferences.getString("passKey", "zonel333");
			hliveUser = sharedPreferences.getString("hliveKey", "false");
			// checked settings
			// boolean jsonValue = sharedPreferences.getBoolean("jsonValue", false);
			// boolean xmlValue = sharedPreferences.getBoolean("xmlValue", false);
			// boolean soapValue = sharedPreferences.getBoolean("soapValue", false);
	
			roadBuilder.append("Data Source=").append(DataSource).append(";")
					.append("Initial Catalog=").append(InitialCatalog).append(";")
					.append("User ID=").append(userI).append(";")
					.append("Password=").append(pass).append(";")
					.append("Integrated Security=").append(hliveUser).append(";");
	
			pateka = roadBuilder.toString();
		} else {
			InitialCatalog = sharedPreferences.getString("dbKey", "matka");
			userI = sharedPreferences.getString("userKey", "sa");
			pass = sharedPreferences.getString("passKey", "zonel333");
			hliveUser = sharedPreferences.getString("hliveKey", "false");
			// checked settings
			// boolean jsonValue = sharedPreferences.getBoolean("jsonValue", false);
			// boolean xmlValue = sharedPreferences.getBoolean("xmlValue", false);
			// boolean soapValue = sharedPreferences.getBoolean("soapValue", false);
	
			roadBuilder.append("Data Source=").append(DataSource).append(";")
					.append("Initial Catalog=").append(InitialCatalog).append(";")
					.append("User ID=").append(userI).append(";")
					.append("Password=").append(pass).append(";")
					.append("Integrated Security=").append(hliveUser).append(";");
	
			pateka = roadBuilder.toString();
		}

		Log.v("PATEKA ---> :  ", pateka );

		// **************************** end settings
		// *********************************************

		// sharedPreferences = getSharedPreferences("GeneraliiPrefs",
		// Context.MODE_PRIVATE);

		// Search input reference objects
		searchArtikli = (EditText) findViewById(R.id.search_art_inp);
		searchSifri = (EditText) findViewById(R.id.search_sifri_inp);
		// List Views
		tempListView1 = (ListView) findViewById(R.id.naracani_produkti);
		tempListView2 = (ListView) findViewById(R.id.produkti_list);

		// Sum of price list for ordered artikl in list View one [ top ]
		sumNum = (EditText) findViewById(R.id.vkupno_num);
		// sumNum.setText(""+sumOfProductPrices(sum));

		// User session preferences - vraboten broj i vraboten ime
		session = new UserSessionManager(getApplicationContext());
		HashMap<String, String> user = session.getUserDetails();

		vrabotenId = user.get(UserSessionManager.KEY_ID);
		vrabotenName = user.get(UserSessionManager.KEY_NAME);
		Log.i("Vraboten num", "Broj na Vraboten e:  " + vrabotenId);
		Log.i("Vraboten ime", "Ime na Vraboten e:  " + vrabotenName);
		
		final List<HashMap<String, String>> insideProducts = new ArrayList<HashMap<String, String>>();
		// key valus for list adapter
		final String[] from = new String[] { "br", "SIFRA", "NAZIV","KOLICINA", "CENA"};   //   last INC value on 25 03 2014
		// id valus for text viws
		final int[] to = new int[] { R.id.br_txt, R.id.masi_txt,R.id.ime_txt_r,  R.id.kolicina_txt_r    , R.id.cena_txt_r  };  //   last 'R.layout.inc_lays' value on 27 03 2014
				
		// id valus for text viws
		final String[] from_ = new String[] { "br", "SIFRA", "NAZIV","KOLICINA", "CENA","+","-"};   //   last INC value on 27 03 2014
		final int[] to_ = new int[] { R.id.br_txt_, R.id.masi_txt_, R.id.ime_txt_r_,R.id.kolicina_txt_r_ , R.id.cena_txt_r_, R.id.menu_item_txt,R.id.textView2  };  //   last 'R.layout.inc_lays' value on 27 03 2014

		// search input boxes key listeners - ARTIKLI
		searchArtikli.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int start, int before,int count) {
				//StartClass.this.searchAdapter.getFilter().filter(s);
				//StartClass.this.simpleCollectProduct2.getFilter().filter(cs);
				
				// 17 04 2014  **********************  adding reduced list search after naziv values: *********************************
				// setting the list 2 addapter data
				//Log.v("THE GROUP MENU:", view.getId() + "");
				//TextView t = (TextView) view.findViewById(R.id.menu_item_txt);
				//Log.v("THE TEXT ITEM:", t.getText().toString());
				//String textGroupString = t.getText().toString();
				//insideProducts.clear();
				
				ArrayList<Artikli> tempGroupList = db.getSearchedArtikliData(cs.toString());
				// setting the list 2 addapter data
				HashMap<String, String> mapArtikliI;
				for (int i = 0; i < tempGroupList.size(); i++) {
					mapArtikliI = new HashMap<String, String>();
					mapArtikliI.put("br", ((Artikli) tempGroupList.get(i)).getTip1()); // ************************************
					mapArtikliI.put("SIFRA", ((Artikli) tempGroupList.get(i)).getSifri());
					mapArtikliI.put("NAZIV", ((Artikli) tempGroupList.get(i)).getNaziv());
					mapArtikliI.put("KOLICINA", ((Artikli) tempGroupList.get(i)).getKolicina().toString());
					mapArtikliI.put("CENA", ((Artikli) tempGroupList.get(i)).getCena().toString());
//					LayoutInflater inflo = this.getLayoutInflater();
					mapArtikliI.put("+", "+" );  //  last INC value on 27 03 2014
					mapArtikliI.put("-", "-");  //  last INC value on 27 03 2014
					insideProducts.add(mapArtikliI);
				}
				
				Log.i("List 2 Info", Arrays.asList(insideProducts).toString());
				
				simpleCollectProduct2 = new SimpleAdapter(StartClass.this, insideProducts,	R.layout.list_lays, from, to){

					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						
						Context contxt = StartClass.this.getApplicationContext();
						
						LayoutInflater inflater = (LayoutInflater) contxt
					            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

					    View rowView = inflater.inflate(R.layout.list_lays, parent, false);
					    
						 /* Font file is saved in path assets\fonts\ */
				        customTypeface = Typeface.createFromAsset(contxt.getAssets(), "fonts/zonelmak.ttf");
				        TextView text = (TextView) rowView.findViewById(R.id.ime_txt_r);
				        //TextView quant = (TextView) rowView.findViewById(R.id.kolicina_txt_r);
				        
				        /* Set custom typeface to text view you want to custom */
				        text.setTypeface(customTypeface );
				        //Log.i("-----------------:", "in the place to beee...........");
						return super.getView(position, rowView, parent);
					}
					
				};
				if(count>2) {
					InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(searchArtikli.getWindowToken(), 0);
				}
				tempListView2.setAdapter(simpleCollectProduct2);
			}
			
			

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
//		searchArtikli.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				
//					InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//				    imm.hideSoftInputFromWindow(searchArtikli.getWindowToken(), 0);
//				
//			}
//		});
		
		// search input boxes key listeners - SIFRI
		searchSifri.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int start, int before,int count) {
				// StartClass.this.searchAdapter.getFilter().filter(s);
				//StartClass.this.simpleCollectProduct2.getFilter().filter(cs);
				//simpleCollectProduct2.notifyDataSetChanged();
				
				insideProducts.clear();
				ArrayList<Artikli> tempGroupList = db.getSearchedSifraArtikliData(cs.toString());
				// setting the list 2 addapter data
				HashMap<String, String> mapArtikliI;
				for (int i = 0; i < tempGroupList.size(); i++) {
					mapArtikliI = new HashMap<String, String>();
					mapArtikliI.put("br", ((Artikli) tempGroupList.get(i)).getTip1()); // ************************************
					mapArtikliI.put("SIFRA", ((Artikli) tempGroupList.get(i)).getSifri());
					mapArtikliI.put("NAZIV", ((Artikli) tempGroupList.get(i)).getNaziv());
					mapArtikliI.put("KOLICINA", ((Artikli) tempGroupList.get(i)).getKolicina().toString());
					mapArtikliI.put("CENA", ((Artikli) tempGroupList.get(i)).getCena().toString());
//					LayoutInflater inflo = this.getLayoutInflater();
					mapArtikliI.put("+", "+" );  //  last INC value on 27 03 2014
					mapArtikliI.put("-", "-");  //  last INC value on 27 03 2014
					insideProducts.add(mapArtikliI);
				}
				
				Log.i("List 2 Info", Arrays.asList(insideProducts).toString());
				
				simpleCollectProduct2 = new SimpleAdapter(StartClass.this, insideProducts,	R.layout.list_lays, from, to){

					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						
						Context contxt = StartClass.this.getApplicationContext();
						
						LayoutInflater inflater = (LayoutInflater) contxt
					            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

					    View rowView = inflater.inflate(R.layout.list_lays, parent, false);
					    
						 /* Font file is saved in path assets\fonts\ */
				        customTypeface = Typeface.createFromAsset(contxt.getAssets(), "fonts/zonelmak.ttf");
				        TextView text = (TextView) rowView.findViewById(R.id.ime_txt_r);
				        //TextView quant = (TextView) rowView.findViewById(R.id.kolicina_txt_r);
				        
				        /* Set custom typeface to text view you want to custom */
				        text.setTypeface(customTypeface );
				        //Log.i("-----------------:", "in the place to beee...........");
						return super.getView(position, rowView, parent);
					}
					
				};
				if(count>2) {
					InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				    imm.hideSoftInputFromWindow(searchArtikli.getWindowToken(), 0);
				}
				tempListView2.setAdapter(simpleCollectProduct2);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});

		// referencing this activity context for Dialog Box
		thisVar = this;

		

		// Edit Text box for masa number entry
		masaBr = (EditText) findViewById(R.id.masa_br_ed);
		masaBr.setFocusable(true);
		masaBr.requestFocus();

		// masa key listener
//		masaBr.setOnKeyListener(new EditText.OnKeyListener() {
//			@Override
//			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
//				if (artikliNaracani.size() > 0) {
//					Toast.makeText(getApplicationContext(),	"Imate ostanati naracani produkti!\n",Toast.LENGTH_LONG).show();
//					masaBr.setText("");
//				}
//				return false;
//			}
//		});

		// quantityInp = (EditText) findViewById(R.id.search_input_txt);
		//quantityListTxt = (TextView) findViewById(R.id.kolicina_txt_r);

		// data for list adapter
		sifriList = new ArrayList<HashMap<String, String>>();

		// call to new GetSifri will return ArrayList<HashMap<String,String>>
		// values for populating listview

		// =======================================================================================================
		AsyncTask<Void, Void, Void> at;
		
		// ************************ COmented out on 17 04 2014 put pn the line: 334
		//List<HashMap<String, String>> insideProducts = new ArrayList<HashMap<String, String>>();

//		// key valus for list adapter
//		final String[] from = new String[] { "br", "SIFRA", "NAZIV","KOLICINA", "CENA"};   //   last INC value on 25 03 2014
//		// id valus for text viws
//		final int[] to = new int[] { R.id.br_txt, R.id.masi_txt,R.id.ime_txt_r,  R.id.kolicina_txt_r    , R.id.cena_txt_r  };  //   last 'R.layout.inc_lays' value on 27 03 2014
//		
//		// id valus for text viws
//		final String[] from_ = new String[] { "br", "SIFRA", "NAZIV","KOLICINA", "CENA","+","-"};   //   last INC value on 27 03 2014
//		final int[] to_ = new int[] { R.id.br_txt_, R.id.masi_txt_, R.id.ime_txt_r_,R.id.kolicina_txt_r_ , R.id.cena_txt_r_, R.id.menu_item_txt,R.id.textView2  };  //   last 'R.layout.inc_lays' value on 27 03 2014

		final ArrayList<Artikli> listOfArtikli = db.getAllArtikliData("ARTIKLI");
		db.close();

		HashMap<String, String> mapArtikli = new HashMap<String, String>();
		int productsSize = listOfArtikli.size();

		// setting the list 2 addapter data
		for (int i = 0; i < productsSize; i++) {
			mapArtikli = new HashMap<String, String>();
			mapArtikli.put("br", ((Artikli) listOfArtikli.get(i)).getTip1()); // ************************************
			mapArtikli.put("SIFRA", ((Artikli) listOfArtikli.get(i)).getSifri());
			mapArtikli.put("NAZIV", ((Artikli) listOfArtikli.get(i)).getNaziv());
			mapArtikli.put("KOLICINA", ((Artikli) listOfArtikli.get(i)).getKolicina().toString());
			mapArtikli.put("CENA", ((Artikli) listOfArtikli.get(i)).getCena().toString());
//			LayoutInflater inflo = this.getLayoutInflater();
			mapArtikli.put("+", "+" );  //  last INC value on 27 03 2014
			mapArtikli.put("-", "-");  //  last INC value on 27 03 2014
			insideProducts.add(mapArtikli);
		}
		
		Log.i("List 2 Info", Arrays.asList(insideProducts).toString());
		
		simpleCollectProduct2 = new SimpleAdapter(this, insideProducts,	R.layout.list_lays, from, to){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				Context contxt = StartClass.this.getApplicationContext();
				
				LayoutInflater inflater = (LayoutInflater) contxt
			            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			    View rowView = inflater.inflate(R.layout.list_lays, parent, false);
			    
				 /* Font file is saved in path assets\fonts\ */
		        customTypeface = Typeface.createFromAsset(contxt.getAssets(), "fonts/zonelmak.ttf");
		        TextView text = (TextView) rowView.findViewById(R.id.ime_txt_r);
		        //TextView quant = (TextView) rowView.findViewById(R.id.kolicina_txt_r);
		        
		        /* Set custom typeface to text view you want to custom */
		        text.setTypeface(customTypeface );
		        Log.i("-----------------:", "in the place to beee...........");
				return super.getView(position, rowView, parent);
			}
			
		};
		// adapter for ListView - 1
		simpleCollectProduct1 = new SimpleAdapter(this, artikliNaracani,R.layout.list_lays_o, from_, to_)
		{
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				
				Context contxt = StartClass.this.getApplicationContext();
				final StartClass sc = new StartClass();
				LayoutInflater inflater = (LayoutInflater) contxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View rowView = inflater.inflate(R.layout.list_lays_o, parent, false);
				 /* Font file is saved in path assets\fonts\ */
		        Typeface customTypeface = Typeface.createFromAsset(contxt.getAssets(), "fonts/zonelmak.ttf");
		        TextView text = (TextView) rowView.findViewById(R.id.ime_txt_r_);
		        //final TextView masiTxt = (TextView) rowView.findViewById(R.id.masi_txt_);
		        final TextView quantT = (TextView) rowView.findViewById(R.id.kolicina_txt_r_);
		        TextView inc = (TextView) rowView.findViewById(R.id.menu_item_txt);
		        TextView dec = (TextView) rowView.findViewById(R.id.textView2);
		        inc.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int i = sc.IncAndDecClick(v);
//						TextView quanT = (TextView) ((View)((View)v.getParent()).getParent()).findViewById(R.id.kolicina_txt_r_);
						//quanT.setDrawingCacheEnabled(true);
						quantT.setText(i+"");
						HashMap<String, String> tempMap = (HashMap<String, String>) artikliNaracani.get(position);
						//artikliNaracani.remove(position);
						tempMap.put("KOLICINA", quantT.getText().toString());
						artikliNaracani.set(position, tempMap );
						simpleCollectProduct1.notifyDataSetChanged();
						//quantMap.put(masiTxt.getText().toString(), Integer.parseInt( quanT.getText().toString() ));
						//simpleCollectProduct1
						//tempListView1.setAdapter(simpleCollectProduct1);
					}
				});
		        dec.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int i = sc.IncAndDecClick(v);
						//TextView quanT = (TextView) ((View)((View)v.getParent()).getParent()).findViewById(R.id.kolicina_txt_r_);
						quantT.setText(i+"");
						//artikliNaracani.remove(position);
						HashMap<String, String> tempMap = (HashMap<String, String>) artikliNaracani.get(position);
						//artikliNaracani.remove(position);
						tempMap.put("KOLICINA", quantT.getText().toString());
						artikliNaracani.set(position, tempMap );
						simpleCollectProduct1.notifyDataSetChanged();
						//quantMap.put(masiTxt.getText().toString(), Integer.parseInt( quanT.getText().toString() ));
//						notifyDataSetChanged();
					}
				});
		        /* Set custom typeface to text view you want to custom */
		        text.setTypeface(customTypeface);
		        
		        // seting the default quantity:
		        //quant.setText("2");
//		        Log.i("Artikli naracani LIST:", artikliNaracani.get(position) + "");
//		        HashMap<String, String> tempMap = (HashMap<String, String>) artikliNaracani.get(position);
//		        
//		        artikliNaracani.set(position, tempMap );
//		        simpleCollectProduct1.notifyDataSetChanged();
//		        Log.i("Artikli naracani MAP:",tempMap + "");
//		        //Log.i("Artikli naracani MAP:",quantT.getText().toString());
//		        Log.i("Artikli naracani MAP:",tempMap.get("KOLICINA") + "");
//		        if(!artikliNaracani.isEmpty() && Integer.parseInt(tempMap.get("KOLICINA")) > 1) {
//		        	artikliNaracani.remove(position);
//		        	tempMap.put("KOLICINA", quantT.getText().toString());
//				    artikliNaracani.set(position, tempMap );
//		        }
				return super.getView(position, rowView, parent);
			}
			
			
		
	};  // end of simple addapter for temp list view 1
		
		tempListView2.setAdapter(simpleCollectProduct2);

		tempListView1.setOnItemClickListener(new OnItemClickListener() {
			
			HashMap hm;
			double sumI;
			@Override
			public void onItemClick(AdapterView arg0, View arg1, int arg2,final long arg3) {
				/*
				 * 
				 * Changed asked from client
				 * 
				 * 28 03 2014
				 * 
				 * 
				 */
				AlertDialog.Builder alert = new AlertDialog.Builder(thisVar);
//				alert.setTitle(R.string.quantity_txt_str);
//				alert.setMessage(R.string.quantity_txt_str);
				
				alert.setTitle("Otstrani Produkt");
				alert.setMessage("Potvrdete dali sakate da go otstranite produktot od listata na narachani produkti?");
				
//				final EditText input = new EditText(thisVar);
//				input.setInputType(InputType.TYPE_CLASS_NUMBER);
//				alert.setView(input);
				// AlertDialog setting the positive OK button click listener
				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int whichButton) {
//								quantTextList.setVisibility(EditText.INVISIBLE);
								hm  = (HashMap) (tempListView1.getItemAtPosition((int) arg3));
								double cena = Double.parseDouble(hm.get("CENA") + "");
								double quant = Double.parseDouble(hm.get("KOLICINA") + "");
								Log.i("Quant num", "Suma:  " + quant);
								sum -= ((int) (cena*quant));
								Log.i("Suma sumarum num", "Suma:  " + sum);
								sumNum.setText(sum + "");
								artikliNaracani.remove((int) arg3);
								tempListView1.setAdapter(simpleCollectProduct1);
								//tempListView1.invalidateViews();
							}
						});
				// AlertDialog setting the negative CANCEL button click listener
				alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int whichButton) {
								
							}
						});
				alert.show();
				
//				quantTextList.setVisibility(EditText.INVISIBLE);
//				hm  = (HashMap) (tempListView1.getItemAtPosition((int) arg3));
//				double cena = Double.parseDouble(hm.get("CENA") + "");
//				double quant = Double.parseDouble(hm.get("KOLICINA") + "");
//				Log.i("Quant num", "Suma:  " + quant);
//				sum -= ((int) (cena*quant));
//				Log.i("Suma sumarum num", "Suma:  " + sum);
//				sumNum.setText(sum + "");
//				artikliNaracani.remove((int) arg3);
//				tempListView1.setAdapter(simpleCollectProduct1);
			}

		});
		
		
		tempListView2.setOnItemClickListener(new OnItemClickListener() {

			HashMap hm;
			//HashMap<String,Integer> quantMap = new HashMap<String,Integer>();
			@Override
			public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
//				quantTextList.setVisibility(EditText.VISIBLE);
				//****************************************************************
				hm  = (HashMap) ((HashMap) (tempListView2.getItemAtPosition((int) arg3))).clone();

				Log.i( " The OBJECT VIEW ", hm.get("KOLICINA") + "");
				
				double cena = Double.parseDouble(hm.get("CENA") + "");
		
				sum += ((int) cena);
				sumNum.setText(sum + "");
				Log.i( " KOLICINA After ITEM klik: ", hm.get("KOLICINA") + "");
				hm.put("KOLICINA", "1");
				Log.i( " KOLICINA After ITEM klik: ", hm.get("KOLICINA") + "");
				int i = tempListView1.getHeight();
				artikliNaracani.add(hm);
				//quantMap.put((String) hm.get("SIFRA"), (Double.parseDouble((String) hm.get("KOLICINA"))) );
				//Log.i( " AFTER QUNTITY MAP IS INCREMENTED: ", quantMap + "");
				tempListView1.setAdapter(simpleCollectProduct1);
				tempListView1.setSelection(tempListView1.getCount() - 1);
				
				
			}

		});
		// on Click listener for list 2
		tempListView2.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,final int arg2, final long arg3) {
				
				// Alert Dialog for setting quantity for artikl
				AlertDialog.Builder alert = new AlertDialog.Builder(thisVar);
//				alert.setTitle(R.string.quantity_txt_str);
//				alert.setMessage(R.string.quantity_txt_str);
				
				alert.setTitle("Opis");
//				alert.setMessage(R.string.quantity_txt_str);
				
				final EditText input = new EditText(thisVar);
				input.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
				alert.setView(input);
				// AlertDialog setting the positive OK button click listener
				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int whichButton) {
								
								opis = input.getText().toString();	
								
//								HashMap hm = (HashMap) ((HashMap) (tempListView2.getItemAtPosition((int) arg3))).clone();
//								hm.put("br", opis);
//								artikliNaracani.add(hm);
//								Log.i( " The OBJECT VIEW ", artikliNaracani + "");
//								tempListView1.setAdapter(simpleCollectProduct1);
//								tempListView1.setSelection(tempListView1.getCount() - 1);
							}
						});
				// AlertDialog setting the negative CANCEL button click listener
				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int whichButton) {
								// Do nothing for now!!!!
							}
						});
				alert.show();
				return false;
			}

		});
		//  ******************  26 03 2014 ***********************************************
		tempListView1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,final int arg2, final long arg3) {
				
				// Alert Dialog for setting quantity for artikl
				AlertDialog.Builder alert = new AlertDialog.Builder(thisVar);
//				alert.setTitle(R.string.quantity_txt_str);
//				alert.setMessage(R.string.quantity_txt_str);
				
				alert.setTitle("Opis");
//				alert.setMessage(R.string.quantity_txt_str);
				
				final EditText input = new EditText(thisVar);
				input.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
				alert.setView(input);
				// AlertDialog setting the positive OK button click listener
				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int whichButton) {
								
								opis = input.getText().toString();
								
//								HashMap hm = (HashMap) (tempListView1.getItemAtPosition((int) arg3));
//								hm.put("br", opis);
//								Log.i( " The OBJECT VIEW ", artikliNaracani + "");
//								tempListView1.setAdapter(simpleCollectProduct1);

							}
						});
				// AlertDialog setting the negative CANCEL button click listener
				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int whichButton) {
								// Do nothing for now!!!!
							}
						});
				alert.show();
				return false;
			}

		});
		
		// 13 04 2014 - adding group menu change listener for reducing the all products lists:
		menuSliderCategories.setOnItemClickListener(new OnItemClickListener() {
			
			List<HashMap<String, String>> insideProducts = new ArrayList<HashMap<String, String>>();
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				String textFromMenuItem = ((TextView)view.findViewById(R.id.menu_item_txt)).getText().toString();
				
				if(textFromMenuItem.equalsIgnoreCase("Site")) {
					//menuCategories.clear();
					
					//*******************************************************************  All Items from List ***********************
					
					Log.v("THE GROUP MENU ITEM TEXT:", ((TextView)view.findViewById(R.id.menu_item_txt)).getText() + "");
					TextView t = (TextView) view.findViewById(R.id.menu_item_txt);
					Log.v("THE TEXT ITEM:", t.getText().toString());
					String textGroupString = t.getText().toString();
					ArrayList<Artikli> tempGroupList = db.getAllArtikliData("ARTIKLI");
					// setting the list 2 addapter data
					HashMap<String, String> mapArtikliI;
					for (int i = 0; i < tempGroupList.size(); i++) {
						mapArtikliI = new HashMap<String, String>();
						mapArtikliI.put("br", ((Artikli) tempGroupList.get(i)).getTip1()); // ************************************
						mapArtikliI.put("SIFRA", ((Artikli) tempGroupList.get(i)).getSifri());
						mapArtikliI.put("NAZIV", ((Artikli) tempGroupList.get(i)).getNaziv());
						mapArtikliI.put("KOLICINA", ((Artikli) tempGroupList.get(i)).getKolicina().toString());
						mapArtikliI.put("CENA", ((Artikli) tempGroupList.get(i)).getCena().toString());
	//					LayoutInflater inflo = this.getLayoutInflater();
						mapArtikliI.put("+", "+" );  //  last INC value on 27 03 2014
						mapArtikliI.put("-", "-");  //  last INC value on 27 03 2014
						insideProducts.add(mapArtikliI);
					}
					
					Log.i("List 2 Info", Arrays.asList(insideProducts).toString());
					
					simpleCollectProduct2 = new SimpleAdapter(StartClass.this, insideProducts,	R.layout.list_lays, from, to){
	
						@Override
						public View getView(int position, View convertView, ViewGroup parent) {
							
							Context contxt = StartClass.this.getApplicationContext();
							
							LayoutInflater inflater = (LayoutInflater) contxt
						            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
						    final View rowView = inflater.inflate(R.layout.list_lays, parent, false);
						    
							 /* Font file is saved in path assets\fonts\ */
					        customTypeface = Typeface.createFromAsset(contxt.getAssets(), "fonts/zonelmak.ttf");
					        TextView text = (TextView) rowView.findViewById(R.id.ime_txt_r);
					        //TextView quant = (TextView) rowView.findViewById(R.id.kolicina_txt_r);
					        
					        /* Set custom typeface to text view you want to custom */
					        text.setTypeface(customTypeface );
					        
							return super.getView(position, rowView, parent);
						}
						
						
						
					};
					//simpleCollectProduct2.notifyDataSetChanged();
					tempListView2.setAdapter(simpleCollectProduct2);
					slider.toggle();
					
					
					//*************************************************END ***********************************************************
					
				} else {
//					List<HashMap<String, String>> insideProductsI = new ArrayList<HashMap<String, String>>();
//					menuCategories.clear();
					//*******************************************************************  Reduced list according to Search Text from list ***********************
					Log.v("THE GROUP MENU ITEM TEXT:", ((TextView)view.findViewById(R.id.menu_item_txt)).getText() + "");
					TextView t = (TextView) view.findViewById(R.id.menu_item_txt);
					Log.v("THE TEXT ITEM:", t.getText().toString());
					String textGroupString = t.getText().toString();
					ArrayList<Artikli> tempGroupList = db.getREDTipoviData(textGroupString);
					// setting the list 2 addapter data
					HashMap<String, String> mapArtikliI;
					for (int i = 0; i < tempGroupList.size(); i++) {
						mapArtikliI = new HashMap<String, String>();
						mapArtikliI.put("br", ((Artikli) tempGroupList.get(i)).getTip1()); // ************************************
						mapArtikliI.put("SIFRA", ((Artikli) tempGroupList.get(i)).getSifri());
						mapArtikliI.put("NAZIV", ((Artikli) tempGroupList.get(i)).getNaziv());
						mapArtikliI.put("KOLICINA", ((Artikli) tempGroupList.get(i)).getKolicina().toString());
						mapArtikliI.put("CENA", ((Artikli) tempGroupList.get(i)).getCena().toString());
	//					LayoutInflater inflo = this.getLayoutInflater();
						mapArtikliI.put("+", "+" );  //  last INC value on 27 03 2014
						mapArtikliI.put("-", "-");  //  last INC value on 27 03 2014
						insideProducts.add(mapArtikliI);
					}
					
					Log.i("List 2 Info", Arrays.asList(insideProducts).toString());
					
					simpleCollectProduct2 = new SimpleAdapter(StartClass.this, insideProducts,	R.layout.list_lays, from, to){
	
						@Override
						public View getView(int position, View convertView, ViewGroup parent) {
							
							Context contxt = StartClass.this.getApplicationContext();
							
							LayoutInflater inflater = (LayoutInflater) contxt
						            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
						    final View rowView = inflater.inflate(R.layout.list_lays, parent, false);
						    
							 /* Font file is saved in path assets\fonts\ */
					        customTypeface = Typeface.createFromAsset(contxt.getAssets(), "fonts/zonelmak.ttf");
					        TextView text = (TextView) rowView.findViewById(R.id.ime_txt_r);
					        //TextView quant = (TextView) rowView.findViewById(R.id.kolicina_txt_r);
					        
					        /* Set custom typeface to text view you want to custom */
					        text.setTypeface(customTypeface );
					        
							return super.getView(position, rowView, parent);
						}
					};
					//simpleCollectProduct2.notifyDataSetChanged();
					tempListView2.setAdapter(simpleCollectProduct2);
					slider.toggle();
					//*************************************************************************************************
				}
			}
			
		});
		db.checkDataBase();
				
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent register = new Intent(getApplicationContext(),
				RegisterSuccessfull.class);
		startActivity(register);
		this.finish();
	}

	public void ispratiOnClick(View view) {

		if (masaBr.getText().toString().equals(""))
			masaBr.setText("0");

		Integer masaBrojVnes = Integer.parseInt(masaBr.getText().toString());

		if ((masaBrojVnes <= 0) || ("".equals(masaBr.getText().toString()))) {
			AlertDialog.Builder alert = new AlertDialog.Builder(thisVar);
			alert.setTitle("Vnimanie!!!");
			alert.setMessage("Nemate selektirano broj na masa.\nDali sakate da gi zadrzite naracanite artikli "
					+ "\ni da vnesete broj na masa? - Vnesete broj na masa i pritisnete OK."
					+ "\nVo sprotivno pritisnete CANCEL.");

			final EditText input = new EditText(thisVar);
			input.setInputType(InputType.TYPE_CLASS_NUMBER);
			alert.setView(input);

			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int whichButton) {
							masaBr.setText(input.getText().toString());
							sendButton.performClick();  //  28 03 2014  perform send Order after masa vnes if masa is not set!
						}
					});

			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,	int whichButton) {
//							artikliNaracani.clear();
//							masaBr.setText("");
							masaBr.requestFocus();
//							tempListView1.setAdapter(simpleCollectProduct1);

						}
					});

			alert.show();
			return;
		}
		// Date and Time calculations: ****************************
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf.format(new Date());
		long ldate = System.currentTimeMillis();
		String time = new SimpleDateFormat("HH:mm").format(new Date(ldate));
		// *******************************************************************

		// ****************** TEST *******************************
		// final String iTopN = "5";
		// ******************** END TEST **************************

		// ************** Generalii setings **********************
		SharedPreferences sharedPreferences = getSharedPreferences("GeneraliiPrefs", Context.MODE_PRIVATE);
		String genFirma = sharedPreferences.getString("firmaKey", "101");
		String genProdav = sharedPreferences.getString("prodavKey", "1");
		String genKasa = sharedPreferences.getString("kasaKey", "k2");

		// ********************************************************

		// this field is needeth for SOAP action -> kreiraj_naracka
		final String proc_name = "KREIRAJ_NOV_PARAGON_RESTORAN";
		final String prodav = genProdav; // 1+"";//genProdav; //1
		// this fields is nedeth for SOAP action -> orderByAndroid
		final String firma = genFirma; // 101+"";// genFirma; // 101 -6- for
										// testing purposes!!!!
		final String kasa = genKasa; // "k"+2+"";//genKasa; // k3
		final String vraboten = vrabotenId; // vrabotenId;// "9" is the vraboten
											// broj in helix
		final String prodavnica = 1 + "";// genProdav; //1
		final String masa = masaBr.getText().toString();
		final String art_date = date; // DateTime.now().toLocalDate().toString()
										// +
		// "00:00:00.0"; //"2014-01-01 00:00:00.0"
		final String art_time = time; // Calendar.getInstance().getTime().toString();
		
			
		// with Ascnc call
			
		AsyncTask<Void, Void, Void> asT = new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				Log.i("Beffore: " ,"Before call");
				 dialog = new ProgressDialog(StartClass.this);
				 dialog.setMessage("Isprakjanje na naracanite produkti./...");
				 dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				 dialog.setCancelable(false);
				 dialog.show();
				
			}
			
			
			

			@Override
			protected Void doInBackground(Void... params) {
				Product product;
				//Service1 service = new Service1();
				StringBuffer productList = new StringBuffer();
				String productListResult = "";
				//boolean result = false;
				if(artikliNaracani.size()>0) {
					for (int i = 0; i < tempListView1.getCount(); i++) {
						
						double tempPrice = Double.parseDouble((String) ((HashMap) tempListView1.getItemAtPosition(i)).get("CENA"));
						double tempQuantity = Double.parseDouble((String) ((HashMap) tempListView1.getItemAtPosition(i)).get("KOLICINA"));
						String sifra = ((HashMap<String, String>) tempListView1.getItemAtPosition(i)).get("SIFRA");
						int cena = (int) tempPrice;
						int kolicina = (int) tempQuantity;
						product = new Product(sifra,kolicina,cena,art_date,art_time,opis);
						productList.append(product.toString());
						//b = sendSOAP(firma, kasa, vraboten, prodavnica, masa,sifra, kolicina, cena, art_date, art_time);
						//return null;
					}
					productListResult += productList.toString();
					b = sendSOAPI(pateka,firma, kasa, masa, prodav,vraboten ,productListResult);
				} else {
					return null;
				}
//				try {
//					b = service.orderByAndroidNew(pateka, firma, kasa, masa, prodavnica, vraboten, productList.toString());
//				} catch (Exception e) {
//					
//					e.printStackTrace();
//				}
				return null;
			}
			
			
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				Log.i("After: " ,"Affter call B value is: " + b);
				sendButton.setBackgroundColor(Color.BLACK);
				if (b) {
//					Toast.makeText(getApplicationContext(), "Vashata narachka e uspeshno ispratena...", Toast.LENGTH_LONG).show();
					//**********************************************************************************
					LayoutInflater inflater = getLayoutInflater();
	    			View layout = inflater.inflate(R.layout.custom_toast_ok,(ViewGroup) findViewById(R.id.toast_layout_root_));
	    			TextView text = (TextView) layout.findViewById(R.id.text_);
	    			String toastMessage = "Vashata narachka e uspeshno ispratena...";
	    			text.setText(toastMessage);
	    			Toast toast = new Toast(getApplicationContext());
	    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	    			toast.setDuration(Toast.LENGTH_LONG);
	    			toast.setView(layout);
	    			toast.show();
					//************************************************************************************** 28 03 2014
					masaBr.setText("");
					masaBr.requestFocus();
					artikliNaracani.clear();
					tempListView1.setAdapter(simpleCollectProduct1);
					sumNum.setText("0");
					searchArtInp.setText("");
					
					
				} else {
//					Toast.makeText(getApplicationContext(), "Vashata narachka e NEUSPESHNO ispratena...", Toast.LENGTH_LONG).show();
					
					//**********************************************************************************  28 03 2014
					LayoutInflater inflater = getLayoutInflater();
	    			View layout = inflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.toast_layout_root));
	    			TextView text = (TextView) layout.findViewById(R.id.text);
	    			String toastMessage = "Vashata narachka e NEUSPESHNO ispratena...";
	    			text.setText(toastMessage);
	    			Toast toast = new Toast(getApplicationContext());
	    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	    			toast.setDuration(Toast.LENGTH_LONG);
	    			toast.setView(layout);
	    			toast.show();
					//**************************************************************************************
				}
				sendButton.setTextColor(Color.WHITE);
				dialog.dismiss();
			}
			
		};
		sendButton.setBackgroundColor(Color.RED);
		asT.execute();

	}

	protected boolean sendSOAPI(String pateka2, String firma, String kasa,String masa, String prodavnica,String vraboten, String productListResult) {


		Log.i("The no. Of KASA: " ,"Broj na KASA e: " + kasa + "");
		Log.i("The no. Of FIRMA: " ,"Broj na FIRMA e: " + firma + "");
		Log.i("The no. Of VRABOTEN: " ,"Broj na VRABOTEN e: " + vraboten + "");
		Log.i("The no. Of PRODAVNICA: " ,"Broj na PRODAVNICA e: " + prodavnica + "");
		Log.i("The no. Of MASA: " ,"Broj na MASA e: " + masa + "");
		Log.i("The no. Of SIFRA: " ,"Broj na SIFRA e: " + productListResult + "");


		
		SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME_NEW);

		PropertyInfo pi1 = new PropertyInfo();
		pi1.setName("pateka");
		pi1.setValue(pateka2);
		pi1.setType(String.class);
		Request.addProperty(pi1);

		PropertyInfo pi2 = new PropertyInfo();
		pi2.setName("firma");
		pi2.setValue(firma);
		pi2.setType(String.class);
		Request.addProperty(pi2);

		PropertyInfo pi3 = new PropertyInfo();
		pi3.setName("kasa");
		pi3.setValue(kasa);
		pi3.setType(String.class);
		Request.addProperty(pi3);

		PropertyInfo pi4 = new PropertyInfo();
		pi4.setName("masa");
		pi4.setValue(masa);
		pi4.setType(String.class);
		Request.addProperty(pi4);

		PropertyInfo pi5 = new PropertyInfo();
		pi5.setName("prodav");
		pi5.setValue(prodavnica);
		pi5.setType(String.class);
		Request.addProperty(pi5);

		PropertyInfo pi6 = new PropertyInfo();
		pi6.setName("vraboten");
		pi6.setValue(vraboten);
		pi6.setType(String.class);
		Request.addProperty(pi6);

		PropertyInfo pi7 = new PropertyInfo();
		pi7.setName("products");
		pi7.setValue(productListResult);
		pi7.setType(String.class);
		Request.addProperty(pi7);

//		PropertyInfo pi8 = new PropertyInfo();
//		pi8.setName("kolicina");
//		pi8.setValue(kolicina);
//		pi8.setType(int.class);
//		Request.addProperty(pi8);
//
//		PropertyInfo pi9 = new PropertyInfo();
//		pi9.setName("cena");
//		pi9.setValue(cena);
//		pi9.setType(int.class);
//		Request.addProperty(pi9);
//
//		PropertyInfo pi10 = new PropertyInfo();
//		pi10.setName("art_dat");
//		pi10.setValue(art_date); // cal.getTime()
//		pi10.setType(String.class);
//		Request.addProperty(pi10);
//
//		PropertyInfo pi11 = new PropertyInfo();
//		pi11.setName("art_time");
//		pi11.setValue(art_time);
//		pi11.setType(String.class);
//		Request.addProperty(pi11);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		envelope.setAddAdornments(false);
		// envelope.implicitTypes = true;
		envelope.setOutputSoapObject(Request);

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			Log.v("In the try catch block :  ", "BEFORE ANY METHOD CALL.....");
			// Log.v("DateTime obj is---> :  ", art_date +"");
//			Log.v("Date obj is---> :  ", art_date + "");
//			Log.v("Time obj is---> :  ", art_time + "");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			try {
				Log.v("Date obj is---> :  ", envelope.getResponse() + "");
				if (Boolean.parseBoolean(envelope.getResponse() + "")) {
					return true;
				} else {
					return false;
				}
			} catch (SoapFault e) {
				Log.i("SOAP", "SOAP FAULT EXCEPTION!!!");
				return false;
			}

		} catch (Exception e) {
			// Log.v("In the Exception---> :  ", envelope.getResponse()+"");
			return false;
		}
	}

	public void clickIzlez(View view) {
		Intent backToSuccess = new Intent(getApplicationContext(),
				RegisterSuccessfull.class);
		startActivity(backToSuccess);
		this.finish();
	}


	/*
	 * MASI [ TABLES ] new method click handler implementation for showing the tables in the restorant and order for selected table
	 *  changed method implementation on 10 04 2014
	 */
	public void clickNovaMasa(View view) {
		GetMasiData masiData = new GetMasiData();
		masiData.execute();
		
//		Intent masa = new Intent(getApplicationContext(),Masi.class);
//		bundle.putStringArrayList("masi", masiListStrings);
//		masa.putExtras(bundle);
//		startActivity(masa);
		
	}// End of new method click handler implementation!!!!!!!!!!!!!!!
	
	
	

	public void clickBrishi(View view) {
		int selected = tempListView1.getSelectedItemPosition();
		artikliNaracani.remove(selected);
		tempListView1.setAdapter(simpleCollectProduct1);
	}

	public boolean sendSOAP(String firma, String kasa, String vraboten,
			String prodavnica, String masa, String sifra, int kolicina,
			int cena, String art_date, String art_time) {
		
//		Log.i("The no. Of KASA: " ,"Broj na KASA e: " + kasa + "");
//		Log.i("The no. Of FIRMA: " ,"Broj na FIRMA e: " + firma + "");
//		Log.i("The no. Of VRABOTEN: " ,"Broj na VRABOTEN e: " + vraboten + "");
//		Log.i("The no. Of PRODAVNICA: " ,"Broj na PRODAVNICA e: " + prodavnica + "");
//		Log.i("The no. Of MASA: " ,"Broj na MASA e: " + masa + "");
//		Log.i("The no. Of SIFRA: " ,"Broj na SIFRA e: " + sifra + "");
//		Log.i("The no. Of KOLICINA: " ,"Broj na KOLICINA e: " + kolicina + "");
//		Log.i("The no. Of CENA: " ,"Broj na CENA e: " + cena + "");
		
		SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME_NEW);

		PropertyInfo pi1 = new PropertyInfo();
		pi1.setName("pateka");
		pi1.setValue(pateka);
		pi1.setType(String.class);
		Request.addProperty(pi1);

		PropertyInfo pi2 = new PropertyInfo();
		pi2.setName("firma");
		pi2.setValue(firma);
		pi2.setType(String.class);
		Request.addProperty(pi2);

		PropertyInfo pi3 = new PropertyInfo();
		pi3.setName("kasa");
		pi3.setValue(kasa);
		pi3.setType(String.class);
		Request.addProperty(pi3);

		PropertyInfo pi4 = new PropertyInfo();
		pi4.setName("vraboten");
		pi4.setValue(vraboten);
		pi4.setType(String.class);
		Request.addProperty(pi4);

		PropertyInfo pi5 = new PropertyInfo();
		pi5.setName("prodavnica");
		pi5.setValue(prodavnica);
		pi5.setType(String.class);
		Request.addProperty(pi5);

		PropertyInfo pi6 = new PropertyInfo();
		pi6.setName("masa");
		pi6.setValue(masa);
		pi6.setType(String.class);
		Request.addProperty(pi6);

		PropertyInfo pi7 = new PropertyInfo();
		pi7.setName("sifra");
		pi7.setValue(sifra);
		pi7.setType(String.class);
		Request.addProperty(pi7);

		PropertyInfo pi8 = new PropertyInfo();
		pi8.setName("kolicina");
		pi8.setValue(kolicina);
		pi8.setType(int.class);
		Request.addProperty(pi8);

		PropertyInfo pi9 = new PropertyInfo();
		pi9.setName("cena");
		pi9.setValue(cena);
		pi9.setType(int.class);
		Request.addProperty(pi9);

		PropertyInfo pi10 = new PropertyInfo();
		pi10.setName("art_dat");
		pi10.setValue(art_date); // cal.getTime()
		pi10.setType(String.class);
		Request.addProperty(pi10);

		PropertyInfo pi11 = new PropertyInfo();
		pi11.setName("art_time");
		pi11.setValue(art_time);
		pi11.setType(String.class);
		Request.addProperty(pi11);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		envelope.setAddAdornments(false);
		// envelope.implicitTypes = true;
		envelope.setOutputSoapObject(Request);

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			Log.v("In the try catch block :  ", "BEFORE ANY METHOD CALL.....");
			// Log.v("DateTime obj is---> :  ", art_date +"");
			Log.v("Date obj is---> :  ", art_date + "");
			Log.v("Time obj is---> :  ", art_time + "");
			androidHttpTransport.call(SOAP_ACTION, envelope);

			try {
				if (Boolean.parseBoolean(envelope.getResponse() + "")) {
					return true;
				} else {
					return false;
				}
			} catch (SoapFault e) {
				Log.i("SOAP", "SOAP FAULT EXCEPTION!!!");
				return false;
			}

		} catch (Exception e) {
			// Log.v("In the Exception---> :  ", envelope.getResponse()+"");
			return false;
		}
	}
	

	public Context getThisApplicationContext() {
		return thisVar;
	}

	public void saveCurrentOrder(ArrayList<String> unOrderedProducts) {
		Context context = getApplicationContext();
		String masaBroj = masaBr.getText().toString();
		SaveOrderModel saveOrder = new SaveOrderModel(masaBroj,
				unOrderedProducts, context);
		saveOrder.save();
	}
				// IncAndDecClick  *****************************************
	public int IncAndDecClick(View view) {
		Log.i("And the View is: ", view + "");
		int i = view.getId();
		int kolI = 0;
		int kolD = 0;
		
		switch(i) {
			case R.id.menu_item_txt:
				Log.w("TEXTVIEW1", "In first text view: " + ((TextView)((View)((View)view.getParent()).getParent()).findViewWithTag("kolicina")).getText() );
				TextView t = ((TextView)((View)((View)view.getParent()).getParent()).findViewWithTag("kolicina"));
				try {
					kolI = Integer.parseInt(t.getText().toString());
					Log.w("WRONG NUM", kolI + "" );
					kolI++;
					//Toast.makeText(getApplicationContext(), kol, 4000).show();
//					sum += Integer.parseInt(((TextView)((View)((View)view.getParent()).getParent()).findViewWithTag("cena")).toString())*kol;
//					sumNum.setText(sum+"");
					//simpleCollectProduct1.notifyDataSetChanged();
					//return Integer.parseInt(t.getText().toString());
					return kolI;
				} catch (Exception e) {
					Log.w("WRONG NUM", "In NUM Exception view ONE" );
					
				}
				
				break;
			case R.id.textView2:
				Log.w("TEXTVIEW2", "In second text view: " + view.getParent());
				TextView tt = ((TextView)((View)((View)view.getParent()).getParent()).findViewWithTag("kolicina"));
				try {
					kolD = Integer.parseInt(tt.getText().toString());
					Log.w("WRONG NUM", kolD + "" );
					kolD--;
//					sum -= Integer.parseInt(((TextView)((View)((View)view.getParent()).getParent()).findViewWithTag("cena")).toString())*kol;
//					sumNum.setText(sum+"");
					if(kolD>0) {
						//Toast.makeText(getApplicationContext(), kol, 4000).show();
						tt.setText(String.valueOf(kolD));
//						return Integer.parseInt(tt.getText().toString());
						return kolD;
					} else {
						// REMOVE THE ELEMENT FROM LIST ONE!!!!!!!!!!!!!
						//tempListView1.removeViewAt();
					}
					
				} catch (Exception e) {
					Log.w("WRONG NUM", "In SECOND NUM Exception view " );
				}
				
				break;
			default:
				Log.w("DEFAULT_2", "In default text view: " + view.getParent());
				return 1;
		}
		return 1;
	}
	
	private class GetMasiData extends AsyncTask<Void, Void, ArrayList<String>> {
		
		ArrayList<Masis> masiList = null;
		StringBuilder paragoniList = new StringBuilder();
		
		
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
			// Showing progress dialog
            pDialog = new ProgressDialog(StartClass.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
		}

		

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			masiList = new ArrayList<Masis>();
			masiListStrings = new ArrayList<String>();
			
			
			SoapObject Request = new SoapObject(NAMESPACE, MASI_METHOD_NAME);

			PropertyInfo pi1 = new PropertyInfo();
			pi1.setName("sql");
			pi1.setValue(MASI_QUERY);
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
				androidHttpTransport.call(MASI_SOAP_ACTION, envelope);
				Log.v("In the final after getting request value :  ", 
						//((SoapObject)
								((SoapObject)
										((SoapObject)
												((SoapObject)
														envelope.getResponse()).getProperty(1)
														
														).getProperty(0)
														//).getAttribute("Table1")
														).getProperty(0)
														//).getProperty(0) 
																	+ "");
				Masis masiObj = null;
				SoapObject temp =// (SoapObject) 
						//(
								(SoapObject)((SoapObject)((SoapObject) envelope.getResponse()).getProperty(1)).getProperty(0);
								//).getProperty(0);
				Log.w("The object from request is: ", temp.getPropertyCount()+"");
//				// getting the data from SOAP object
				for (int i = 0; i < temp.getPropertyCount(); i++) {
					
					SoapObject tempI = (SoapObject) temp.getProperty(i);
					
					String kasa = tempI.getProperty(0).toString();
					String broj = tempI.getProperty(1).toString();
					String paragId = tempI.getProperty(2).toString();
					String datum = tempI.getProperty(3).toString();
					String vreme = tempI.getProperty(4).toString();
					String vraboten = tempI.getProperty(5).toString();
					String zatvoren = tempI.getProperty(6).toString();
					String tipPlakanje = tempI.getProperty(7).toString();
					String iznos = tempI.getProperty(8).toString();
					String firma = tempI.getProperty(9).toString();
					String prodav = tempI.getProperty(10).toString();
					String zatProm = tempI.getProperty(11).toString();
					String masa = tempI.getProperty(12).toString();
					String napl = tempI.getProperty(13).toString();
					String zakl = tempI.getProperty(14).toString();
					String zanosenje = tempI.getProperty(15).toString();
					String guid = tempI.getProperty(16).toString();
					String flag = tempI.getProperty(17).toString();
					
					masiObj = new Masis();
					
					if(masiObj != null) {
						//setting the data to masi object
						masiObj.setKasa(kasa);
						masiObj.setBroj(broj);
						masiObj.setParagId(paragId);
						masiObj.setDatum(datum);
						masiObj.setVreme(vreme);
						masiObj.setVraboten(vraboten);
						masiObj.setZatvoren(zatvoren);
						masiObj.setTipPlakanje(tipPlakanje);
						masiObj.setIznos(iznos);
						masiObj.setFirma(firma);
						masiObj.setProdav(prodav);
						masiObj.setZatProm(zatProm);
						masiObj.setMasa(masa);
						masiObj.setNapl(napl);
						masiObj.setZakl(zakl);
						masiObj.setZanosenje(zanosenje);
						masiObj.setGuid(guid);
						masiObj.setFlag(flag);
						Log.w("The MASI object from request is: ", masiObj.toString());
						
						masiList.add(masiObj);
						masiListStrings.add(masiObj.getMasa());
						
						paragoniList.append(masiObj.getParagId()+",");
						
					} else {
						Log.w("The MASI object from request is: ", "SOMETHING IS WRONG>.....  THERE IS NO MASI........");
					}
					
					
					Log.w("The MASI object from request is: ", Arrays.asList(masiListStrings) + "");
					
				}
				
				
				
				
				
				
			} catch(Exception e) {
				// for now just eat the value...
				Log.v("In the final after getting request value :  ",  "IN the EXCEPTION PART OF THE METOD CALL....");
			}
			Log.w("The MASI object from request is: ", Arrays.asList(masiListStrings) + "");
			return masiListStrings;
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			if(pDialog.isShowing()) {
				pDialog.dismiss();
			}
			Intent masa = new Intent(getApplicationContext(),Masi.class);
//			Bundle bundle = new Bundle();
//			//bundle.putParcelableArrayList("masi", result);
//			if(masiList != null) {
//				Log.v("GOOD :  ",  "THE BUNDLE RESULT IS NOOOOOOOOOOOT NULLL....");
//				bundle.putParcelableArrayList("masi", masiList);
//			} else {
//				Log.v("NoT GOOD :  ",  "THE BUNDLE RESULT IS NULLL....");
//			}
			bundle.putStringArrayList("masi", masiListStrings);
			//bundle.putString("paragoniid:" , paragoniList.toString());
			Log.v("PARAGONI BUNDLE----- :  ",  Arrays.asList(paragoniList)+"");
			masa.putExtra("paragon", paragoniList.toString());
			masa.putExtra("masiData",bundle);
			//Log.v("PARAGONI BUNDLE----- :  ",  bundle.getString("paragoniId"));
			startActivity(masa);
		}
		
	}
	
	

}



