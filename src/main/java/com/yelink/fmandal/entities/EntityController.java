package com.yelink.fmandal.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yelink.fmandal.Player;

public class EntityController {
    private int key = 0;
    private Player player;
    private Map<Integer, List<Item>> items = new HashMap<Integer, List<Item>>();

    public EntityController(Player player) {
        this.player = player;
        items.put(key, new ArrayList<Item>());
        items.get(key).add(new Item(0, 900.0f, 160.0f));
    }

    public void update() {
        for (int key : items.keySet()) {
            for (Item item : items.get(key)) {
                item.update(player.getPosition());

                if (Math.abs(player.getPosition().x - item.getPosition().x) + Math.abs(player.getPosition().y - item.getPosition().y)<  500.0f) {
                    item.checkCollision(player);
                }
            }
        }
    }

    public void render() {
        for (int key : items.keySet()) {
            for (Item item : items.get(key)) {
                if (item.getPickedUp()) {
                    items.remove(key);
                } else {
                    item.render();
                }

            }
        }
    }
}
