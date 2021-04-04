package com.yelink.fmandal.entities;

public class Character {
	private String name;
	private int health;
	private int power;
	private int speed;
	private int attSpeed;
	private int jumpHeight;
	private boolean doubleJump;
	/*
	 * Phrases:
	 * Fly-Man: Get off my antennae, these are for the ladies!
	 * Fly-man: Things aren't just meant for flying, they're meant for speed!
	 */
	public Character(String name) {
		this.name = name;
		
		if (this.name == "Fly-Man") {
			this.health = 100;
			this.power = 10;
			this.speed = 3;
			this.attSpeed = 2;
			this.jumpHeight = 20;
			this.doubleJump = true;
		} else if (this.name == "Aqua Lad") {
			this.health = 200;
			this.power = 20;
			this.speed = 2;
			this.attSpeed = 1;
			this.jumpHeight = 15;
			this.doubleJump = false;
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
}
