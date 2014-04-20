package com.example.model;

import java.util.Date;
import java.util.Hashtable;

import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Artiklis extends AttributeContainer implements KvmSerializable,
		java.io.Serializable {

	private static final long serialVersionUID = -1L;
	
	private Integer id;

	public String firma;

	public String sifra;

	public String naziv;

	public String em;

	public String tarifa;

	public Double prod2;

	public Double prod3;

	public Date datum;

	public Double prkol;

	public Double prsuma;

	public String selec;

	public String tip;

	public String poteklo;

	public Double edprcena;

	public String vid;

	public String selekt;

	public Double virtcena;

	public Double virtkol;

	public Double kritkol;

	public Double prod4;

	public Double prod5;

	public Double prod6;

	public Double prod7;

	public Double prod8;

	public Double prod9;

	public String stranime;

	public String sifsek;

	public String userbroj;

	public String slika;

	public Integer serdane;

	public Double prisutnakol;

	public Double prodadenakol;

	public String brend;

	public String dobav;

	public String tip1;

	public String tip2;

	public String tip3;

	public String tip4;

	public String tip5;

	public String vominus;

	public Integer barkodkonv;

	public Double dcena;

	public Double drabat1;

	public Double drabat2;

	public Double drabat3;

	public String zatprom;

	public String hp;

	public Double carstapka;

	public String konsignacija;

	public Double parpaket;

	public Integer paketpaleta;

	public Double netotezina;

	public Double paletatezina;

	public String tarozn;

	public Double nabcena;

	public Short stranski;

	public String tarifa2;

	public Double dcenamalo;

	public Short fzo;

	public String virtem;

	public String grupa1;

	public String grupa2;

	public Float predcasrabota;

	public Float vredcasrabota;

	public String opisraboperacija;

	public Float fiksnadolzina;

	public String pozicija1;

	public String pozicija2;

	public String pozicija3;

	public String pozicija4;

	public Short parcinja;

	public byte[] opisraboperacija1;

	public String sifrigrcena;

	public Integer kritdenovi;

	public Double dcenauvoz;

	public Short ap;

	public Float posnabcensoddv;

	public Float procmarza;

	public Double pakplastika;

	public Double pakhartijakarton;

	public Double pakmetal;

	public Double pakstaklo;

	public Double pakdrvenimaterijali;

	public Double pakkompozitnimaterijali;

	public Double dimenzijax;

	public Double dimenzijay;

	public Double dimenzijaz;

	public String em2;

	public String pomosenstr;

	public Short proizaparatdvete;

	public Date datainstalacijaaparat;

	public Short mesredovenservis;

	public Date flag;

	public Double brutotezina;

	public String formatslika;

	public Date datslika;

	public Short sklopdorab;

	public Short faznoproizv;

	public Short rabnalog;

	public String identitet;

	public String pogon;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getEm() {
		return em;
	}

	public void setEm(String em) {
		this.em = em;
	}

	public String getTarifa() {
		return tarifa;
	}

	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}

	public Double getProd2() {
		return prod2;
	}

	public void setProd2(Double prod2) {
		this.prod2 = prod2;
	}

	public Double getProd3() {
		return prod3;
	}

	public void setProd3(Double prod3) {
		this.prod3 = prod3;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Double getPrkol() {
		return prkol;
	}

	public void setPrkol(Double prkol) {
		this.prkol = prkol;
	}

	public Double getPrsuma() {
		return prsuma;
	}

	public void setPrsuma(Double prsuma) {
		this.prsuma = prsuma;
	}

	public String getSelec() {
		return selec;
	}

	public void setSelec(String selec) {
		this.selec = selec;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getPoteklo() {
		return poteklo;
	}

	public void setPoteklo(String poteklo) {
		this.poteklo = poteklo;
	}

	public Double getEdprcena() {
		return edprcena;
	}

	public void setEdprcena(Double edprcena) {
		this.edprcena = edprcena;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getSelekt() {
		return selekt;
	}

	public void setSelekt(String selekt) {
		this.selekt = selekt;
	}

	public Double getVirtcena() {
		return virtcena;
	}

	public void setVirtcena(Double virtcena) {
		this.virtcena = virtcena;
	}

	public Double getVirtkol() {
		return virtkol;
	}

	public void setVirtkol(Double virtkol) {
		this.virtkol = virtkol;
	}

	public Double getKritkol() {
		return kritkol;
	}

	public void setKritkol(Double kritkol) {
		this.kritkol = kritkol;
	}

	public Double getProd4() {
		return prod4;
	}

	public void setProd4(Double prod4) {
		this.prod4 = prod4;
	}

	public Double getProd5() {
		return prod5;
	}

	public void setProd5(Double prod5) {
		this.prod5 = prod5;
	}

	public Double getProd6() {
		return prod6;
	}

	public void setProd6(Double prod6) {
		this.prod6 = prod6;
	}

	public Double getProd7() {
		return prod7;
	}

	public void setProd7(Double prod7) {
		this.prod7 = prod7;
	}

	public Double getProd8() {
		return prod8;
	}

	public void setProd8(Double prod8) {
		this.prod8 = prod8;
	}

	public Double getProd9() {
		return prod9;
	}

	public void setProd9(Double prod9) {
		this.prod9 = prod9;
	}

	public String getStranime() {
		return stranime;
	}

	public void setStranime(String stranime) {
		this.stranime = stranime;
	}

	public String getSifsek() {
		return sifsek;
	}

	public void setSifsek(String sifsek) {
		this.sifsek = sifsek;
	}

	public String getUserbroj() {
		return userbroj;
	}

	public void setUserbroj(String userbroj) {
		this.userbroj = userbroj;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public Integer getSerdane() {
		return serdane;
	}

	public void setSerdane(Integer serdane) {
		this.serdane = serdane;
	}

	public Double getPrisutnakol() {
		return prisutnakol;
	}

	public void setPrisutnakol(Double prisutnakol) {
		this.prisutnakol = prisutnakol;
	}

	public Double getProdadenakol() {
		return prodadenakol;
	}

	public void setProdadenakol(Double prodadenakol) {
		this.prodadenakol = prodadenakol;
	}

	public String getBrend() {
		return brend;
	}

	public void setBrend(String brend) {
		this.brend = brend;
	}

	public String getDobav() {
		return dobav;
	}

	public void setDobav(String dobav) {
		this.dobav = dobav;
	}

	public String getTip1() {
		return tip1;
	}

	public void setTip1(String tip1) {
		this.tip1 = tip1;
	}

	public String getTip2() {
		return tip2;
	}

	public void setTip2(String tip2) {
		this.tip2 = tip2;
	}

	public String getTip3() {
		return tip3;
	}

	public void setTip3(String tip3) {
		this.tip3 = tip3;
	}

	public String getTip4() {
		return tip4;
	}

	public void setTip4(String tip4) {
		this.tip4 = tip4;
	}

	public String getTip5() {
		return tip5;
	}

	public void setTip5(String tip5) {
		this.tip5 = tip5;
	}

	public String getVominus() {
		return vominus;
	}

	public void setVominus(String vominus) {
		this.vominus = vominus;
	}

	public Integer getBarkodkonv() {
		return barkodkonv;
	}

	public void setBarkodkonv(Integer barkodkonv) {
		this.barkodkonv = barkodkonv;
	}

	public Double getDcena() {
		return dcena;
	}

	public void setDcena(Double dcena) {
		this.dcena = dcena;
	}

	public Double getDrabat1() {
		return drabat1;
	}

	public void setDrabat1(Double drabat1) {
		this.drabat1 = drabat1;
	}

	public Double getDrabat2() {
		return drabat2;
	}

	public void setDrabat2(Double drabat2) {
		this.drabat2 = drabat2;
	}

	public Double getDrabat3() {
		return drabat3;
	}

	public void setDrabat3(Double drabat3) {
		this.drabat3 = drabat3;
	}

	public String getZatprom() {
		return zatprom;
	}

	public void setZatprom(String zatprom) {
		this.zatprom = zatprom;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public Double getCarstapka() {
		return carstapka;
	}

	public void setCarstapka(Double carstapka) {
		this.carstapka = carstapka;
	}

	public String getKonsignacija() {
		return konsignacija;
	}

	public void setKonsignacija(String konsignacija) {
		this.konsignacija = konsignacija;
	}

	public Double getParpaket() {
		return parpaket;
	}

	public void setParpaket(Double parpaket) {
		this.parpaket = parpaket;
	}

	public Integer getPaketpaleta() {
		return paketpaleta;
	}

	public void setPaketpaleta(Integer paketpaleta) {
		this.paketpaleta = paketpaleta;
	}

	public Double getNetotezina() {
		return netotezina;
	}

	public void setNetotezina(Double netotezina) {
		this.netotezina = netotezina;
	}

	public Double getPaletatezina() {
		return paletatezina;
	}

	public void setPaletatezina(Double paletatezina) {
		this.paletatezina = paletatezina;
	}

	public String getTarozn() {
		return tarozn;
	}

	public void setTarozn(String tarozn) {
		this.tarozn = tarozn;
	}

	public Double getNabcena() {
		return nabcena;
	}

	public void setNabcena(Double nabcena) {
		this.nabcena = nabcena;
	}

	public Short getStranski() {
		return stranski;
	}

	public void setStranski(Short stranski) {
		this.stranski = stranski;
	}

	public String getTarifa2() {
		return tarifa2;
	}

	public void setTarifa2(String tarifa2) {
		this.tarifa2 = tarifa2;
	}

	public Double getDcenamalo() {
		return dcenamalo;
	}

	public void setDcenamalo(Double dcenamalo) {
		this.dcenamalo = dcenamalo;
	}

	public Short getFzo() {
		return fzo;
	}

	public void setFzo(Short fzo) {
		this.fzo = fzo;
	}

	public String getVirtem() {
		return virtem;
	}

	public void setVirtem(String virtem) {
		this.virtem = virtem;
	}

	public String getGrupa1() {
		return grupa1;
	}

	public void setGrupa1(String grupa1) {
		this.grupa1 = grupa1;
	}

	public String getGrupa2() {
		return grupa2;
	}

	public void setGrupa2(String grupa2) {
		this.grupa2 = grupa2;
	}

	public Float getPredcasrabota() {
		return predcasrabota;
	}

	public void setPredcasrabota(Float predcasrabota) {
		this.predcasrabota = predcasrabota;
	}

	public Float getVredcasrabota() {
		return vredcasrabota;
	}

	public void setVredcasrabota(Float vredcasrabota) {
		this.vredcasrabota = vredcasrabota;
	}

	public String getOpisraboperacija() {
		return opisraboperacija;
	}

	public void setOpisraboperacija(String opisraboperacija) {
		this.opisraboperacija = opisraboperacija;
	}

	public Float getFiksnadolzina() {
		return fiksnadolzina;
	}

	public void setFiksnadolzina(Float fiksnadolzina) {
		this.fiksnadolzina = fiksnadolzina;
	}

	public String getPozicija1() {
		return pozicija1;
	}

	public void setPozicija1(String pozicija1) {
		this.pozicija1 = pozicija1;
	}

	public String getPozicija2() {
		return pozicija2;
	}

	public void setPozicija2(String pozicija2) {
		this.pozicija2 = pozicija2;
	}

	public String getPozicija3() {
		return pozicija3;
	}

	public void setPozicija3(String pozicija3) {
		this.pozicija3 = pozicija3;
	}

	public String getPozicija4() {
		return pozicija4;
	}

	public void setPozicija4(String pozicija4) {
		this.pozicija4 = pozicija4;
	}

	public Short getParcinja() {
		return parcinja;
	}

	public void setParcinja(Short parcinja) {
		this.parcinja = parcinja;
	}

	public byte[] getOpisraboperacija1() {
		return opisraboperacija1;
	}

	public void setOpisraboperacija1(byte[] opisraboperacija1) {
		this.opisraboperacija1 = opisraboperacija1;
	}

	public String getSifrigrcena() {
		return sifrigrcena;
	}

	public void setSifrigrcena(String sifrigrcena) {
		this.sifrigrcena = sifrigrcena;
	}

	public Integer getKritdenovi() {
		return kritdenovi;
	}

	public void setKritdenovi(Integer kritdenovi) {
		this.kritdenovi = kritdenovi;
	}

	public Double getDcenauvoz() {
		return dcenauvoz;
	}

	public void setDcenauvoz(Double dcenauvoz) {
		this.dcenauvoz = dcenauvoz;
	}

	public Short getAp() {
		return ap;
	}

	public void setAp(Short ap) {
		this.ap = ap;
	}

	public Float getPosnabcensoddv() {
		return posnabcensoddv;
	}

	public void setPosnabcensoddv(Float posnabcensoddv) {
		this.posnabcensoddv = posnabcensoddv;
	}

	public Float getProcmarza() {
		return procmarza;
	}

	public void setProcmarza(Float procmarza) {
		this.procmarza = procmarza;
	}

	public Double getPakplastika() {
		return pakplastika;
	}

	public void setPakplastika(Double pakplastika) {
		this.pakplastika = pakplastika;
	}

	public Double getPakhartijakarton() {
		return pakhartijakarton;
	}

	public void setPakhartijakarton(Double pakhartijakarton) {
		this.pakhartijakarton = pakhartijakarton;
	}

	public Double getPakmetal() {
		return pakmetal;
	}

	public void setPakmetal(Double pakmetal) {
		this.pakmetal = pakmetal;
	}

	public Double getPakstaklo() {
		return pakstaklo;
	}

	public void setPakstaklo(Double pakstaklo) {
		this.pakstaklo = pakstaklo;
	}

	public Double getPakdrvenimaterijali() {
		return pakdrvenimaterijali;
	}

	public void setPakdrvenimaterijali(Double pakdrvenimaterijali) {
		this.pakdrvenimaterijali = pakdrvenimaterijali;
	}

	public Double getPakkompozitnimaterijali() {
		return pakkompozitnimaterijali;
	}

	public void setPakkompozitnimaterijali(Double pakkompozitnimaterijali) {
		this.pakkompozitnimaterijali = pakkompozitnimaterijali;
	}

	public Double getDimenzijax() {
		return dimenzijax;
	}

	public void setDimenzijax(Double dimenzijax) {
		this.dimenzijax = dimenzijax;
	}

	public Double getDimenzijay() {
		return dimenzijay;
	}

	public void setDimenzijay(Double dimenzijay) {
		this.dimenzijay = dimenzijay;
	}

	public Double getDimenzijaz() {
		return dimenzijaz;
	}

	public void setDimenzijaz(Double dimenzijaz) {
		this.dimenzijaz = dimenzijaz;
	}

	public String getEm2() {
		return em2;
	}

	public void setEm2(String em2) {
		this.em2 = em2;
	}

	public String getPomosenstr() {
		return pomosenstr;
	}

	public void setPomosenstr(String pomosenstr) {
		this.pomosenstr = pomosenstr;
	}

	public Short getProizaparatdvete() {
		return proizaparatdvete;
	}

	public void setProizaparatdvete(Short proizaparatdvete) {
		this.proizaparatdvete = proizaparatdvete;
	}

	public Date getDatainstalacijaaparat() {
		return datainstalacijaaparat;
	}

	public void setDatainstalacijaaparat(Date datainstalacijaaparat) {
		this.datainstalacijaaparat = datainstalacijaaparat;
	}

	public Short getMesredovenservis() {
		return mesredovenservis;
	}

	public void setMesredovenservis(Short mesredovenservis) {
		this.mesredovenservis = mesredovenservis;
	}

	public Date getFlag() {
		return flag;
	}

	public void setFlag(Date flag) {
		this.flag = flag;
	}

	public Double getBrutotezina() {
		return brutotezina;
	}

	public void setBrutotezina(Double brutotezina) {
		this.brutotezina = brutotezina;
	}

	public String getFormatslika() {
		return formatslika;
	}

	public void setFormatslika(String formatslika) {
		this.formatslika = formatslika;
	}

	public Date getDatslika() {
		return datslika;
	}

	public void setDatslika(Date datslika) {
		this.datslika = datslika;
	}

	public Short getSklopdorab() {
		return sklopdorab;
	}

	public void setSklopdorab(Short sklopdorab) {
		this.sklopdorab = sklopdorab;
	}

	public Short getFaznoproizv() {
		return faznoproizv;
	}

	public void setFaznoproizv(Short faznoproizv) {
		this.faznoproizv = faznoproizv;
	}

	public Short getRabnalog() {
		return rabnalog;
	}

	public void setRabnalog(Short rabnalog) {
		this.rabnalog = rabnalog;
	}

	public String getIdentitet() {
		return identitet;
	}

	public void setIdentitet(String identitet) {
		this.identitet = identitet;
	}

	public String getPogon() {
		return pogon;
	}

	public void setPogon(String pogon) {
		this.pogon = pogon;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {

	}

	@Override
	public void setProperty(int arg0, Object arg1) {

	}

	@Override
	public String toString() {

		return sifra + "         " + naziv + "         " + prkol + "   ";
	}

}
