package com.jcertif.reseaujcertif.persistances;

public class Video {
	
	private int id;
	private String title;
	private String url;
	private String img_url;
	private int event_id;
	private User user;
	private String created;
	
	public Video() {
		this.id = 0;
		this.title = "";
		this.url = "";
		this.event_id = 0;
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getEvent_id() {
		return event_id;
	}
	
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
}
