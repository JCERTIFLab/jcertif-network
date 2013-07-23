package com.jcertif.reseaujcertif.com;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.reseaujcertif.persistances.Comment;
import com.jcertif.reseaujcertif.persistances.User;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class ParsingComments extends Parametres{
	
	private JCertifManager jCertifManager;
	
	public ParsingComments(JCertifManager jCertifManager){
		
		this.jCertifManager = jCertifManager;
	
	}
	
	public void getCommentsByNews(int newsID){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getComment_By_News_URL(newsID)));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		Comment comment = null;
		
		jCertifManager.setListComments(new ArrayList<Comment>());
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			nodes = doc.getElementsByTagName("Comment");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				comment = new Comment();
				
				comment.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				comment.setContent(XMLfunctions.getValue(e, "content"));
				comment.setCreated(XMLfunctions.getValue(e, "created"));				
				
				jCertifManager.getListComments().add(comment);
				
			}
			
			nodes = doc.getElementsByTagName("User");
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				User user = new User();
				
				user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				user.setName(XMLfunctions.getValue(e, "name"));
				user.setFirstname(XMLfunctions.getValue(e, "firstname"));
				user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));				
				
				jCertifManager.getListComments().get(i).setUser(user);
				
			}
			
		}
		
	}
	
	public void getCommentsByPhoto(int photo_ID){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getComment_By_Photo_URL(photo_ID)));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		Comment comment = null;
		
		jCertifManager.setListComments(new ArrayList<Comment>());
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			nodes = doc.getElementsByTagName("Comment");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				comment = new Comment();
				
				comment.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				comment.setContent(XMLfunctions.getValue(e, "content"));
				comment.setCreated(XMLfunctions.getValue(e, "created"));
				
				jCertifManager.getListComments().add(comment);
				
			}
			
			nodes = doc.getElementsByTagName("User");
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				User user = new User();
				
				user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				user.setName(XMLfunctions.getValue(e, "name"));
				user.setFirstname(XMLfunctions.getValue(e, "firstname"));
				user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));				
				
				jCertifManager.getListComments().get(i).setUser(user);
				
			}
			
		}
		
	}
	
	public void getCommentsByVideo(int video_id){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getComment_By_Video_URL(video_id)));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		Comment comment = null;
		
		jCertifManager.setListComments(new ArrayList<Comment>());
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			nodes = doc.getElementsByTagName("Comment");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				comment = new Comment();
				
				comment.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				comment.setContent(XMLfunctions.getValue(e, "content"));
				comment.setCreated(XMLfunctions.getValue(e, "created"));
				
				jCertifManager.getListComments().add(comment);
				
			}
			
			nodes = doc.getElementsByTagName("User");
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				User user = new User();
				
				user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				user.setName(XMLfunctions.getValue(e, "name"));
				user.setFirstname(XMLfunctions.getValue(e, "firstname"));
				user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));				
				
				jCertifManager.getListComments().get(i).setUser(user);
				
			}
			
		}
		
	}
	
	public void getCommentsByForum(int forumID){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getComment_By_Forum_URL(forumID)));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		Comment comment = null;
		
		jCertifManager.setListComments(new ArrayList<Comment>());
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			nodes = doc.getElementsByTagName("Comment");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				comment = new Comment();
				
				comment.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				comment.setContent(XMLfunctions.getValue(e, "content"));
				comment.setCreated(XMLfunctions.getValue(e, "created"));				
				
				jCertifManager.getListComments().add(comment);
				
			}
			
			nodes = doc.getElementsByTagName("User");
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				User user = new User();
				
				user.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				user.setName(XMLfunctions.getValue(e, "name"));
				user.setFirstname(XMLfunctions.getValue(e, "firstname"));
				user.setPhoto_url(XMLfunctions.getValue(e, "photo_url"));				
				
				jCertifManager.getListComments().get(i).setUser(user);
				
			}
			
		}
		
	}
	
	public boolean addComment(Comment comment){
			
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(comment.getContent().length()>0)
			nameValuePairs.add(new BasicNameValuePair("content", comment.getContent()));
		nameValuePairs.add(new BasicNameValuePair("user_id", comment.getUser().getId()+""));
		nameValuePairs.add(new BasicNameValuePair("news_id", comment.getNews_id()+""));
		nameValuePairs.add(new BasicNameValuePair("photo_id", comment.getPhoto_id()+""));
		nameValuePairs.add(new BasicNameValuePair("video_id", comment.getVideo_id()+""));
		nameValuePairs.add(new BasicNameValuePair("forum_id", comment.getForum_id()+""));

		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, Add_Comment_URL));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			return true;
		}else
			return false;
		
	}
	
	public boolean delComment(int idComment){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(Del_Comment_URL+idComment));              
        
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);

		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
	}

}
