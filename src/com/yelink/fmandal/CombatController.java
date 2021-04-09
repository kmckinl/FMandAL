package com.yelink.fmandal;

import java.awt.Rectangle;
import java.util.List;
import java.util.Map;

import com.yelink.fmandal.entities.Enemies;

public class CombatController {
	private int tick = 0;
	private boolean playerAtt, playerCD, enemyAtt;
	private Player player;
	private Map<Integer, List<Enemies>> enemies;
	
	public CombatController(Player player, Map<Integer, List<Enemies>> enemies) {
		this.player = player;
		this.enemies = enemies;
		this.playerAtt = false;
		this.playerCD = false;
		this.enemyAtt = false;
	}
	
	public void update() {
		this.tick++;
		
		if (!player.getAttack()) {
			this.playerCD = false;
		}
		
		for (int key : enemies.keySet()) {
			for (Enemies enemy : enemies.get(key)) {
				if (!enemy.getAttackCD()) {
					//System.out.println("attackCD");
					if (enemy.getAttackRange()) {
						//System.out.println("attackRange");
						if (attackCheck(player.getCharBounds(), enemy.getReach())) {
							enemy.setAttackCD(true);
							player.getCharacter().setHealth(-enemy.getBoss().getAttPower());
							//System.out.println(player.getCharacter().getHealth());
						}
					}
				}
				
			}
		}
		
		if (this.tick >= 1000) {
			this.tick = 0;
		}
	}
	
	public void attack(boolean playeratt, boolean enemyatt) {
		this.playerAtt = playeratt;
		player.setAttackCheck(playeratt);
		this.enemyAtt = enemyatt;
		
		if (!playerCD) {
			for (int key : enemies.keySet()) {
				for (Enemies enemy : enemies.get(key)) {
					if (enemy.getAttackRange()) {
						if (attackCheck(player.getReachBounds(), enemy.getBounds())) {
							enemy.getBoss().setHealth(-player.getCharacter().getPower());
							
							System.out.println(enemy.getBoss().getHealth());
							this.playerCD = true;
						}
					}
				}
			}
		}
	}
	
	public boolean attackCheck(Rectangle player, Rectangle enemy) {
		if (player.intersects(enemy)) {
			return true;
		} else {
			return false;
		}
	}
	
	/* -- -- -- GETTERS / SETTERS -- -- -- */
	public boolean getPlayerAtt() {
		return this.playerAtt;
	}
	
	public void setPlayerAtt(boolean att) {
		this.playerAtt = att;
	}

	public Map<Integer, List<Enemies>> getEnemies() {
		return this.enemies;
	}
}
