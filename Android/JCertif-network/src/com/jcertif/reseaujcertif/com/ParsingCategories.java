package com.jcertif.reseaujcertif.com;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jcertif.reseaujcertif.persistances.Category;
import com.jcertif.reseaujcertif.services.JCertifManager;
import com.jcertif.reseaujcertif.services.Parametres;

public class ParsingCategories extends Parametres{
	
	private JCertifManager jCertifManager;
	
	public ParsingCategories(JCertifManager jCertifManager){
		
		this.jCertifManager = jCertifManager;
	
	}
	
	public void getAllCategories(){
		  
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(getAllCategories));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		Category category = null;
		
		jCertifManager.setListCategories(new ArrayList<Category>());
					
		if(XMLfunctions.getValue(e, "status").equals("OK")){
			
			nodes = doc.getElementsByTagName("Category");
			
			int nbrMax = nodes.getLength();
			
			for (int i = 0; i < nbrMax; i++) {
				
				e = (Element)nodes.item(i);
				
				category = new Category();
				
				category.setId(Integer.parseInt(XMLfunctions.getValue(e, "id")));
				category.setName(XMLfunctions.getValue(e, "title"));
				category.setUrl_img(XMLfunctions.getValue(e, "url_img"));
				
				jCertifManager.getListCategories().add(category);
				
			}
			
		}
		
	}
	
	public boolean addCategory(Category category){

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if(category.getName().length()>0)
			nameValuePairs.add(new BasicNameValuePair("title", category.getName()));
		
		if(category.getUrl_img().length()>0)
			nameValuePairs.add(new BasicNameValuePair("url_img", category.getUrl_img()));
		
		Document doc = XMLfunctions.XMLfromString(postData(nameValuePairs, addCategory));           
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
	}

	public boolean DelCategorie(int idCategorie){
		
		Document doc = XMLfunctions.XMLfromString(XMLfunctions.getXML(delCategory+idCategorie));              
                
		NodeList nodes = doc.getElementsByTagName("response");
		
		Element e = (Element)nodes.item(0);
		
		if(XMLfunctions.getValue(e, "status").equals("OK"))
			return true;
		else
			return false;
		
	}
}
