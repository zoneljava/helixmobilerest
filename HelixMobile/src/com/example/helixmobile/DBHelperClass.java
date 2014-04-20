package com.example.helixmobile;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import com.example.model.Artikli;
import com.example.model.Artiklis;
import com.example.model.Tipovi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

public class DBHelperClass extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.helixmobile/databases/";
 
    private static String DB_NAME = "hm.sqlite";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
    
    //public boolean isOpenMyDB = myDataBase.isOpen();
    
    private String Tabela = "KORISNICI";
    private String id = "_id";
    private String korisnik = "KORISNIK";
    private String password = "LOZINKA";
    
    //*********************
    private SQLiteDatabase db;
    //*********************
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the
     *  application assets and resources.
     * @param context
     */
    public DBHelperClass(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
           //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
        		
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    public void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
    	
    	
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
    
    public SQLiteDatabase getOpenDatabase() {
    	return myDataBase;
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
//		String createArtikli = "CREATE TABLE ARTIKLI (_id NUMERIC, SIFRA NVARCHAR(20), NAZIV NVARCHAR(50)," +
//				" KOLICINA FLOAT, EM NVARCHAR(5), TARIFA NVARCHAR(3), " +
//				"PROCENT FLOAT, CENA FLOAT, VIRT_KOLICINA FLOAT, VIRT_CENA FLOAT, VIRT_EM NVARCHAR(5), STRANSKI SMALLINT, NAB_CENA FLOAT);";
//		db.execSQL(createArtikli);
//		String delQuery = "DELETE * FROM ARTIKLI";
		//db.delete("ARTIKLI", null, null);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	
	
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
	
	public boolean getVerificationFor(String korisnik1,String lozinka1) {
		if(myDataBase.isOpen()) {
			Log.v("The_values_created_for_user_afterClick: " , korisnik1 + " || " + lozinka1);
			Cursor cursor = myDataBase.query(true, Tabela, new String[] {id,korisnik,password}, korisnik.toLowerCase() + "='" + korisnik1+ "' and " + password + "='" + lozinka1+"'", null, null, null, null, null);
			if(cursor != null) {
				cursor.moveToFirst();
				try {
					String user = cursor.getString(1);
					String pwd = cursor.getString(2);
					if(user.equals(korisnik1)&&pwd.equals(lozinka1)) {
						return true;
					}
				} catch (Exception e) {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;
	}

    //****************************************************** 1 option CRUD END
	
	 //****************************************************** 
		// Second Option CRUD :
		
	
	//  Create:
		//HashMap<String, String> queryValues
		
	
	public void insertKorisnici(Korisnik korisnik) {
		  SQLiteDatabase database = this.getWritableDatabase();
		  ContentValues values = new ContentValues();
		  values.put("id", korisnik.getId());
		  values.put("SIFRA", korisnik.getSifra());
		  values.put("NAZIV", korisnik.getNaziv());
		  values.put("KORISNIK", korisnik.getUser());
		  values.put("LOZINKA", korisnik.getPassword());
		  values.put("PRIVILEGIJA", korisnik.getPrivilegija());
		  
		  database.insert("KORISNICI", null, values);
		  database.close();
	}
	
		//  For Tipovi:
	public void insertObject(Artiklis artikli) {
		  SQLiteDatabase database = this.getWritableDatabase();
		  ContentValues values = new ContentValues();
		  values.put("SIFRA", artikli.getSifra());
		  values.put("NAZIV", artikli.getNaziv());
		  values.put("KOLICINA", artikli.getPrkol());
		  values.put("EM", artikli.getEm());
		  values.put("TARIFA", artikli.getTarifa());
		  values.put("NAB_CENA", artikli.getNabcena());
		  //values.put("PROCENT", artikli.get);
		  values.put("CENA", artikli.getEdprcena());
		  values.put("VIRT_KOLICINA", artikli.getVirtkol());
		  values.put("VIRT_CENA", artikli.getVirtcena());
		  values.put("VIRT_EM", artikli.getVirtem());
		  values.put("STRANSKI", artikli.getStranski());
		  
		  // Added on 07 04 2014 [ aquaring TIP1 field for writing to database to be general menu possible ]:
		  values.put("TIP1", artikli.getTip1());
		  
		  database.insert("ARTIKLI", null, values);
		  database.close();
	}
	
		//  For Tipovi:
	public void insertTipovi(Tipovi tipovi) {
		  SQLiteDatabase database = this.getWritableDatabase();
		  ContentValues values = new ContentValues();
		  values.put("FIRMA", tipovi.getFirma());
		  values.put("ID1", tipovi.getId1());
		  values.put("ID2", tipovi.getId2());
		  values.put("Id3", tipovi.getId3());
		  values.put("ID4", tipovi.getId4());
		  values.put("ID5", tipovi.getId5());
		  //values.put("PROCENT", artikli.get);
		  values.put("NIVO", tipovi.getNivo());
		  values.put("OPIS", tipovi.getOpis());
		  values.put("TIPOVI_ID", tipovi.getTipoviId());
		  database.insert("ARTIKLI", null, values);
		  database.close();
	}
	
	
	public void insertNarackiNeisprateni(String masa,ArrayList<String> naracani) {
		ContentValues values = new ContentValues();
		values.put("MASA", masa);
		int size = naracani.size();
		StringBuilder build = new StringBuilder();
		for(int i = 0; i < size; i++) {
			build.append(naracani.get(i));
			if (i < size-1 ) build.append(",");
			Log.i("Object is ==================>", (naracani.get(i))+"");
		}
		values.put("ARTIKLI", build.toString());
		getOpenDatabase().insert("TEMPORARY_DATA", null, values);
	}
	
	//  Read:
	public HashMap<String, ArrayList<String>> getOrderedArtikli(String masa) {
		  HashMap<String, ArrayList<String>> ordered = new HashMap<String, ArrayList<String>>();
		  SQLiteDatabase database = this.getReadableDatabase();
		  
		 //TODO  exchange compatible values:
		  String selectQuery = "SELECT * FROM TEMPORARY_DATA where MASA='"+masa+"'";
		  Cursor cursor = database.rawQuery(selectQuery, null);
		  if (cursor.moveToFirst()) {
			  //int count = 0;
		    do {
		    	ordered.put( cursor.getString(1), (ArrayList) Arrays.asList(cursor.getString(2).split(","))  );  //"MASA-" + 
		    } while (cursor.moveToNext());
		  }           
		  return ordered;
	} 
	
	public static boolean pendingOrders(Context context) {
		DBHelperClass dbHC = new DBHelperClass(context);
		SQLiteDatabase dbP = dbHC.getReadableDatabase();
		String queryPending = "SELECT * FROM TEMPORARY_DATA";
		Cursor c;
		try {
			c = dbP.rawQuery(queryPending, null);
		} catch (Exception e) {
			return false;
		}
		
		int sizeOfPendingOrders = c.getCount();
		if(sizeOfPendingOrders > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public HashMap<String, ArrayList<String>> getOrderedArtikli() {
		  HashMap<String, ArrayList<String>> ordered = new HashMap<String, ArrayList<String>>();
		  SQLiteDatabase database = this.getReadableDatabase();
		  
		 //TODO  exchange compatible values:
		  String selectQuery = "SELECT * FROM TEMPORARY_DATA";
		  Cursor cursor = database.rawQuery(selectQuery, null);
		  if (cursor.moveToFirst()) {
			  //int count = 0;
		    do {
		    	ordered.put( cursor.getString(2), (ArrayList) Arrays.asList(cursor.getString(3).split(":"))  );  
		    } while (cursor.moveToNext());
		  }           
		  return ordered;
	} 
	
	// Update:
	public int updateObject(HashMap<String, String> queryValues) {
		  SQLiteDatabase database = this.getWritableDatabase(); 
		  
		  //TODO  exchange compatible values:
		  ContentValues values = new ContentValues();
		  values.put("animalName", queryValues.get("animalName"));
		  return database.update("animals", values, "animalId" + " = ?", new String[] { queryValues.get("animalId") });
	}
	
	//Delete:
	public void deleteObject(String id) {
		  Log.d("Delete Log: ------>","delete");
		  SQLiteDatabase database = this.getWritableDatabase();  
		  
		  //TODO  exchange compatible values:
		  String deleteQuery = "DELETE FROM  animals where animalId='"+ id +"'";
		  Log.d("query",deleteQuery);   
		  database.execSQL(deleteQuery);
	}
	
    // Getting All Artikli Data:
	public ArrayList<Artikli> getAllArtikliData(String table) {
		ArrayList<Artikli> artikliList = new ArrayList<Artikli>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + table;
		
		Artikli artikli;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				artikli = new Artikli();
				artikli.setId(cursor.getColumnIndex("_id"));
				artikli.setSifri(cursor.getString(1));
				artikli.setNaziv(cursor.getString(2));
				artikli.setKolicina(Float.parseFloat(cursor.getString(3)));
				artikli.setEm(cursor.getString(4));
				artikli.setTarifa(cursor.getString(5));
//				artikli.setNab_cena(cursor.getFloat(6));
				artikli.setProcent(cursor.getFloat(6));
				artikli.setCena(cursor.getFloat(7));
				artikli.setVirt_kolicina(cursor.getFloat(8));
				artikli.setVirt_cena(cursor.getFloat(9));
				artikli.setVirt_em(cursor.getString(10));
				artikli.setStranski(cursor.getInt(11));
				artikli.setNab_cena(cursor.getFloat(12));
				// Adding contact to list
				artikliList.add(artikli);
			} while (cursor.moveToNext());
		}

		// return contact list
		return artikliList;
	}
	
	// GET TIPOVI DATA
	public ArrayList<Tipovi> getAllTipoviData(String table) {
		ArrayList<Tipovi> tipoviList = new ArrayList<Tipovi>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + table;
		
		Tipovi tipovi;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				tipovi = new Tipovi();
				tipovi.set_id(cursor.getColumnIndex("id"));
				tipovi.setFirma(cursor.getString(1));
				tipovi.setId1(cursor.getString(2));
				tipovi.setId2(cursor.getString(3));
				tipovi.setId3(cursor.getString(4));
				tipovi.setId4(cursor.getString(5));
//				artikli.setNab_cena(cursor.getFloat(6));
				tipovi.setId5(cursor.getString(6));
				tipovi.setNivo(cursor.getString(7));
				tipovi.setOpis(cursor.getString(8));
				tipovi.setTipoviId(cursor.getString(9));
				// Adding contact to list
				tipoviList.add(tipovi);
			} while (cursor.moveToNext());
		}

		// return contact list
		return tipoviList;
	}
	// GEt reduced list of artikli:
	public ArrayList<Artikli> getREDTipoviData(String tip) {
		ArrayList<Artikli> artikliList = new ArrayList<Artikli>();
		// Select All Query
		String selectQuery = "SELECT ID1 FROM TIPOVI WHERE TIPOVI_ID='" + tip + "'";
		String selectFromId = "SELECT * FROM ARTIKLI WHERE TIP1='";
		
		Artikli artikli;
		
		SQLiteDatabase db = this.getWritableDatabase();
		String tipID = "";
		Cursor c = db.rawQuery(selectQuery, null);
		 
		c.moveToFirst();
		tipID += c.getString(0);
		Log.i("RESULT FROM QUERY C:", tipID);
		selectFromId += tipID + "'";
		SQLiteDatabase dbI = this.getWritableDatabase();
		Cursor cursor = dbI.rawQuery(selectFromId, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				artikli = new Artikli();
				artikli.setId(cursor.getColumnIndex("_id"));
				artikli.setSifri(cursor.getString(1));
				artikli.setNaziv(cursor.getString(2));
				artikli.setKolicina(Float.parseFloat(cursor.getString(3)));
				artikli.setEm(cursor.getString(4));
				artikli.setTarifa(cursor.getString(5));
//				artikli.setNab_cena(cursor.getFloat(6));
				artikli.setProcent(cursor.getFloat(6));
				artikli.setCena(cursor.getFloat(7));
				artikli.setVirt_kolicina(cursor.getFloat(8));
				artikli.setVirt_cena(cursor.getFloat(9));
				artikli.setVirt_em(cursor.getString(10));
				artikli.setStranski(cursor.getInt(11));
				artikli.setNab_cena(cursor.getFloat(12));
				// Adding contact to list
				artikliList.add(artikli);
			} while (cursor.moveToNext());
		}

		// return contact list
		return artikliList;
	}
	
	// GEt searched from 'NAZIV' list of artikli:
		public ArrayList<Artikli> getSearchedArtikliData(String nazivPart) {
			ArrayList<Artikli> artikliList = new ArrayList<Artikli>();
			// Select All Query
			String selectQuery = "SELECT * FROM ARTIKLI WHERE NAZIV LIKE '%" + nazivPart + "%'";

			Artikli artikli;
			
			Log.i("RESULT FROM QUERY: ", selectQuery);
			SQLiteDatabase dbI = this.getWritableDatabase();
			Cursor cursor = dbI.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					artikli = new Artikli();
					artikli.setId(cursor.getColumnIndex("_id"));
					artikli.setSifri(cursor.getString(1));
					artikli.setNaziv(cursor.getString(2));
					artikli.setKolicina(Float.parseFloat(cursor.getString(3)));
					artikli.setEm(cursor.getString(4));
					artikli.setTarifa(cursor.getString(5));
//					artikli.setNab_cena(cursor.getFloat(6));
					artikli.setProcent(cursor.getFloat(6));
					artikli.setCena(cursor.getFloat(7));
					artikli.setVirt_kolicina(cursor.getFloat(8));
					artikli.setVirt_cena(cursor.getFloat(9));
					artikli.setVirt_em(cursor.getString(10));
					artikli.setStranski(cursor.getInt(11));
					artikli.setNab_cena(cursor.getFloat(12));
					// Adding contact to list
					artikliList.add(artikli);
				} while (cursor.moveToNext());
			}

			// return contact list
			Log.i("ARTIKLI INSIDE DBHELPER:", artikliList + "");
			return artikliList;
		}
	
		// GEt searched from 'SIFRA' list of artikli:
				public ArrayList<Artikli> getSearchedSifraArtikliData(String nazivPart) {
					ArrayList<Artikli> artikliList = new ArrayList<Artikli>();
					// Select All Query
					String selectQuery = "SELECT * FROM ARTIKLI WHERE SIFRA LIKE '%" + nazivPart + "%'";

					Artikli artikli;
					
					Log.i("RESULT FROM QUERY: ", selectQuery);
					SQLiteDatabase dbI = this.getWritableDatabase();
					Cursor cursor = dbI.rawQuery(selectQuery, null);

					// looping through all rows and adding to list
					if (cursor.moveToFirst()) {
						do {
							artikli = new Artikli();
							artikli.setId(cursor.getColumnIndex("_id"));
							artikli.setSifri(cursor.getString(1));
							artikli.setNaziv(cursor.getString(2));
							artikli.setKolicina(Float.parseFloat(cursor.getString(3)));
							artikli.setEm(cursor.getString(4));
							artikli.setTarifa(cursor.getString(5));
//							artikli.setNab_cena(cursor.getFloat(6));
							artikli.setProcent(cursor.getFloat(6));
							artikli.setCena(cursor.getFloat(7));
							artikli.setVirt_kolicina(cursor.getFloat(8));
							artikli.setVirt_cena(cursor.getFloat(9));
							artikli.setVirt_em(cursor.getString(10));
							artikli.setStranski(cursor.getInt(11));
							artikli.setNab_cena(cursor.getFloat(12));
							// Adding contact to list
							artikliList.add(artikli);
						} while (cursor.moveToNext());
					}

					// return contact list
					Log.i("ARTIKLI INSIDE DBHELPER:", artikliList + "");
					return artikliList;
				}
		
	public int sizeOfTipoviTable() {
		String query = "SELECT COUNT(*) FROM TIPOVI";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	
	public ArrayList<Tipovi> getAllGroupTipoviData(String textGroup) {
		ArrayList<Tipovi> groupMenuList = new ArrayList<Tipovi>();
		String query = "SELECT ID1 FROM TIPOVI WHERE ID1='" + textGroup + "'";
		Tipovi tipovi;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						tipovi = new Tipovi();
//						tipovi.set_id(cursor.getColumnIndex("id"));
//						tipovi.setFirma(cursor.getString(1));
						tipovi.setId1(cursor.getString(2));
//						tipovi.setId2(cursor.getString(3));
//						tipovi.setId3(cursor.getString(4));
//						tipovi.setId4(cursor.getString(5));
//						artikli.setNab_cena(cursor.getFloat(6));
//						tipovi.setId5(cursor.getString(6));
//						tipovi.setNivo(cursor.getString(7));
//						tipovi.setOpis(cursor.getString(8));
//						tipovi.setTipoviId(cursor.getString(9));
						// Adding contact to list
						groupMenuList.add(tipovi);
					} while (cursor.moveToNext());
				}
		return groupMenuList;
	}
	
	//******************************************************* second option CRUD end.
}