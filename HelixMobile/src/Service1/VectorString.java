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
import java.util.Vector;

public class VectorString extends Vector<String> implements KvmSerializable {
    
    
    public VectorString(){}
    
    public VectorString(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject != null){
            int size = soapObject.getPropertyCount();
            for (int i0=0;i0<size;i0++){
                Object obj = soapObject.getProperty(i0);
                if (obj !=null && obj.getClass().equals(SoapPrimitive.class)){
                    SoapPrimitive j0 =(SoapPrimitive) soapObject.getProperty(i0);
                    String j1=j0.toString();
                    add(j1);
                }
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        return this.get(arg0);
    }
    
    @Override
    public int getPropertyCount() {
        return this.size();
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        info.name = "string";
        info.type = String.class;
    }
    
    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
