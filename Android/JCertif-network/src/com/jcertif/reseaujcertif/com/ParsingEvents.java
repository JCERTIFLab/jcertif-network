package com.jcertif.reseaujcertif.com;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.reseaujcertif.persistances.Event;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class ParsingEvents extends Parametres{
	
	private JCertifManager jCertifManager;
	
	public ParsingEvents(JCertifManager jCertifManager){
		
		this.jCertifManager = jCertifManager;
	
	}
	
	public boolean DelEvent(int idEvent){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(delEvents+idEvent));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			return true;
		}else
			return false;
		
	}
	
	public void getAllEvents(){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getAllEvents));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		jCertifManager.setListEvents(new ArrayList<Event>());
		
		Event event = null;
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			nodes = doc.getElementsByTagName("Event");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				event = new Event();
				
				event.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				event.setTitle(XMLfunctions.getValue(e, "title"));
				event.setDescription(XMLfunctions.getValue(e, "description"));
				event.setUrl(XMLfunctions.getValue(e, "url"));
				event.setImg_url(XMLfunctions.getValue(e, "img_url"));
				event.setAdress(XMLfunctions.getValue(e, "adress"));	
				event.setVille(XMLfunctions.getValue(e, "ville"));	
				event.setContry(XMLfunctions.getValue(e, "contry"));	
				event.setLongitude(XMLfunctions.getValue(e, "longitude"));	
				event.setLatitude(XMLfunctions.getValue(e, "latitude"));	
				event.setDate_start(XMLfunctions.getValue(e, "date_start"));	
				event.setDate_finish(XMLfunctions.getValue(e, "date_finish"));	
				
				jCertifManager.getListEvents().add(event);
				
			}
			
			nodes = doc.getElementsByTagName("User");
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				User user = new User();
				
				user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				user.setName(XMLfunctions.getValue(e, "name"));
				user.setFirstname(XMLfunctions.getValue(e, "firstname"));
				user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));				
				
				jCertifManager.getListEvents().get(i).setUser(user);
				
			}
			
		}
		
	}
	
	public boolean updateEvent(Event event){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", event.getId()+""));
		
		if(event.getTitle().length()>0)
			nameValuePairs.add(new BasicNameValuePair("title", event.getTitle()));
		
		if(event.getDescription().length()>0)
			nameValuePairs.add(new BasicNameValuePair("description", event.getDescription()));
		
		if(event.getUrl().length()>0)
			nameValuePairs.add(new BasicNameValuePair("url", event.getUrl()));
		
		if(event.getImg_url().length()>0)
			nameValuePairs.add(new BasicNameValuePair("img_url", event.getImg_url()));
		
		if(event.getDate_start().length()>0)
			nameValuePairs.add(new BasicNameValuePair("date_start", event.getDate_start()));
		
		if(event.getDate_finish().length()>0)
			nameValuePairs.add(new BasicNameValuePair("date_finish", event.getDate_finish()));
		
		if(event.getVille().length()>0)
			nameValuePairs.add(new BasicNameValuePair("ville", event.getVille()));
		
		if(event.getContry().length()>0)
			nameValuePairs.add(new BasicNameValuePair("contry", event.getContry()));
		
		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, updateEvent));           
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))		
			return true;
		else
			return false;
		
	}
	
	public boolean addEvent(Event event){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		if(event.getTitle().length()>0)
			nameValuePairs.add(new BasicNameValuePair("title", event.getTitle()));
		
		if(event.getDescription().length()>0)
			nameValuePairs.add(new BasicNameValuePair("description", event.getDescription()));
		
		if(event.getUrl().length()>0)
			nameValuePairs.add(new BasicNameValuePair("url", event.getUrl()));
		
		if(event.getImg_url().length()>0)
			nameValuePairs.add(new BasicNameValuePair("img_url", event.getImg_url()));
		
		if(event.getDate_start().length()>0)
			nameValuePairs.add(new BasicNameValuePair("date_start", event.getDate_start()));
		
		if(event.getDate_finish().length()>0)
			nameValuePairs.add(new BasicNameValuePair("date_finish", event.getDate_finish()));
		
		if(event.getVille().length()>0)
			nameValuePairs.add(new BasicNameValuePair("ville", event.getVille()));
		
		if(event.getContry().length()>0)
			nameValuePairs.add(new BasicNameValuePair("contry", event.getContry()));
		
		nameValuePairs.add(new BasicNameValuePair("user_id", event.getUser().getId()+""));
		
		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, addEvent));           
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))		
			return true;
		else
			return false;
		
	}

}
