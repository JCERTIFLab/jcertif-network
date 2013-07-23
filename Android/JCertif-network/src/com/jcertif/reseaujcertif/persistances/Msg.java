package com.jcertif.reseaujcertif.persistances;

public class Msg {
	
	private String user;
	private String id_Sender;
	private String id_Resiver;
	private String date;
	private String msg;
	
	public Msg(){
		user = "";
		id_Sender = "";
		id_Resiver = "";
		date = "";
		msg = "";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getId_Sender() {
		return id_Sender;
	}

	public void setId_Sender(String id_Sender) {
		this.id_Sender = id_Sender;
	}

	public String getId_Resiver() {
		return id_Resiver;
	}

	public void setId_Resiver(String id_Resiver) {
		this.id_Resiver = id_Resiver;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
