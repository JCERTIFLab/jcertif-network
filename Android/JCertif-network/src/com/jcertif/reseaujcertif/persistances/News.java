package com.jcertif.reseaujcertif.persistances;

public class News {

	private int id;
	private String url;
	private String title;
	private String content;
	private String img_url;
	private User user;
	private String created;

	public News() {
		this.setId(0);
		this.setUrl("");
		this.setTitle("");
		this.setContent("");
		this.setImg_url("");
		this.setUser(new User());
		this.setCreated("");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

}
