package com.yelink.fmandal.entities;

import com.yelink.fmandal.font.FontController;
import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;
import com.yelink.fmandal.CombatController;

public class Enemies {
	private int spawnSet; // Number indicating the set of enemies that spawn at any given time.
	private float width, height;
	private boolean aggroed = false;
	private Vector3f position, movement, playerPos;
	private VertexArray enemy;
	private Texture enemyTex;
	private FontController fc;
	private CombatController cc;
	
	/* 
	 *Proposed improvement: EnemyController creates Map<Integer, List<Enemies>> of all Enemies
	 *Each list in the map contains the enemies and boss for a given section (4 screens each?)
	 */
	
	public Enemies(int spawn) { // Might add the FontController back into this if necessary
		this.spawnSet = spawn;
		
		// For Panda currently
		this.width = 200.0f;
		this.height = 200.0f;
		position = new Vector3f(2000.0f, 500.0f, 0.0f);
		movement = new Vector3f(0.0f, 0.0f, 0.0f);
		// This should be in a single Enemy Controller (which may be this class)
		playerPos = new Vector3f(0.0f, 0.0f, 0.0f);
		
		float[] vertices = new float[] {
			position.x, position.y, 0.0f,
			position.x + 400.0f, position.y, 0.0f,
			position.x + 400.0f, position.y + 400.0f, 0.0f,
			position.x, position.y + 400.0f, 0.0f
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
		
		enemy = new VertexArray(vertices, indices, tcs);
		enemyTex = new Texture("res/panda.png"); 
	}
	
	public void update(Vector3f playerpos) {
		this.playerPos.x = playerpos.x;
		this.playerPos.y = playerpos.y;
		checkPlayerDistance();
		if (this.aggroed) {
			move();
		}
	}
	
	public void render() {
		enemyTex.bind();
		Shader.ENEMIES.enable();
		enemy.bind();
		Shader.ENEMIES.setUniformMat4f("vw_matrix", Matrix4f.translate(this.movement));
		enemy.draw();
		
		Shader.ENEMIES.disable();
		enemyTex.unbind();
	}
	
	public void checkPlayerDistance() {
		// Calculates a Manhattan Distance to determine if Player is within aggro range
		// I may not do aggro so to speak and instead just do set enemies for set screens
		if (Math.abs(playerPos.x - this.position.x) + Math.abs(playerPos.y - this.position.y) < 2000) {
			this.aggroed = true;
			
		} else {
			this.aggroed = false;
		}

	}
	
	public void move() {
		// Loose follow system, prioritize greater difference
		// When in hitting range, don't move, instead attack
		float x = playerPos.x - (this.position.x + this.movement.x);
		float y = playerPos.y - (this.position.y + this.movement.y);

		if (x == y) {
			if (x < 0.0f) { this.movement.x -= 1.0f; }
			else if (x > 0.0f) { this.movement.x += 1.0f; }
			//System.out.println("equal");
			if (y < 0.0f) { this.movement.y -= 1.0f; }
			else if (y > 0.0f) { this.movement.y += 1.0f; }
		} else if (Math.abs(x) < Math.abs(y)) {
			if (y < 0.0f) { this.movement.y -= 1.0f; }
			else if (y > 0.0f) { this.movement.y += 1.0f; }
			//System.out.println("less");
		} else if (Math.abs(x) > Math.abs(y)) {
			if (x < 0.0f) { this.movement.x -= 1.0f; }
			else if (x > 0.0f) { this.movement.x += 1.0f; }
			//System.out.println("greater");
		}
		//System.out.println(this.position.y + this.movement.y);
	}
	
	/* -- -- -- GETTERS / SETTERS -- -- -- */
	public float[] getBounds() {
		// Returns a float array of the left, right, up, and down boundaries
		return new float[] { this.position.x - (this.width / 2), this.position.x + (this.width / 2), 
							this.position.y - (this.height / 2), this.position.y + (this.height / 2) };
	}
	
	public void setPlayerPos(Vector3f playerpos) {
		this.playerPos.x = playerpos.x;
		this.playerPos.y = playerpos.y;
	}
}
