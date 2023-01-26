package com.izza.kalahapplication.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.izza.kalahapplication.model.Player;

@Component
public class PlayerService {
	
	

	private static Map<Integer, Player> playerData = new ConcurrentHashMap<>();
	AtomicInteger playerId = new AtomicInteger(0);
	
	
	public Player addNewPlayer() {
		Player player = new Player(
			playerId.incrementAndGet(),
			"Player "+ playerId.get()
		);
		playerData.put(playerId.get(), player);
		System.out.println("Player "+ playerId.get() + " was added");
		return player;
	}
	

	public Player getPlayerById(int id) {
		return playerData.get(id);
	}
	
}
