package com.jcertif.reseaujcertif.services;

import java.util.ArrayList;

import com.jcertif.reseaujcertif.persistances.Category;
import com.jcertif.reseaujcertif.persistances.Comment;
import com.jcertif.reseaujcertif.persistances.Event;
import com.jcertif.reseaujcertif.persistances.Forum;
import com.jcertif.reseaujcertif.persistances.News;
import com.jcertif.reseaujcertif.persistances.Photo;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.persistances.Video;


public class JCertifManager {
	
	private String rubrique = "";
	
	private int menu_Selecteed = 0;
	
	private int itemSelected = 0;
	
	public boolean recevedNewMsg = false;
	
	public static JCertifManager jCertifManager;
	
	private ArrayList<User> listUsers = new ArrayList<User>();
	
	private ArrayList<News> listNews = new ArrayList<News>();
	
	private ArrayList<Comment> listComments = new ArrayList<Comment>();
	
	private ArrayList<Event> listEvents = new ArrayList<Event>();
	
	private ArrayList<Photo> listPhotos = new ArrayList<Photo>();
	
	private ArrayList<Video> listVideos = new ArrayList<Video>();
	
	private ArrayList<Category> listCategories = new ArrayList<Category>();
	
	private ArrayList<Forum> listForums = new ArrayList<Forum>();
 	
	private User currentUser, selectedUser;
	
	private boolean parsingUsersFinish = false;
	
	private boolean parsingEventFinish = false;
	
	public static JCertifManager getInstance() {
		
		if (jCertifManager == null)
			jCertifManager = new JCertifManager();
		
		return jCertifManager;
	}

	public ArrayList<User> getListUsers() {
		return listUsers;
	}

	public void setListUsers(ArrayList<User> listUsers) {
		this.listUsers = listUsers;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public ArrayList<News> getListNews() {
		return listNews;
	}

	public void setListNews(ArrayList<News> listNews) {
		this.listNews = listNews;
	}

	public ArrayList<Comment> getListComments() {
		return listComments;
	}

	public void setListComments(ArrayList<Comment> listComments) {
		this.listComments = listComments;
	}

	public ArrayList<Event> getListEvents() {
		return listEvents;
	}

	public void setListEvents(ArrayList<Event> listEvents) {
		this.listEvents = listEvents;
	}

	public ArrayList<Photo> getListPhotos() {
		return listPhotos;
	}

	public void setListPhotos(ArrayList<Photo> listPhotos) {
		this.listPhotos = listPhotos;
	}

	public boolean isParsingUsersFinish() {
		return parsingUsersFinish;
	}

	public void setParsingUsersFinish(boolean parsingUsersFinish) {
		this.parsingUsersFinish = parsingUsersFinish;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public ArrayList<Video> getListVideos() {
		return listVideos;
	}

	public void setListVideos(ArrayList<Video> listVideos) {
		this.listVideos = listVideos;
	}

	public int getMenu_Selecteed() {
		return menu_Selecteed;
	}

	public void setMenu_Selecteed(int menu_Selecteed) {
		this.menu_Selecteed = menu_Selecteed;
	}

	public ArrayList<Category> getListCategories() {
		return listCategories;
	}

	public void setListCategories(ArrayList<Category> listCategories) {
		this.listCategories = listCategories;
	}

	public ArrayList<Forum> getListForums() {
		return listForums;
	}

	public void setListForums(ArrayList<Forum> listForums) {
		this.listForums = listForums;
	}

	public String getRubrique() {
		return rubrique;
	}

	public void setRubrique(String rubrique) {
		this.rubrique = rubrique;
	}

	public int getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(int itemSelected) {
		this.itemSelected = itemSelected;
	}

	public boolean isParsingEventFinish() {
		return parsingEventFinish;
	}

	public void setParsingEventFinish(boolean parsingEventFinish) {
		this.parsingEventFinish = parsingEventFinish;
	}

}
