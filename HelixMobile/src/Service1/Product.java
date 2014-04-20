package Service1;

//------------------------------------------------------------------------------
// <wsdl2code-generated>
//    This code was generated by http://www.wsdl2code.com version  2.5
//
// Date Of Creation: 4/16/2014 2:21:23 PM
//    Please dont change this code, regeneration will override your changes
//</wsdl2code-generated>
//
//------------------------------------------------------------------------------
//
//This source code was auto-generated by Wsdl2Code  Version
//
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import java.util.Hashtable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

public class Product implements KvmSerializable {
    
    public String sifra;
    public float kolicina;
    public float cena;
    public String aRT_Datum;
    public String aRT_Vreme;
    public String notes;
    public String device;
    
    public Product(){}
    
    public Product(String sifra, int kolicina, int cena,String art_date,String art_time,String opis) {
    	this.sifra = sifra;
    	this.kolicina = kolicina;
    	this.cena = cena;
    	this.aRT_Datum = art_date;
    	this.aRT_Vreme = art_time;
    	this.notes = opis;
    }
    
    public Product(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("Sifra"))
        {
            Object obj = soapObject.getProperty("Sifra");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                sifra = j.toString();
            }else if (obj!= null && obj instanceof String){
                sifra = (String) obj;
            }
        }
        if (soapObject.hasProperty("Kolicina"))
        {
            Object obj = soapObject.getProperty("Kolicina");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                kolicina = Float.parseFloat(j.toString());
            }else if (obj!= null && obj instanceof Number){
                kolicina = (Integer) obj;
            }
        }
        if (soapObject.hasProperty("Cena"))
        {
            Object obj = soapObject.getProperty("Cena");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                cena = Float.parseFloat(j.toString());
            }else if (obj!= null && obj instanceof Number){
                cena = (Integer) obj;
            }
        }
        if (soapObject.hasProperty("ART_Datum"))
        {
            Object obj = soapObject.getProperty("ART_Datum");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                aRT_Datum = j.toString();
            }else if (obj!= null && obj instanceof String){
                aRT_Datum = (String) obj;
            }
        }
        if (soapObject.hasProperty("ART_Vreme"))
        {
            Object obj = soapObject.getProperty("ART_Vreme");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                aRT_Vreme = j.toString();
            }else if (obj!= null && obj instanceof String){
                aRT_Vreme = (String) obj;
            }
        }
        if (soapObject.hasProperty("Notes"))
        {
            Object obj = soapObject.getProperty("Notes");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                notes = j.toString();
            }else if (obj!= null && obj instanceof String){
                notes = (String) obj;
            }
        }
        if (soapObject.hasProperty("Device"))
        {
            Object obj = soapObject.getProperty("Device");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                device = j.toString();
            }else if (obj!= null && obj instanceof String){
                device = (String) obj;
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return sifra;
            case 1:
                return kolicina;
            case 2:
                return cena;
            case 3:
                return aRT_Datum;
            case 4:
                return aRT_Vreme;
            case 5:
                return notes;
            case 6:
                return device;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 7;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Sifra";
                break;
            case 1:
                info.type = Float.class;
                info.name = "Kolicina";
                break;
            case 2:
                info.type = Float.class;
                info.name = "Cena";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ART_Datum";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ART_Vreme";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Notes";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Device";
                break;
        }
    }
    
    @Override
    public void setProperty(int arg0, Object arg1) {
    }

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(sifra+"-").append(kolicina+"-").append(cena+"-").append(aRT_Datum+"-").append(aRT_Vreme+"-").append(notes+"-");
		return result.toString() + ";";
	}
    
    
    
}