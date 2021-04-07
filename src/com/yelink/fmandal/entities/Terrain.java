package com.yelink.fmandal.entities;

import java.util.ArrayList;
import java.util.List;

import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

public class Terrain {
	
	public List<Tile> terrainTiles = new ArrayList<Tile>();
	
	// I don't like this, but will figure it out tomorrow
	
	private Texture tileSheet;
	
	public Terrain() {
		int[][] tileGrid = new int[][] {
			{0,0},
			{0,1},
			{0,2},
			{0,3},
			{1,0},
			{1,1},
			{1,2},
			{1,3}
		};
		float y = 500.0f;
		terrainTiles.add(new Tile(0.0f, y+0.0f, 0.0f, 0.0f));
		terrainTiles.add(new Tile(0.0f, y+64.0f, 0.0f, 0.25f));
		terrainTiles.add(new Tile(0.0f, y+128.0f, 0.0f, 0.5f));
		terrainTiles.add(new Tile(0.0f, y+192.0f, 0.0f, 0.75f));
		terrainTiles.add(new Tile(64.0f, y+0.0f, 0.5f, 0.0f));
		terrainTiles.add(new Tile(64.0f, y+64.0f, 0.5f, 0.25f));
		terrainTiles.add(new Tile(64.0f, y+128.0f, 0.5f, 0.5f));
		terrainTiles.add(new Tile(64.0f, y+192.0f, 0.5f, 0.75f));
		terrainTiles.add(new Tile(128.0f, y+0.0f, 0.0f, 0.0f));
		terrainTiles.add(new Tile(128.0f, y+64.0f, 0.0f, 0.25f));
		terrainTiles.add(new Tile(128.0f, y+128.0f, 0.0f, 0.5f));
		terrainTiles.add(new Tile(128.0f, y+192.0f, 0.0f, 0.75f));
		terrainTiles.add(new Tile(192.0f, y+0.0f, 0.5f, 0.0f));
		terrainTiles.add(new Tile(192.0f, y+64.0f, 0.5f, 0.25f));
		terrainTiles.add(new Tile(192.0f, y+128.0f, 0.5f, 0.5f)); 
		terrainTiles.add(new Tile(192.0f, y+192.0f, 0.5f, 0.75f));
		terrainTiles.add(new Tile(256.0f, y+0.0f, 0.0f, 0.0f));
		terrainTiles.add(new Tile(256.0f, y+64.0f, 0.0f, 0.25f));
		terrainTiles.add(new Tile(256.0f, y+128.0f, 0.0f, 0.5f));
		terrainTiles.add(new Tile(256.0f, y+192.0f, 0.0f, 0.75f));
		terrainTiles.add(new Tile(320.0f, y+0.0f, 0.5f, 0.0f));
		terrainTiles.add(new Tile(320.0f, y+64.0f, 0.5f, 0.25f));
		terrainTiles.add(new Tile(320.0f, y+128.0f, 0.5f, 0.5f));
		terrainTiles.add(new Tile(320.0f, y+192.0f, 0.5f, 0.75f));
		terrainTiles.add(new Tile(384.0f, y+0.0f, 0.0f, 0.0f));
		terrainTiles.add(new Tile(384.0f, y+64.0f, 0.0f, 0.25f));
		terrainTiles.add(new Tile(384.0f, y+128.0f, 0.0f, 0.5f));
		terrainTiles.add(new Tile(384.0f, y+192.0f, 0.0f, 0.75f));
		terrainTiles.add(new Tile(448.0f, y+0.0f, 0.5f, 0.0f));
		terrainTiles.add(new Tile(448.0f, y+64.0f, 0.5f, 0.25f));
		terrainTiles.add(new Tile(448.0f, y+128.0f, 0.5f, 0.5f));
		terrainTiles.add(new Tile(448.0f, y+192.0f, 0.5f, 0.75f));

		
		tileSheet = new Texture("res/road.png");
	}
	
	
	public void render() {
		tileSheet.bind();
		for (Tile tile : terrainTiles) {
			Shader.TERRAIN.enable();
			tile.getVAO().bind();
			Shader.TERRAIN.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
			tile.getVAO().draw();
			
			Shader.TERRAIN.disable();
		}
		tileSheet.unbind();
	}
	
}
