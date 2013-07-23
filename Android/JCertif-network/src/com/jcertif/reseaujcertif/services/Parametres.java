package com.jcertif.reseaujcertif.services;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Parametres {

	public final String Domain_name = "http://reseau-social.p.ht";
	
//	public final String Domain_name = "http://192.168.1.2";

	public final String BASE_URL = Domain_name+"/ProjetAAC/admin/";
	
	public final String Base_IMG_URL = Domain_name+"/ProjetAAC/";
	
	public final String AllNews_URL = BASE_URL+"news/getallnews"; 
	public final String delNews_URL = BASE_URL+"news/deletenews/";
	public final String updateNews_URL = BASE_URL+"news/updatenews/";
	public final String addNews_URL = BASE_URL+"news/addnews/";
	
	public final String Authentification_URL = "users/authentification/";
	public final String getAllUsers_URL = BASE_URL+"users/getAllUsers";
	public final String Add_User_URL = BASE_URL+"users/addUser/";
	public final String Update_User_URL = BASE_URL+"users/updateUser/";
	
	public final String Comment_URL = BASE_URL+"comments/";
	public final String Add_Comment_URL = BASE_URL+"comments/addcomment/";
	public final String Del_Comment_URL = BASE_URL+"comments/deletecomment/";
	
	public final String getAllEvents = BASE_URL+"events/getallevents";
	public final String delEvents = BASE_URL+"events/deleteevent/";
	public final String updateEvent = BASE_URL+"events/updateevent/";
	public final String addEvent = BASE_URL+"events/addevent/";
	
	public final String Photos_URL = BASE_URL+"photos/getphotosbyevent/";
	public final String addPhoto_URL = BASE_URL+"photos/addphoto/";
	
	public final String Video_URL = BASE_URL+"videos/getvideosbyevent/";
	public final String addVideo_URL = BASE_URL+"videos/addvideo/";
	
	public final String getAllCategories = BASE_URL+"categories/getallcategories";
	public final String addCategory = BASE_URL+"categories/addcategory/";
	public final String delCategory = BASE_URL+"categories/deletecategory/";
	
	public final String getForumsByCategory = BASE_URL+"forums/getforumsbycategory/";
	public final String deleteForum = BASE_URL+"forums/deleteforum/";
	public final String UpdateForum = BASE_URL+"forums/updateforum/";
	public final String AddForum = BASE_URL+"forums/addforum/";
	
	public final String sendPush = BASE_URL+"users/send_message/";
	
	
	public String getDeleteForum_URL(int forum_id){
		return deleteForum+forum_id;
	}
	
	public String getForumByCategory_URL(int category_id){
		return getForumsByCategory+category_id;
	}
	
	public final String getImgULR(String url){
		return Base_IMG_URL+url;
	}
	
	public String getURLPhotosByEvent(int event_id){
		return Photos_URL+event_id;
	}
	
	public String getURLVideosByEvent(int event_id){
		return Video_URL+event_id;
	}
	
	public String getAuthentification_URL(String login, String password){
		return BASE_URL+Authentification_URL+login+"/"+password;
	}

	public String getComment_By_News_URL(int id){
		return Comment_URL+"getCommentsByNews/"+id;
	}
	
	public String getComment_By_Forum_URL(int id){
		return Comment_URL+"getCommentsByForum/"+id;
	}
	
	public String getComment_By_Photo_URL(int id){
		return Comment_URL+"getCommentsByPhoto/"+id;
	}
	
	public String getComment_By_Video_URL(int id){
		return Comment_URL+"getCommentsByVideo/"+id;
	}
	
	public String postData(List<NameValuePair> nameValuePairs, String url) {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity resEntity = response.getEntity();
			String str = "";
			if (resEntity != null)    
				str = EntityUtils.toString(resEntity);

			return str;

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	} 
}
