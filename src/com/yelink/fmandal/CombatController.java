package com.yelink.fmandal;

import java.util.List;
import java.util.Map;

import com.yelink.fmandal.entities.Enemies;

public class CombatController {
	private boolean playerAtt, enemyAtt;
	private Player player;
	private Map<Integer, List<Enemies>> enemies;
	
	public CombatController(Player player, Map<Integer, List<Enemies>> enemies) {
		this.player = player;
		this.enemies = enemies;
	}
	
	
}
