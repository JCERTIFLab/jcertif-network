package com.jcertif.reseaujcertif.com;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.persistances.Video;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class ParsingVideos extends Parametres{
	
	private JCertifManager jCertifManager;
	
	public ParsingVideos(JCertifManager jCertifManager){
		
		this.jCertifManager = jCertifManager;
	
	}
	
	public void getVideosByEvent(int event_id){
		  
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getURLVideosByEvent(event_id)));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		Video video = null;
		
		jCertifManager.setListVideos(new ArrayList<Video>());
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			nodes = doc.getElementsByTagName("Video");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				video = new Video();
				
				video.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				video.setTitle(XMLfunctions.getValue(e, "title"));
				video.setUrl(XMLfunctions.getValue(e, "url"));
				video.setImg_url(XMLfunctions.getValue(e, "img_url"));
				video.setEvent_id(Integer.parseInt(XMLfunctions.getValue(e, "event_id")));
				video.setCreated(XMLfunctions.getValue(e, "created"));
				
				jCertifManager.getListVideos().add(video);
				
			}
			
			nodes = doc.getElementsByTagName("User");
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				User user = new User();
				
				user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				user.setName(XMLfunctions.getValue(e, "name"));
				user.setFirstname(XMLfunctions.getValue(e, "firstname"));
				user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));				
				
				jCertifManager.getListVideos().get(i).setUser(user);
				
			}
			
		}
		
	}
	
	public boolean addVideo(Video video){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(video.getTitle().length()>0)
			nameValuePairs.add(new BasicNameValuePair("title", video.getTitle()));
		
		if(video.getUrl().length()>0)
			nameValuePairs.add(new BasicNameValuePair("url", video.getUrl()));
		
		if(video.getImg_url().length()>0)
			nameValuePairs.add(new BasicNameValuePair("img_url", video.getImg_url()));
		
		nameValuePairs.add(new BasicNameValuePair("event_id", video.getEvent_id()+""));
		nameValuePairs.add(new BasicNameValuePair("user_id", video.getUser().getId()+""));
		
		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, addVideo_URL));           
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK")){		
			return true;
		}else{
			Log.i("test",">>Non");
			return false;
		}
	}


}
