package com.jcertif.reseaujcertif.com;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.reseaujcertif.persistances.Forum;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class ParsingForum extends Parametres{
	
	private JCertifManager jCertifManager;
	
	public ParsingForum(JCertifManager jCertifManager){
		
		this.jCertifManager = jCertifManager;
	
	}
	
	public boolean DelForum(int forum_id){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getDeleteForum_URL(forum_id)));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			return true;
		}else
			return false;
		
	}
	
	public void getAllForumByCategories(int categroy_id){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getForumByCategory_URL(categroy_id)));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		Forum forum = null;
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			nodes = doc.getElementsByTagName("Forum");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				forum = new Forum();
				
				forum.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				forum.setTitle(XMLfunctions.getValue(e, "title"));
				forum.setContent(XMLfunctions.getValue(e, "content"));
				forum.setResolut(XMLfunctions.getValue(e, "resolut"));			
				forum.setCreated(XMLfunctions.getValue(e, "created"));				
				
				jCertifManager.getListForums().add(forum);
				
			}
			
			nodes = doc.getElementsByTagName("User");
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				User user = new User();
				
				user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				user.setName(XMLfunctions.getValue(e, "name"));
				user.setFirstname(XMLfunctions.getValue(e, "firstname"));
				user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));				
				
				jCertifManager.getListForums().get(i).setUser(user);
				
			}
			
		}
		
	}
	
	public boolean updateForum(Forum forum){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", forum.getId()+""));
		
		if(forum.getTitle().length()>0)
			nameValuePairs.add(new BasicNameValuePair("title", forum.getTitle()));
		
		if(forum.getCreated().length()>0)
			nameValuePairs.add(new BasicNameValuePair("category", forum.getCreated()));
		
		if(forum.getContent().length()>0)
			nameValuePairs.add(new BasicNameValuePair("content", forum.getContent()));
		
		if(forum.getResolut().length()>0)
			nameValuePairs.add(new BasicNameValuePair("resolut", forum.getResolut()));
		
		nameValuePairs.add(new BasicNameValuePair("user_id", forum.getUser().getId()+""));
		
		if(forum.getCreated().length()>0)
			nameValuePairs.add(new BasicNameValuePair("created", forum.getCreated()));
		
		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, UpdateForum));           
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))		
			return true;
		else
			return false;
		
	}
	
	public boolean addForum(Forum forum){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(forum.getTitle().length()>0)
			nameValuePairs.add(new BasicNameValuePair("title", forum.getTitle()));
		
		nameValuePairs.add(new BasicNameValuePair("category_id", forum.getCategory().getId()+""));
		
		if(forum.getContent().length()>0)
			nameValuePairs.add(new BasicNameValuePair("content", forum.getContent()));
		
		if(forum.getResolut().length()>0)
			nameValuePairs.add(new BasicNameValuePair("resolut", forum.getResolut()));
		
		nameValuePairs.add(new BasicNameValuePair("user_id", forum.getUser().getId()+""));
		
		if(forum.getCreated().length()>0)
			nameValuePairs.add(new BasicNameValuePair("created", forum.getCreated()));
		
		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, AddForum));           
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))		
			return true;
		else
			return false;
		
	}

}
