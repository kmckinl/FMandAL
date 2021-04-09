package com.yelink.fmandal.entities;

import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Vector3f;

public class Tile {
	
	private float xOffset, yOffset;
	private boolean house;
	
	private Vector3f position, movement;
	private VertexArray vao;
	private Texture texture;
	
	public Tile(float x, float y, float xOffset, float yOffset, boolean house) {
		this.position = new Vector3f(x, y, 0.1f);
		this.movement = new Vector3f(0.0f, 0.0f, 0.0f);
		
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.house = house;
		
		float[] vertices;
		float[] tcs;
		
		if (this.house) {
			vertices = new float[] {
				this.position.x, this.position.y, 0.2f,
				this.position.x + 192.0f, this.position.y, 0.2f,
				this.position.x + 192.0f, this.position.y + 384.0f, 0.2f,
				this.position.x, this.position.y + 384.0f, 0.2f
			};
			
			tcs = new float[] {
					xOffset, yOffset,
					xOffset + (1.0f / 5.0f), yOffset,
					xOffset + (1.0f / 5.0f), yOffset + 1.0f,
					xOffset, yOffset + 1.0f
			};
		} else {
			vertices = new float[] {
				this.position.x, this.position.y, 0.2f,
				this.position.x + 192.0f, this.position.y, 0.2f,
				this.position.x + 192.0f, this.position.y + 192.0f, 0.2f,
				this.position.x, this.position.y + 192.0f, 0.2f
			};
				
			tcs = new float[] {
				xOffset, yOffset,
				xOffset + (1.0f / 5.0f), yOffset,
				xOffset + (1.0f / 5.0f), yOffset + (1.0f / 5.0f),
				xOffset, yOffset + (1.0f / 5.0f)
			};
		}
		
		
		byte[] indices = new byte[] {
			0, 1, 2, 
			0, 2, 3
		};
		
		vao = new VertexArray(vertices, indices, tcs);
		if (this.house) {
			texture = new Texture("res/houses.png");
		} else {
			texture = new Texture("res/roadsprite.png");
		}
		
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public float getYOffset() {
		return this.yOffset; 
	}
	
	public VertexArray getVAO() {
		return this.vao;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public boolean getHouse() {
		return this.house;
	}
}
