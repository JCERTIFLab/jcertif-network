package com.jcertif.reseaujcertif.persistances;


public class Step {
	
	private Position start_location;
	private Position end_location;
	private int duration;
	private String instructions;
	private String distance;
	
	public Step(){
		start_location = new Position();
		end_location = new Position();
		duration = 0;
		instructions = "";
		distance = "";
	}

	public Position getStart_location() {
		return start_location;
	}

	public void setStart_location(Position start_location) {
		this.start_location = start_location;
	}

	public Position getEnd_location() {
		return end_location;
	}

	public void setEnd_location(Position end_location) {
		this.end_location = end_location;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

}
