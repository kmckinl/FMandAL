package com.yelink.fmandal.entities;

import java.awt.Rectangle;

import com.yelink.fmandal.utilities.Vector3f;


public class Character {
	private String name;
	private int health, power, speed, attSpeed, jumpHeight;
	private float width, height, attReach;
	private boolean doubleJump;
	private Rectangle charBounds, reachBounds, collectBounds;
	/*
	 * Phrases:
	 * Fly-Man: Get off my antennae, these are for the ladies!
	 * Fly-man: These things aren't just meant for flying, they're meant for speed!
	 * Fly-Man: Boosh!
	 */
	public Character(String name, float x, float y) {
		this.name = name;
		
		if (this.name == "Fly-Man") {
			this.health = 100;
			this.power = 10;
			this.speed = 4;
			this.attSpeed = 1;
			this.jumpHeight = 20;
			this.width = 100.0f;
			this.height = 180.0f;
			this.attReach = 65.0f;
			this.doubleJump = true;
			int centerX = (int) (x + 100);
			int centerY = (int) (y + 100);
			this.charBounds = new Rectangle(centerX - (int) (width / 2), centerY - (int) (height / 2), (int) this.width, (int) this.height);
			this.reachBounds = new Rectangle(centerX, centerY - 30, (int) this.attReach, 40);
			this.collectBounds = new Rectangle(centerX - 20, centerY - 40, 40, 80);
		} else if (this.name == "Aqua Lad") {
			this.health = 200;
			this.power = 20;
			this.speed = 2;
			this.attSpeed = 2;
			this.jumpHeight = 15;
			this.doubleJump = false;
			this.width = 120.0f;
			this.height = 180.0f;
			this.attReach = 50.0f;
			int centerX = (int) (x + 100);
			int centerY = (int) (y + 100);
			this.charBounds = new Rectangle(centerX - (int) (width / 2), centerY - (int) (height / 2), (int) this.width, (int) this.height);
			this.reachBounds = new Rectangle(centerX, centerY - 40, (int) this.attReach, 50);
			this.collectBounds = new Rectangle(centerX - 40, centerY - 60, 80, 120);
		}
	}
	
	/* -- -- -- GETTERS / SETTERS -- -- -- */
	public void setHealth(int delHealth) {
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
	
	public Rectangle getBounds() {
		return this.charBounds;
	}
	
	public Rectangle getReachBounds() {
		return this.reachBounds;
	}
	
	public Rectangle getCollectBounds() {
		return this.collectBounds;
	}
	
	public void setBoundsPosition(int x, int y) {
		this.charBounds.setLocation((int) (x + this.charBounds.getX()), (int) (y + this.charBounds.getY()));
		this.reachBounds.setLocation((int) (x + this.charBounds.getX()), (int) (y + this.charBounds.getY()));
		this.collectBounds.setLocation((int) (x + this.charBounds.getX()), (int) (y + this.charBounds.getY()));
	}
	
	public float[] getCenter() {
		return new float[] {
			(float) this.charBounds.getCenterX(), (float) this.charBounds.getCenterY()
		};
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	
}
