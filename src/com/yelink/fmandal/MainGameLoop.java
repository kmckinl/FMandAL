package com.yelink.fmandal;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import com.yelink.fmandal.InputController;
import com.yelink.fmandal.development.Test;
import com.yelink.fmandal.entities.Enemies;
import com.yelink.fmandal.font.FontController;
import com.yelink.fmandal.rendering.Display;
import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.utilities.FontUtils;
import com.yelink.fmandal.utilities.Matrix4f;


public class MainGameLoop implements Runnable {
	private long variableYieldTime, lastTime, timer;
	private boolean running = true;
	
	private Thread thread;
	
	private Player player;
	private Enemies enemy;
	
	private FontController fontController;
	
	public void start() {
		thread = new Thread(this, "game loop");
		thread.start();
	}
	
	public void update() {
		Display.updateDisplay();
		player.update();
		// TODO add input checks/updates
		if (InputController.keys[GLFW_KEY_UP]) {
			
		}
		if (InputController.keys[GLFW_KEY_DOWN]) {
			
		}
		if (InputController.keys[GLFW_KEY_LEFT]) {
			
		}
		if (InputController.keys[GLFW_KEY_RIGHT]) {
			
		}
		if (InputController.keys[GLFW_KEY_SPACE]) {
			player.setSwap();
		}
		if (InputController.keys[GLFW_KEY_Q]) {
			player.attack();
		}
		if (InputController.keys[GLFW_KEY_E]) {
			//Really need to figure out a better way of doing this as it is
			//Currently crashing if more than one attacks trigger at once.
			player.setTrigger(false);
		}

	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		int error = glGetError();
		if (error != GL_NO_ERROR) {
			System.out.println(error);
		}
		
		// TODO render stuff
		player.render();
		enemy.render();
		//FontOld.bangers.draw();
		fontController.renderText();
		
		glfwSwapBuffers(Display.window);
	}

	@Override
	public void run() {
		// Create the display 
		Display.createDisplay("The Adventures of Fly Man and Aqua Lad", false);

		// Load shaders
		Shader.loadAll();
		
		// Create projection matrix
		Matrix4f pr_matrix = Matrix4f.orthographic(0, Display.WINDOW_WIDTH, Display.WINDOW_HEIGHT, 0, 1, -1);
		
		// Set pr_matrix
		Shader.TEST.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.CHARACTER.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.CHARACTER.setUniform1i("tex", 1);
		Shader.FONT.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.FONT.setUniform1i("tex", 1);
		
		// Create entities
		fontController = new FontController();
		player = new Player(fontController);
		enemy = new Enemies();
		fontController.loadText("bangers", "level", 250, 250, false);
		
		//FontOld.bangers.loadText("100", 250, 250, false);
		// Game Loop
		while(running) {
			sync(60);
			
			if (glfwWindowShouldClose(Display.window)) running = false; 
		}
		
		// Clean up
		//test.cleanUp();
		Display.closeDisplay();
		
	}
	
	private void sync(int FPS) {
		/*
		 * Code from lwjgl forums: orig author kappa - adapted by Dapperpixpy
		 * 
		 */
		
		if(FPS <= 0) return;
		
		long sleepTime = 1000000000 / FPS;
		
		long yieldTime = Math.min(sleepTime, this.variableYieldTime + sleepTime % (1000 * 1000));
		long overSleep = 0;
		
		try {
			while(true) {
				long t = System.nanoTime() - this.lastTime;
				
				if (t < sleepTime - yieldTime) {
					this.thread.sleep(1);
				} else if (t < sleepTime) {
					this.thread.yield();
				} else {
					overSleep = t - sleepTime;
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			this.lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
			
			if (overSleep > this.variableYieldTime) {
				this.variableYieldTime = Math.min(this.variableYieldTime + 200 * 1000, sleepTime);
			} else if (overSleep < this.variableYieldTime - 200 * 1000) {
				this.variableYieldTime = Math.max(this.variableYieldTime - 2 * 1000, 0);
			}
		}

		update();
		render();
	}
	
	public static void main(String[] args) {
		new MainGameLoop().start();
	}
}
