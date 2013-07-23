package com.jcertif.reseaujcertif.persistances;

public class Event {
	
	private int id;
	private String title;
	private String description;
	private String url;
	private String img_url;
	private String adress;
	private String ville;
	private String contry;
	private String longitude;
	private String latitude;
	private String date_start;
	private String date_finish;
	private User user;
	
	public Event() {
		this.id = 0;
		this.title = "";
		this.description = "";
		this.url = "";
		this.img_url = "";
		this.adress = "";
		this.ville = "";
		this.contry = "";
		this.longitude = "";
		this.latitude = "";
		this.date_start = "";
		this.date_finish = "";
		this.user = new User();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImg_url() {
		return img_url;
	}
	
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	
	public String getAdress() {
		return adress;
	}
	
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public String getVille() {
		return ville;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getContry() {
		return contry;
	}
	
	public void setContry(String contry) {
		this.contry = contry;
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
	
	public String getDate_start() {
		return date_start;
	}
	
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	
	public String getDate_finish() {
		return date_finish;
	}
	
	public void setDate_finish(String date_finish) {
		this.date_finish = date_finish;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
