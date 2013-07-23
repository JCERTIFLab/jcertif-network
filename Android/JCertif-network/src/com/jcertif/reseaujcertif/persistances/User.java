package com.jcertif.reseaujcertif.persistances;

public class User {
	
	private int id;
	private String login;
	private String password;
	private String name;
	private String firstname;
	private String date_naissance;
	private String email;
	private String tel;
	private String payes;
	private String ville;
	private boolean status;
	private boolean admin;
	private String photo_url;
	private String longitude;
	private String latitude;
	private String description;
	private String gcm_regid; 
	
	public User() {
		this.id = 0;
		this.login = "";
		this.password = "";
		this.name = "";
		this.firstname = "";
		this.date_naissance = "";
		this.email = "";
		this.tel = "";
		this.payes = "";
		this.ville = "";
		this.status = false;
		this.admin = false;
		this.photo_url = "";
		this.longitude = "";
		this.latitude = "";
		this.description = "";
		this.gcm_regid = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getDate_naissance() {
		return date_naissance;
	}

	public void setDate_naissance(String date_naissance) {
		this.date_naissance = date_naissance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPayes() {
		return payes;
	}

	public void setPayes(String payes) {
		this.payes = payes;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGcm_regid() {
		return gcm_regid;
	}

	public void setGcm_regid(String gcm_regid) {
		this.gcm_regid = gcm_regid;
	}
	
}
