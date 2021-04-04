package com.yelink.fmandal;

import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;
import com.yelink.fmandal.entities.Character;
import com.yelink.fmandal.font.FontController;

public class Player {
	private int text;
	private int swapCD = 300;
	private boolean swapping = false;
	private boolean textTrigger = false;
	private float moveX, moveY;
	private Vector3f position;
	private Character flyMan, aquaLad;
	private VertexArray FlyMan, AquaLad;
	private Texture FlyTexture, LadTexture;
	private FontController fc;
	
	private boolean isFlyMan = true;
	
	public Player(FontController fc) {
		this.fc = fc;
		
		// Initialize position
		position = new Vector3f(500.0f, 500.0f, 0.0f);
		
		// Initialize movement to Fly Man
		moveX = 0.0f;
		moveY = 0.0f;
		
		float[] vertices = new float[] {
			position.x, position.y, 0.0f,
			position.x + 200.0f, position.y, 0.0f,
			position.x + 200.0f, position.y + 200.0f, 0.0f,
			position.x, position.y + 200.0f, 0.0f
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
		flyMan = new Character("Fly-Man");
		aquaLad = new Character("Aqua Lad");
		FlyMan = new VertexArray(vertices, indices, tcs);
		FlyTexture = new Texture("res/flyman.png");
		AquaLad = new VertexArray(vertices, indices, tcs);
		LadTexture = new Texture("res/aqualad.png");
	}
	
	public void update() {

		if (this.swapping) {
			this.swapCD -= 1;
			if (this.swapCD <= 0) {
				this.swapCD = 300;
				this.swapping = false;
			}
		}

	}
	
	public void render() {
		if (isFlyMan) {
			FlyTexture.bind();
			Shader.CHARACTER.enable();
			FlyMan.bind();
			Shader.CHARACTER.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(this.moveX, this.moveY, 0.0f)));
			FlyMan.draw();
			
			Shader.CHARACTER.disable();
			FlyTexture.unbind();
		} else {
			LadTexture.bind();
			Shader.CHARACTER.enable();
			AquaLad.bind();
			Shader.CHARACTER.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(this.moveX, this.moveY, 0.0f)));
			AquaLad.draw();
			
			Shader.CHARACTER.disable();
			LadTexture.unbind();
		}
	}
	
	/* -- -- -- GETTERS / SETTERS -- -- -- */
	public boolean getFlyMan() {
		return this.isFlyMan;
	}
	
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
	
	public void setSwap() {
		if (this.swapCD >= 300) {
			this.swapping = true;
			this.isFlyMan = !this.isFlyMan;
		}
	}
	
	public void setTrigger(boolean trig) {
		this.textTrigger = trig;
	}
	
	public void attack() {
		
		if (textTrigger == false) {
			text = fc.loadText("bangers", "POW!", this.position.x + 50.0f, this.position.y - 50.0f, true);
			textTrigger = true; 
		}
	}
	
	public void move(int x, int y) {
		if (this.isFlyMan) {
			
			
		} else {
			
		}
	}
}
