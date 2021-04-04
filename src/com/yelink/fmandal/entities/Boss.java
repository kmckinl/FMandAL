package com.yelink.fmandal.entities;

public class Boss {
	private boolean miniBoss;
	
	
	/*
	 * Bosses: Panda, Cat, Weed, Office Man
	 * Cat uses headbutt way too often
	 * Panda climbs up away from your ability to hit
	 * Weed won't stop running away
	 * Office Man tries to drop kick too often
	 * 
	 */
	public Boss(boolean miniboss) {
		this.miniBoss = miniBoss;
	}
}
