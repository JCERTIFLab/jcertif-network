package com.jcertif.reseaujcertif.persistances;

public class Position {

	private String altitude;
	private String longitude;
	
	public Position(){
		
	}
	
	public Position(String alt, String lon){
		altitude = alt;
		longitude = lon;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
}
