package com.yelink.fmandal.entities;

import java.awt.Rectangle;

import com.yelink.fmandal.Player;
import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

public class Item {
	// Temporary item class for the three items
	private int id, tick, health;
	private float width, height;
	private boolean pickedUp = false;
	private Vector3f position, move;
	private VertexArray vao;
	private Texture texture;
	private Rectangle bounds;
	public Item(int id, float x, float y) {
		this.id = id;
		this.tick = 0;
		this.health = 30;
		this.position = new Vector3f(x, y, 0.1f);
		this.move = new Vector3f(0.0f, 0.0f, 0.0f);
		
		float[] vertices = new float[] {
			this.position.x, this.position.y, this.position.z,
			this.position.x + 24.0f, this.position.y, this.position.z,
			this.position.x + 24.0f, this.position.y + 48.0f, this.position.z,
			this.position.x, this.position.y + 48.0f, this.position.z
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			0, 2, 3
		};
		
		float[] tcs = new float[] {
			0, 0,
			1, 0,
			1, 1,
			0, 1
		};
		
		this.vao = new VertexArray(vertices, indices, tcs);
		
		if (this.id == 0) {
			this.width = 20.0f;
			this.height = 44.0f;
			this.texture = new Texture("res/judgementenergy.png");
			this.bounds = new Rectangle((int) x + 2, (int) y + 2, (int) this.width, (int) this.height);
		}
	}
	
	public void update(Vector3f playerpos) {
		this.tick++;
		if (this.tick % 9 == 0 && this.tick < 45) {
			this.move.y++;
		} else if (this.tick % 9 == 0 && this.tick > 45) {
			this.move.y--;
			
		}
		if (this.tick >= 90) { this.tick = 0; }
		
		if (Math.abs(playerpos.x - (this.position.x + this.move.x)) + Math.abs(playerpos.y - (this.position.y + this.move.y)) < 500) {
			//checkCollision(playerpos);
		}
	}
	
	public void render() {
		this.texture.bind();
		Shader.ENEMIES.enable();
		this.vao.bind();
		Shader.ENEMIES.setUniformMat4f("vw_matrix", Matrix4f.translate(this.move));
		this.vao.draw();
		
		Shader.ENEMIES.disable();
		this.vao.unbind();
		this.texture.unbind();
	}
	
	public void checkCollision(Player player) {
		// Currently using player position which is top left corner, will not want to do that going forward
		// for the outer check of which left, right, up, or down
		if (player.getCharBounds().intersects(this.bounds)) {
			// May only allow pick up if health below max
			this.pickedUp = true;
			player.updateHealth(this.health);
		}

	}
	
	/* -- -- -- GETTERS / SETTERS -- -- -- */
	public void setMove(float x, float y) {
		this.move.x += x;
		this.move.y += y;
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public float[] getCenter() {
		return new float[] {(this.position.x + this.move.x + (this.width / 2)), (this.position.y + this.move.y + (this.height / 2)) };
	}
	
	public boolean getPickedUp() {
		return this.pickedUp;
	}
	
	public void setPickedUp(boolean pUp) {
		this.pickedUp = pUp;
	}
	
	public Vector3f getPosition() {
		return new Vector3f(this.position.x + this.move.x, this.position.y + this.move.y, this.position.z);
	}
}
