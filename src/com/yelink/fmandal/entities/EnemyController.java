package com.yelink.fmandal.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yelink.fmandal.font.FontController;
import com.yelink.fmandal.utilities.Vector3f;

public class EnemyController {
	private int key = 0;
	private FontController fc;
	private Map<Integer, List<Enemies>> enemies = new HashMap<Integer, List<Enemies>>();
	
	public EnemyController(FontController fc) {
		// Initialize all the lists
		// First set (4 screens) 
		enemies.put(key, new ArrayList<Enemies>());
		enemies.get(key).add(new Enemies(key));
	}
	
	public void update(Vector3f playerpos) {
		for (Enemies enemy : enemies.get(key)) {
			enemy.update(playerpos);
		}
	}
	
	public void render() {
		// Uses key to render the current list, key will be updated based on player actions
		for (Enemies enemy : enemies.get(key)) {
			enemy.render();
		}
	}	
	
	/* -- -- -- GETTERS / SETTERS -- -- -- */
	public Map<Integer, List<Enemies>> getEnemies() {
		return this.enemies;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public void setKey(int newKey) {
		this.key = newKey;
	}
}
