package com.yelink.fmandal;

import com.yelink.fmandal.entities.*;
import com.yelink.fmandal.font.FontController;
import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.utilities.Matrix4f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class MainGameLoop implements Runnable {
    private long variableYieldTime, lastTime, timer;
    private boolean running = true;

    private Thread thread;

    private Player player;
    private Enemies enemy;
    private Terrain terrain;
    private Item item;
    private EnemyController enemyController;
    private EntityController entityController;
    private CombatController combatController;
    private FontController fontController;
    private HUDController hudController;
    //private SoundManager soundManager;

    private Map<Integer, List<Enemies>> enemies = new HashMap<Integer, List<Enemies>>();

    public void start() {
        thread = new Thread(this, "game loop");
        thread.start();
    }

    public void update() {
        Display.updateDisplay();

        // TODO: add input controller
        //item.update(player.getPosition());
        enemyController.update(player.getPosition(), player.getCharacter().getCenter());
        entityController.update();
        combatController.update();

        if (InputController.keys[GLFW_KEY_UP]) {
            player.setIdle(false);
            player.setMoving(true);
            player.move(0.0f, -1.0f);
        }
        if (InputController.keys[GLFW_KEY_DOWN]) {
            player.setIdle(false);
            player.setMoving(true);
            player.move(0.0f, 1.0f);
        }
        if (InputController.keys[GLFW_KEY_LEFT]) {
            player.setIdle(false);
            player.setMoving(true);
            player.move(-1.0f, 0.0f);
        }
        if (InputController.keys[GLFW_KEY_RIGHT]) {
            player.setIdle(false);
            player.setMoving(true);
            player.move(1.0f, 0.0f);
        }
        if (InputController.keys[GLFW_KEY_TAB]) {
            player.setSwap();
        }
        if (InputController.keys[GLFW_KEY_Q]) {
            combatController.attack(true, false);
        }
        if (InputController.keys[GLFW_KEY_E]) {
            //Really need to figure out a better way of doing this as it is
            //Currently crashing if more than one attacks trigger at once.
            player.setAttackCheck(false);
            //soundManager.play();
        }
        hudController.update();
        // Update entities
        player.updateFrame();
        player.update();

    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        int error = glGetError();
        if (error != GL_NO_ERROR) {
            System.out.println(error);
        }

        // TODO: render stuff
        player.render();
        //enemy.render();
        enemyController.render();
        terrain.render();
        //FontOld.bangers.draw();
        fontController.renderText(player.getPosition());

        //item.render();
        entityController.render();
        hudController.render();

        glfwSwapBuffers(Display.window);
    }

    @Override
    public void run() {
        Display.createDisplay("The Adventures of Fly-Man and Aqua Lad", false);

        // TODO: load all shaders
        Shader.loadAll();
        // TODO: create pr_matrix and set the uniformMat4f and tex for each shader
        Matrix4f pr_matrix = Matrix4f.orthographic(0, Display.WINDOW_WIDTH, Display.WINDOW_HEIGHT, 0, 1, -1);

        // Set pr_matrix
        Shader.TEST.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.CHARACTER.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.CHARACTER.setUniform1i("tex", 1);
        Shader.FONT.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.FONT.setUniform1i("tex", 1);
        Shader.ENEMIES.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.ENEMIES.setUniform1i("tex", 1);
        Shader.TERRAIN.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.TERRAIN.setUniform1i("tex", 1);
        Shader.HUD.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.HUD.setUniform1i("tex", 1);
        Shader.ITEM.setUniformMat4f("pr_matrix", pr_matrix);
        Shader.ITEM.setUniform1i("tex", 1);
        // Create entities
        fontController = new FontController();

        player = new Player(fontController);
        enemyController = new EnemyController(fontController);

        fontController.loadText("bangers", "Level 1", 1720, 64, false);
        combatController = new CombatController(player, enemyController.getEnemies());

        terrain = new Terrain();

        //item = new Item(0, 900.0f, 20.0f);
        entityController = new EntityController(player);

        hudController = new HUDController(player);

        // Game Loop
        while(running) {
            sync(60);

            if (glfwWindowShouldClose(Display.window)) running = false;
        }

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
