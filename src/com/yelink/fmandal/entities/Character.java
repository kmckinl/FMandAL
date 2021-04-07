package com.yelink.fmandal.entities;

import com.yelink.fmandal.utilities.Vector3f;

public class Character {
	private String name;
	private int health, power, speed, attSpeed, jumpHeight;
	private float width, height, attReach;
	private boolean doubleJump;
	/*
	 * Phrases:
	 * Fly-Man: Get off my antennae, these are for the ladies!
	 * Fly-man: These things aren't just meant for flying, they're meant for speed!
	 */
	public Character(String name) {
		this.name = name;
		
		if (this.name == "Fly-Man") {
			this.health = 100;
			this.power = 10;
			this.speed = 3;
			this.attSpeed = 2;
			this.jumpHeight = 20;
			this.width = 100.0f;
			this.height = 180.0f;
			this.attReach = 65.0f;
			this.doubleJump = true;
		} else if (this.name == "Aqua Lad") {
			this.health = 200;
			this.power = 20;
			this.speed = 2;
			this.attSpeed = 1;
			this.jumpHeight = 15;
			this.doubleJump = false;
			this.width = 120.0f;
			this.height = 180.0f;
			this.attReach = 50.0f;
		}
	}
	
	/* -- -- -- GETTERS / SETTERS -- -- -- */
	public void setHeatlh(int delHealth) {
		this.health += delHealth; 
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void setPower(int newPower) {
		this.power = newPower;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void setSpeed(int newSpeed) {
		this.speed = newSpeed;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setAttSpeed(int delASpeed) {
		this.attSpeed += delASpeed;
	}
	
	public int getAttSpeed() {
		return this.attSpeed;
	}
	
	public int getJumpHeight() {
		return this.jumpHeight;
	}
	
	public boolean getDoubleJump() {
		return this.doubleJump;
	}
	
	public float getReach() {
		return this.attReach;
	}
	
	public float[] getBounds() {
		return new float[] { this.width, this.height };
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
}
