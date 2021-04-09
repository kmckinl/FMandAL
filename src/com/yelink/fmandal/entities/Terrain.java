package com.yelink.fmandal.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

public class Terrain {
	
	private int currentScreen = 0; // pre-load the next screen 
	private boolean loadNextScreen = false; 
	public List<Tile> terrainTiles = new ArrayList<Tile>();
	
	private List<List<Integer>> houseList = new ArrayList<List<Integer>>();
	private List<List<List<Integer>>> roadList = new ArrayList<List<List<Integer>>>();
	
	private Map<Integer, List<Tile>> environment = new HashMap<Integer, List<Tile>>();
	// I don't like this, but will figure it out tomorrow
	
	private Texture tileSheet;
	
	public Terrain() {
		// Set up the house list
		for (int i = 0; i < 5; i++) {
			houseList.add(new ArrayList<Integer>());
		}
		
		
		generateHouseList();
		
		/*
		 * List of Lists of road tiles
		 * First nested list contains the screen
		 * Nested list of screens is of rows
		 * Each row list contains the tiles
		 * 
		 */
		for (int i = 0; i < 5; i++) {
			roadList.add(new ArrayList<List<Integer>>());
			for (int k = 0; k < 5; k++) {
				roadList.get(i).add(new ArrayList<Integer>());
			}
		}
		boolean manhole = false;
		Random rand = new Random();
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 10; k++) {
				int num = rand.nextInt(5);
				if (roadList.get(0).get(i).size() > 3) {
					while (roadList.get(0).get(i).get(roadList.get(0).get(i).size() - 1) == num || 
							roadList.get(0).get(i).get(roadList.get(0).get(i).size() - 2) == num ||
							roadList.get(0).get(i).get(roadList.get(0).get(i).size() - 3) == num) {
						num = rand.nextInt(5);
					}
				}
				if (i == 3) {
					if (num == 3 && !manhole) {
						manhole = true;
						while (num  == 3) {
							num = rand.nextInt(5);
						}
						
					}
				}
				roadList.get(0).get(i).add(num);
			}	
		}
		// Screen 0
		// Top Row / Top Sidewalk
		environment.put(this.currentScreen, new ArrayList<Tile>());
		
		generateTiles(0);
		//generateTiles(1);
		
		//tileSheet = new Texture("res/roadsprite.png");
	}
	
	
	public void render() {
		for (Tile tile : environment.get(this.currentScreen)) {
			tile.getTexture().bind(); // I don't like this, it all will need to be one tile sheet
			Shader.TERRAIN.enable();
			tile.getVAO().bind();
			Shader.TERRAIN.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
			tile.getVAO().draw();
			
			tile.getVAO().unbind();
			Shader.TERRAIN.disable();
			tile.getTexture().unbind();
		}
	}
	
	public void swapScreen() {
		if (environment.containsKey(currentScreen + 1) && this.currentScreen < 4) {
			
			this.currentScreen++;
			environment.remove(this.currentScreen - 1);
			generateTiles(this.currentScreen + 1);
		}
	}
	
	public void generateHouseList() {
		houseList.get(0).add(1);
		houseList.get(0).add(0);
		houseList.get(0).add(1);
		houseList.get(0).add(0);
		houseList.get(0).add(1);
		houseList.get(0).add(1);
		houseList.get(0).add(2);
		houseList.get(0).add(3);
		houseList.get(0).add(4);
		houseList.get(0).add(3);
		
		houseList.get(1).add(4);
		houseList.get(1).add(4);
		houseList.get(1).add(4);
		houseList.get(1).add(1);
		houseList.get(1).add(0);
		houseList.get(1).add(0);
		houseList.get(1).add(1);
		houseList.get(1).add(1);
		houseList.get(1).add(2);
		houseList.get(1).add(4);
		
		houseList.get(2).add(3);
		houseList.get(2).add(1);
		houseList.get(2).add(1);
		houseList.get(2).add(0);
		houseList.get(2).add(1);
		houseList.get(2).add(0);
		houseList.get(2).add(2);
		houseList.get(2).add(1);
		houseList.get(2).add(0);
		houseList.get(2).add(1);
		
		houseList.get(3).add(1);
		houseList.get(3).add(0);
		houseList.get(3).add(1);
		houseList.get(3).add(2);
		houseList.get(3).add(4);
		houseList.get(3).add(4);
		houseList.get(3).add(4);
		houseList.get(3).add(3);
		houseList.get(3).add(1);
		houseList.get(3).add(1);
		
		houseList.get(4).add(0);
		houseList.get(4).add(2);
		houseList.get(4).add(3);
		houseList.get(4).add(4);
		houseList.get(4).add(4);
		houseList.get(4).add(3);
		houseList.get(4).add(1);
		houseList.get(4).add(0);
		houseList.get(4).add(1);
		houseList.get(4).add(2);
	}

	public void generateTiles(int screen) {
		if (screen < 5) {
			System.out.println("hit");
			for (int i = 0; i < 10; i++) {
				environment.get(screen).add(new Tile(i * 192.0f, 
													-256.0f, houseList.get(screen).get(i) / 5.0f, 0, true));
			}
	
			for (int i = 0; i < 10; i++) {
				environment.get(screen).add(new Tile(i * 192.0f, 128.0f, 
													roadList.get(screen).get(0).get(i) / 5.0f, 0.0f, false));
			}
			
			
			// 2nd row
			for (int i = 0; i < 10; i++) {
				environment.get(screen).add(new Tile(i * 192.0f, 320.0f,
													roadList.get(screen).get(1).get(i) / 5.0f, 1.0f / 5.0f, false));
			}
			
			// 3rd row
			for (int i = 0; i < 10; i++) {
				environment.get(screen).add(new Tile(i * 192.0f, 512.0f,
													roadList.get(screen).get(2).get(i) / 5.0f, 2.0f / 5.0f, false));
			}
			
			// 4th row
			for (int i = 0; i < 10; i++) {
				environment.get(screen).add(new Tile(i * 192.0f, 704.0f,
													roadList.get(screen).get(3).get(i) / 5.0f, 3.0f / 5.0f, false));
			}
			
			// 5th row
			for (int i = 0; i < 10; i++) {
				environment.get(screen).add(new Tile(i * 192.0f, 896.0f,
													roadList.get(screen).get(4).get(i) / 5.0f, 4.0f / 5.0f, false));
			}
		}
	}
}
