package com.jcertif.reseaujcertif.com;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.reseaujcertif.persistances.News;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class ParsingNews extends Parametres{
	
	private JCertifManager jCertifManager;
	
	public ParsingNews(JCertifManager jCertifManager){
		
		this.jCertifManager = jCertifManager;
	
	}
	
	public boolean DelNews(int idNews){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(delNews_URL+idNews));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			return true;
		}else
			return false;
		
	}
	
	public boolean UpdateNews(News news){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("id", news.getId()+""));
		
		if(news.getUrl().length()>0)
			nameValuePairs.add(new BasicNameValuePair("url", news.getUrl()));
		
		if(news.getTitle().length()>0)
			nameValuePairs.add(new BasicNameValuePair("title", news.getTitle()));
		
		if(news.getContent().length()>0)
			nameValuePairs.add(new BasicNameValuePair("content", news.getContent()));
		
		if(news.getImg_url().length()>0)
			nameValuePairs.add(new BasicNameValuePair("img_url", news.getImg_url()));
		
		nameValuePairs.add(new BasicNameValuePair("user_id", news.getUser().getId()+""));
		
		if(news.getCreated().length()>0)
			nameValuePairs.add(new BasicNameValuePair("created", news.getCreated()));

		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, updateNews_URL));                   
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
	}
	
	public boolean AddNews(News news){
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(news.getUrl().length()>0)
			nameValuePairs.add(new BasicNameValuePair("url", news.getUrl()));
		
		if(news.getTitle().length()>0)
			nameValuePairs.add(new BasicNameValuePair("title", news.getTitle()));
		
		if(news.getContent().length()>0)
			nameValuePairs.add(new BasicNameValuePair("content", news.getContent()));
		
		if(news.getImg_url().length()>0)
			nameValuePairs.add(new BasicNameValuePair("img_url", news.getImg_url()));
		
		nameValuePairs.add(new BasicNameValuePair("user_id", news.getUser().getId()+""));

		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, addNews_URL));                   
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
	}
	
	public void getAllNews(){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(AllNews_URL));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		News news = null;
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			jCertifManager.setListNews(new ArrayList<News>());
			
			nodes = doc.getElementsByTagName("News");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				news = new News();
				
				news.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				news.setUrl(XMLfunctions.getValue(e, "url"));
				news.setTitle(XMLfunctions.getValue(e, "title"));
				news.setContent(XMLfunctions.getValue(e, "content"));
				news.setImg_url(XMLfunctions.getValue(e, "img_url"));
				news.setCreated(XMLfunctions.getValue(e, "created"));				
				
				jCertifManager.getListNews().add(news);
				
			}
			
			nodes = doc.getElementsByTagName("User");
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				User user = new User();
				
				user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				user.setName(XMLfunctions.getValue(e, "name"));
				user.setFirstname(XMLfunctions.getValue(e, "firstname"));
				user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));				
				
				jCertifManager.getListNews().get(i).setUser(user);
				
			}
			
		}
		
	}

}
