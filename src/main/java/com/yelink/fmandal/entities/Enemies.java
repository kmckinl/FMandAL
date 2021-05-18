package com.yelink.fmandal.entities;

import com.yelink.fmandal.font.FontController;
import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

import java.awt.Rectangle;

import com.yelink.fmandal.CombatController;

public class Enemies {
    private int spawnSet, tick; // Number indicating the set of enemies that spawn at any given time.
    private int defAttSpeed = 30;
    private float width, height;
    private boolean attackRange = false;
    private boolean attackCD = false;
    private boolean aggroed = false;
    private boolean facingLeft = true; // start off facing left
    private boolean hit = false;
    private Vector3f position, movement, playerPos;
    private VertexArray enemy;
    private Texture enemyTex;
    private FontController fc;
    private CombatController cc;
    private Rectangle charBounds, reachBounds;
    private Boss boss; // Want to improve this
    /*
     *Proposed improvement: EnemyController creates Map<Integer, List<Enemies>> of all Enemies
     *Each list in the map contains the enemies and boss for a given section (4 screens each?)
     */

    public Enemies(int spawn) { // Might add the FontController back into this if necessary
        this.boss = new Boss("Panda");
        this.spawnSet = spawn;
        this.tick = 0;
        // For Panda currently
        this.width = 200.0f;
        this.height = 200.0f;
        this.position = new Vector3f(2000.0f, 500.0f, 0.0f);
        this.movement = new Vector3f(0.0f, 0.0f, 0.0f);
        // This should be in a single Enemy Controller (which may be this class)
        playerPos = new Vector3f(0.0f, 0.0f, 0.0f);

        // This is just based around the panda only, will need to really sit down with each
        // sprite and determine the correct values for each and hardcode it, may in some .txt file
        int centerX = (int) this.position.x + 100;
        int centerY = (int) this.position.y + 100;

        this.charBounds = new Rectangle(centerX - (int) (this.width / 2), centerY - (int) (this.height / 2), (int) this.width, (int) this.height);
        // I just realized these reach bounds don't take into consideration the entity turning around
        this.reachBounds = new Rectangle(centerX - 80, centerY - 40, 80, 50);

        float[] vertices = new float[] {
                position.x, position.y, 0.0f,
                position.x + 256.0f, position.y, 0.0f,
                position.x + 256.0f, position.y + 280.0f, 0.0f,
                position.x, position.y + 280.0f, 0.0f
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

        this.defAttSpeed *= this.boss.getAttSpeed();

        enemy = new VertexArray(vertices, indices, tcs);
        enemyTex = new Texture("res/aqbajiquan.png");
    }

    public void update(Vector3f playerpos, float[] playerCenter) {
        this.tick++;
        this.playerPos.x = playerpos.x;
        this.playerPos.y = playerpos.y;
        checkPlayerDistance(playerCenter);
        if (this.aggroed) {
            move();
            if (this.tick % 60 == 0) {
                // Currently a "1 second" approx attack timer, this can be customized based on the enemy.
                //if (Math.abs(playerPos.x - (this.position.x + this.movement.x)) + Math.abs(this.playerPos.y - (this.position.y + this.movement.y)) < 100) {
                if (Math.abs(playerCenter[0] - getCenter()[0]) + Math.abs(playerCenter[1] - getCenter()[1]) < 100) {
                    //attack();
                    this.attackRange = true;
                }

            }
        }

        if (this.attackCD) {
            this.defAttSpeed -= 1;
            if (this.defAttSpeed == 0) {
                this.defAttSpeed *= this.boss.getAttSpeed();
                this.attackCD = false;
            }
        }

        if(this.tick > 600) {
            this.tick = 0;
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

    public void checkPlayerDistance(float[] playerCenter) {
        // Calculates a Manhattan Distance to determine if Player is within aggro range
        // I may not do aggro so to speak and instead just do set enemies for set screens
        //if (Math.abs(playerPos.x - this.position.x) + Math.abs(playerPos.y - this.position.y) < 2000) {
        if (Math.abs(playerCenter[0] - getCenter()[0]) + Math.abs(playerCenter[1] - getCenter()[1]) < 2000) {
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
            if (x < 0.0f) { this.movement.x -= 1.0f; setBoundsPosition(-1, 0); this.facingLeft = true; }
            else if (x > 0.0f) { this.movement.x += 1.0f; setBoundsPosition(1, 0); this.facingLeft = false; }

            if (y < 0.0f) { this.movement.y -= 1.0f; setBoundsPosition(0, -1); }
            else if (y > 0.0f) { this.movement.y += 1.0f; setBoundsPosition(0, 1);}
        } else if (Math.abs(x) < Math.abs(y)) {
            if (y < 0.0f) { this.movement.y -= 1.0f; setBoundsPosition(0, -1);}
            else if (y > 0.0f) { this.movement.y += 1.0f; setBoundsPosition(0, 1);}

        } else if (Math.abs(x) > Math.abs(y)) {
            if (x < 0.0f) { this.movement.x -= 1.0f; setBoundsPosition(-1, 0); this.facingLeft = true; }
            else if (x > 0.0f) { this.movement.x += 1.0f; setBoundsPosition(1, 0); this.facingLeft = false; }

        }

    }

    public void attack() {
        System.out.println("attack");
    }


    public Rectangle getBounds() {
        return this.charBounds;
    }

    public Rectangle getReach() {
        return this.reachBounds;
    }

    public void setBoundsPosition(int x, int y) {
        this.charBounds.setLocation((int) (x + this.charBounds.getX()), (int) (y + this.charBounds.getY()));
        this.reachBounds.setLocation((int) (x + this.reachBounds.getX()), (int) (y + this.reachBounds.getY()));
    }

    public void switchReachBounds() {
        if (this.facingLeft) {
            this.reachBounds.setLocation((int) this.reachBounds.getX() - 80, (int) this.reachBounds.getY());
        } else {
            this.reachBounds.setLocation((int) this.reachBounds.getX() + 80, (int) this.reachBounds.getY());
        }
    }

    public float[] getCenter() {
        return new float[] { (float)  this.charBounds.getCenterX(), (float) this.charBounds.getCenterY() };
    }

    public void setAttackRange(boolean att) {
        this.attackRange = att;
    }

    public boolean getAttackRange() {
        return this.attackRange;
    }

    public void setAttackCD(boolean att) {
        this.attackCD = att;
    }

    public boolean getAttackCD() {
        return this.attackCD;
    }

    public void setPlayerPos(Vector3f playerpos) {
        this.playerPos.x = playerpos.x;
        this.playerPos.y = playerpos.y;
    }

    public Boss getBoss() {
        return this.boss;
    }
}
