package com.sahin.flowapp.doctor.Models;

public class PatientIslemTakipModel {
	private boolean tf;
	private String hasresim;
	private String hastur;
	private String islemtarih;
	private String telefon;
	private String islemisim;
	private String kadi;
	private String hasisim;
	private String hascins;
	private String islemid;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setHasresim(String hasresim){
		this.hasresim = hasresim;
	}

	public String getHasresim(){
		return hasresim;
	}

	public void setHastur(String hastur){
		this.hastur = hastur;
	}

	public String getHastur(){
		return hastur;
	}

	public void setIslemtarih(String islemtarih){
		this.islemtarih = islemtarih;
	}

	public String getIslemtarih(){
		return islemtarih;
	}

	public void setTelefon(String telefon){
		this.telefon = telefon;
	}

	public String getTelefon(){
		return telefon;
	}

	public void setIslemisim(String islemisim){
		this.islemisim = islemisim;
	}

	public String getIslemisim(){
		return islemisim;
	}

	public void setKadi(String kadi){
		this.kadi = kadi;
	}

	public String getKadi(){
		return kadi;
	}

	public void setHasisim(String hasisim){
		this.hasisim = hasisim;
	}

	public String getHasisim(){
		return hasisim;
	}

	public void setHascins(String hascins){
		this.hascins = hascins;
	}

	public String getHascins(){
		return hascins;
	}

	public void setIslemid(String islemid){
		this.islemid = islemid;
	}

	public String getIslemid(){
		return islemid;
	}

	@Override
 	public String toString(){
		return 
			"PatientIslemTakipModel{" +
			"tf = '" + tf + '\'' + 
			",hasresim = '" + hasresim + '\'' +
			",hastur = '" + hastur + '\'' +
			",islemtarih = '" + islemtarih + '\'' +
			",telefon = '" + telefon + '\'' + 
			",islemisim = '" + islemisim + '\'' +
			",kadi = '" + kadi + '\'' + 
			",hasisim = '" + hasisim + '\'' +
			",hascins = '" + hascins + '\'' +
			",islemid = '" + islemid + '\'' +
			"}";
		}
}
