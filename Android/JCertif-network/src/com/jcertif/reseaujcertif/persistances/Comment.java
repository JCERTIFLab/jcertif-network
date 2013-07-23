package com.jcertif.reseaujcertif.persistances;

public class Comment {
	
	private int id;
	private String content;
	private User user;
	private int news_id;
	private int photo_id;
	private int video_id;
	private int forum_id;
	private String created;

	public Comment() {
		this.id = 0;
		this.content = "";
		this.user = new User();
		this.setNews_id(0);
		this.photo_id = 0;
		this.video_id = 0;
		this.forum_id = 0;
		this.setCreated("");
	}
	
	public Comment(String content, User user, int news_id, int photo_id, int video_id, int forum_id) {
		this.content = content;
		this.user = user;
		this.news_id = news_id;
		this.photo_id = photo_id;
		this.video_id = video_id;
		this.forum_id = forum_id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public int getPhoto_id() {
		return photo_id;
	}
	
	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
	}
	
	public int getVideo_id() {
		return video_id;
	}
	
	public void setVideo_id(int video_id) {
		this.video_id = video_id;
	}
	
	public int getForum_id() {
		return forum_id;
	}
	
	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}

	public int getNews_id() {
		return news_id;
	}

	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
