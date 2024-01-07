package com.sahin.flowapp.doctor.Models;

public class PatientModel {
	private boolean tf;
	private Object hasid;
	private Object hasresim;
	private Object hastur;
	private Object hasisim;
	private Object hascins;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setHasid(Object hasid){
		this.hasid = hasid;
	}

	public Object getHasid(){
		return hasid;
	}

	public void setHasresim(Object hasresim){
		this.hasresim = hasresim;
	}

	public Object getHasresim(){
		return hasresim;
	}

	public void setHastur(Object hastur){
		this.hastur = hastur;
	}

	public Object getHastur(){
		return hastur;
	}

	public void setHasisim(Object hasisim){
		this.hasisim = hasisim;
	}

	public Object getHasisim(){
		return hasisim;
	}

	public void setHascins(Object hascins){
		this.hascins = hascins;
	}

	public Object getHascins(){
		return hascins;
	}

	@Override
 	public String toString(){
		return 
			"PatientModel{" +
			"tf = '" + tf + '\'' + 
			",hasid = '" + hasid + '\'' +
			",hasresim = '" + hasresim + '\'' +
			",hastur = '" + hastur + '\'' +
			",hasisim = '" + hasisim + '\'' +
			",hascins = '" + hascins + '\'' +
			"}";
		}
}
