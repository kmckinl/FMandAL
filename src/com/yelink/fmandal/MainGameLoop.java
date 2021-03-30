package com.yelink.fmandal;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import com.yelink.fmandal.rendering.Display;

public class MainGameLoop implements Runnable {
	private long variableYieldTime, lastTime;
	private boolean running = true;
	
	private Thread thread;
	
	public void start() {
		thread = new Thread(this, "game loop");
		thread.start();
	}
	
	public void update() {
		Display.updateDisplay();
		
		// TODO add input checks/updates

	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		int error = glGetError();
		if (error != GL_NO_ERROR) {
			System.out.println(error);
		}
		
		// TODO render stuff

		
		glfwSwapBuffers(Display.window);
	}

	@Override
	public void run() {
		// Create the display 
		Display.createDisplay("Prop Hunt", false);
		
		// Load shaders
		//Shader.loadAll();
		
		// Create projection matrix
		//Matrix4f pr_matrix = Matrix4f.orthographic(0, Display.WINDOW_WIDTH, Display.WINDOW_HEIGHT, 0, 1000, -1000);
		
		// Set pr_matrix

		
		// Create entities

		
		// Game Loop
		while(running) {
			sync(60);
			
			if (glfwWindowShouldClose(Display.window)) running = false; 
		}
		
		// Clean up
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
