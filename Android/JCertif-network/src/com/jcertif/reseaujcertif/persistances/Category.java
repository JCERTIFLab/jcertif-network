package com.jcertif.reseaujcertif.persistances;

public class Category {
	
	private int id;
	private String name;
	private String url_img;
	
	public Category(){
		this.id = 0;
		this.name = "";
		this.url_img = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl_img() {
		return url_img;
	}

	public void setUrl_img(String url_img) {
		this.url_img = url_img;
	}

}
