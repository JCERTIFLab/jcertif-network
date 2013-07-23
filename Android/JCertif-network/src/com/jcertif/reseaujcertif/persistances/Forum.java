package com.jcertif.reseaujcertif.persistances;

public class Forum {

	private int id;
	private String title;
	private Category category;
	private String content;
	private String resolut;
	private User user;
	private String created;
	
	public Forum() {
		this.id = 0;
		this.title = "";
		this.category = new Category();
		this.content = "";
		this.resolut = "";
		this.user = new User();
		this.created = "";
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getResolut() {
		return resolut;
	}

	public void setResolut(String resolut) {
		this.resolut = resolut;
	}

}
