package com.jcertif.reseaujcertif.com;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.jcertif.reseaujcertif.persistances.Position;
import com.jcertif.reseaujcertif.persistances.Step;

public class ParsingSteps {

	public Step[] listStep;
	public String duration;
	public String distance;
	public boolean parsingStatus;
	public String url_path = "http://maps.google.com/maps/api/directions/xml?";
	
	public ParsingSteps(double latitude1, double lontitude1, double latitude2, double lontitude2){
		
		parsingStatus = false;
		
		String orig = "origin="+latitude1+","+lontitude1;
		String dest = "destination="+latitude2+","+lontitude2;
		
		parsing(url_path+orig+"&"+dest+"&sensor=true&mode=driving");
	}
	
	public void parsing(String URL){
		
		String xml = XMLfunctions.getXML(URL);
        Document doc = XMLfunctions.XMLfromString(xml);              
                
		NodeList nodes = doc.getElementsByTagName("step");
		
		listStep = new Step[nodes.getLength()];
		
		for(int i=0; i<nodes.getLength(); i++)
			listStep[i] = new Step();
		
		for (int i = 0; i < nodes.getLength(); i++) {						
			Element e = (Element)nodes.item(i);
			listStep[i].setInstructions(XMLfunctions.getValue(e, "html_instructions"));
			Log.i("instrction", listStep[i].getInstructions());
		}
		
		nodes = doc.getElementsByTagName("start_location");
							
		for (int i = 0; i < nodes.getLength()-1; i++) {						
			Element e = (Element)nodes.item(i);			
			listStep[i].setStart_location(new Position(XMLfunctions.getValue(e, "lat"), XMLfunctions.getValue(e, "lng")));
		}
		
		nodes = doc.getElementsByTagName("end_location");
		
		for (int i = 0; i < nodes.getLength()-1; i++) {						
			Element e = (Element)nodes.item(i);			
			listStep[i].setEnd_location(new Position(XMLfunctions.getValue(e, "lat"), XMLfunctions.getValue(e, "lng")));
		}
		
		nodes = doc.getElementsByTagName("distance");
		
		for (int i = 0; i < nodes.getLength(); i++) {						
			Element e = (Element)nodes.item(i);
			String dist = XMLfunctions.getValue(e, "text");
			
			if(i!= nodes.getLength()-1)
				listStep[i].setDistance(dist);
			else
				distance = dist;
		}
		
		parsingStatus = true;
	}

}

