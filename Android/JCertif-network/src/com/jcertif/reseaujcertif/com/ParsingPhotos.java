package com.jcertif.reseaujcertif.com;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.reseaujcertif.persistances.Photo;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class ParsingPhotos extends Parametres{
	
	private JCertifManager jCertifManager;
	
	public ParsingPhotos(JCertifManager jCertifManager){
		this.jCertifManager = jCertifManager;
	}
	
	public void getPhotosByEvent(int event_id){
		  
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getURLPhotosByEvent(event_id)));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		Photo photo = null;
		
		jCertifManager.setListPhotos(new ArrayList<Photo>());
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			nodes = doc.getElementsByTagName("Photo");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				photo = new Photo();
				
				photo.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				photo.setTitle(XMLfunctions.getValue(e, "title"));
				photo.setUrl(XMLfunctions.getValue(e, "url"));
				photo.setEvent_id(Integer.parseInt(XMLfunctions.getValue(e, "event_id")));
				photo.setCreated(XMLfunctions.getValue(e, "created"));
				
				jCertifManager.getListPhotos().add(photo);
				
			}
			
			nodes = doc.getElementsByTagName("User");
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				User user = new User();
				
				user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				user.setName(XMLfunctions.getValue(e, "name"));
				user.setFirstname(XMLfunctions.getValue(e, "firstname"));
				user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));				
				
				jCertifManager.getListPhotos().get(i).setUser(user);
				
			}
			
		}
		
	}
	
	public boolean addPhoto(Photo photo){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(photo.getTitle().length()>0)
			nameValuePairs.add(new BasicNameValuePair("title", photo.getTitle()));
		
		if(photo.getImg_url().length()>0)
			nameValuePairs.add(new BasicNameValuePair("url", photo.getImg_url()));
		
		nameValuePairs.add(new BasicNameValuePair("event_id", photo.getEvent_id()+""));
		nameValuePairs.add(new BasicNameValuePair("user_id", photo.getUser().getId()+""));
		
		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, addPhoto_URL));           
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))	
			return true;
		else
			return false;
	}

}
