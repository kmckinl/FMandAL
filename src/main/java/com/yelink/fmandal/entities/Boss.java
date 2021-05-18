package com.yelink.fmandal.entities;

public class Boss {
    private int health;
    private int attPower;
    private int attSpeed;
    private int speed;
    private boolean miniBoss;
    private String name;

    /*
     * Bosses: Panda, Cat, Weed, Office Man
     * Cat uses headbutt way too often
     * Panda climbs up away from your ability to hit
     * Weed won't stop running away
     * Office Man tries to drop kick too often
     *
     */
    public Boss(String name) {
        this.name = name;

        if (this.name == "Panda") {
            this.health = 200;
            this.attPower = 4;
            this.attSpeed = 5;
            this.speed = 2;
        }
    }

    /* -- -- -- GETTERS / SETTERS -- -- -- */
    public int getHealth() {
        return this.health;
    }

    public void setHealth(int delHealth) {
        this.health += delHealth;
    }

    public int getAttPower() {
        return this.attPower;
    }

    public int getAttSpeed() {
        return this.attSpeed;
    }

    public int getSpeed() {
        return this.speed;
    }
}
