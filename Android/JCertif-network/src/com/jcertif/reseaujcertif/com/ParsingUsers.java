package com.jcertif.reseaujcertif.com;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class ParsingUsers extends Parametres{
	
	private JCertifManager jCertifManager;
	
	public ParsingUsers(JCertifManager jCertifManager){
		
		this.jCertifManager = jCertifManager;
	
	}

	public void getUsers(){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getAllUsers_URL));              
                
		NodeList nodes = doc.getElementsByTagName("User");
		
		jCertifManager.setListUsers(new ArrayList<User>());
					
		for (int i = 0; i < nodes.getLength(); i++) {						
			
			Element e = (Element)nodes.item(i);
			
			User user = new User();

			user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
			user.setLogin(XMLfunctions.getValue(e, "login"));
			user.setPassword(XMLfunctions.getValue(e, "password"));
			user.setName(XMLfunctions.getValue(e, "name"));
			user.setFirstname(XMLfunctions.getValue(e, "firstname"));
			user.setDate_naissance(XMLfunctions.getValue(e, "date_naissance"));
			user.setEmail(XMLfunctions.getValue(e, "email"));
			user.setTel(XMLfunctions.getValue(e, "tel"));
			user.setPayes(XMLfunctions.getValue(e, "payes"));
			user.setVille(XMLfunctions.getValue(e, "ville"));
			
			if(XMLfunctions.getValue(e, "status").equals("1"))
				user.setStatus(true);
			else
				user.setStatus(false);
			
			if(XMLfunctions.getValue(e, "admin").equals("1"))
				user.setAdmin(true);
			else
				user.setAdmin(false);
			
			user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));
			user.setLongitude(XMLfunctions.getValue(e, "longitude"));
			user.setLatitude(XMLfunctions.getValue(e, "latitude"));
			user.setDescription(XMLfunctions.getValue(e, "description"));
			user.setGcm_regid(XMLfunctions.getValue(e, "gcm_regid"));
			
			jCertifManager.getListUsers().add(user);
		}	
		
	}
	
	public User authentification(String login, String password){

		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getAuthentification_URL(login, password)));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		User user = null;
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			user = new User();
			
			nodes = doc.getElementsByTagName("User");
			
			e = (Element)nodes.item(0);

			user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
			user.setLogin(XMLfunctions.getValue(e, "login"));
			user.setPassword(XMLfunctions.getValue(e, "password"));
			user.setName(XMLfunctions.getValue(e, "name"));
			user.setFirstname(XMLfunctions.getValue(e, "firstname"));
			user.setDate_naissance(XMLfunctions.getValue(e, "date_naissance"));
			user.setEmail(XMLfunctions.getValue(e, "email"));
			user.setTel(XMLfunctions.getValue(e, "tel"));
			user.setPayes(XMLfunctions.getValue(e, "payes"));
			user.setVille(XMLfunctions.getValue(e, "ville"));
			
			if(XMLfunctions.getValue(e, "status").equals("1"))
				user.setStatus(true);
			else
				user.setStatus(false);
			
			if(XMLfunctions.getValue(e, "admin").equals("1"))
				user.setAdmin(true);
			else
				user.setAdmin(false);
			
			user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));
			user.setLongitude(XMLfunctions.getValue(e, "longitude"));
			user.setLatitude(XMLfunctions.getValue(e, "latitude"));
			user.setDescription(XMLfunctions.getValue(e, "description"));
			user.setGcm_regid(XMLfunctions.getValue(e, "gcm_regid"));
		}
		
		return user;
		
	}
	
	public boolean addUser(User user){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
		nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
		
		if(user.getName().length()>0)
			nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
		
		if(user.getFirstname().length()>0)
			nameValuePairs.add(new BasicNameValuePair("firstname", user.getFirstname()));
		
		if(user.getDate_naissance().length()>0)
			nameValuePairs.add(new BasicNameValuePair("date_naissance", user.getDate_naissance()));
		
		if(user.getEmail().length()>0)
			nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
		
		if(user.getTel().length()>0)
			nameValuePairs.add(new BasicNameValuePair("tel", user.getTel()));
		
		if(user.getPayes().length()>0)
			nameValuePairs.add(new BasicNameValuePair("payes", user.getPayes()));
		
		if(user.getVille().length()>0)
			nameValuePairs.add(new BasicNameValuePair("ville", user.getVille()));
	
		if(user.isStatus())
			nameValuePairs.add(new BasicNameValuePair("status", "1"));
		else
			nameValuePairs.add(new BasicNameValuePair("status", "0"));
		
		
		if(user.isAdmin())
			nameValuePairs.add(new BasicNameValuePair("admin", "1"));
		else
			nameValuePairs.add(new BasicNameValuePair("admin", "0"));
		
		if(user.getPhoto_url().length()>0)
			nameValuePairs.add(new BasicNameValuePair("photo_url", user.getPhoto_url()));
		
		if(user.getLongitude().length()>0)
			nameValuePairs.add(new BasicNameValuePair("longitude", user.getLongitude()));
		
		if(user.getLatitude().length()>0)
			nameValuePairs.add(new BasicNameValuePair("latitude", user.getLatitude()));
		
		if(user.getDescription().length()>0)
			nameValuePairs.add(new BasicNameValuePair("description", user.getDescription()));
		
		if(user.getGcm_regid().length()>0)
			nameValuePairs.add(new BasicNameValuePair("gcm_regid", user.getGcm_regid()));

		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, Add_User_URL));  
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			return true;
		}else
			return false;
		
	}
	
	public boolean updateUser(User user){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", user.getId()+""));
		nameValuePairs.add(new BasicNameValuePair("login", user.getLogin()));
		nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
		
		if(user.getName().length()>0)
			nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
		
		if(user.getFirstname().length()>0)
			nameValuePairs.add(new BasicNameValuePair("firstname", user.getFirstname()));
		
		if(user.getDate_naissance().length()>0)
			nameValuePairs.add(new BasicNameValuePair("date_naissance", user.getDate_naissance()));
		
		if(user.getEmail().length()>0)
			nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
		
		if(user.getTel().length()>0)
			nameValuePairs.add(new BasicNameValuePair("tel", user.getTel()));
		
		if(user.getPayes().length()>0)
			nameValuePairs.add(new BasicNameValuePair("payes", user.getPayes()));
		
		if(user.getVille().length()>0)
			nameValuePairs.add(new BasicNameValuePair("ville", user.getVille()));
	
		if(user.isStatus())
			nameValuePairs.add(new BasicNameValuePair("status", "1"));
		else
			nameValuePairs.add(new BasicNameValuePair("status", "0"));
		
		
		if(user.isAdmin())
			nameValuePairs.add(new BasicNameValuePair("admin", "1"));
		else
			nameValuePairs.add(new BasicNameValuePair("admin", "0"));
		
		if(user.getPhoto_url().length()>0)
			nameValuePairs.add(new BasicNameValuePair("photo_url", user.getPhoto_url()));
		
		if(user.getLongitude().length()>0)
			nameValuePairs.add(new BasicNameValuePair("longitude", user.getLongitude()));
		
		if(user.getLatitude().length()>0)
			nameValuePairs.add(new BasicNameValuePair("latitude", user.getLatitude()));
		
		if(user.getDescription().length()>0)
			nameValuePairs.add(new BasicNameValuePair("description", user.getDescription()));
		
		if(user.getGcm_regid().length()>0)
			nameValuePairs.add(new BasicNameValuePair("gcm_regid", user.getGcm_regid()));
		
		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, Update_User_URL));           
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))	
			return true;
		else
			return false;
		
	}
	
	public boolean sendMessage(String resiver_id, String sender_id, String msg){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("data[User][registatoin_ids]", resiver_id));
		nameValuePairs.add(new BasicNameValuePair("data[User][sender_id]", sender_id));
		nameValuePairs.add(new BasicNameValuePair("data[User][message]", msg));
		
		//{"multicast_id":4920398906049300564,"success":1,"failure":0,"canonical_ids":0,"results":[{"message_id":"0:1363956928411627%d4b44822f9fd7ecd"}]}
		String rep = postData(nameValuePairs, sendPush);
		
		if(rep.subSequence(rep.indexOf("success")+9, rep.indexOf("failure")-2).equals("1"))
			return true;
		else
			return false;
	}


}
