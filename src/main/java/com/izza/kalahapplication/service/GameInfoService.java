package com.izza.kalahapplication.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.izza.kalahapplication.model.Game;
import com.izza.kalahapplication.model.GameInfo;
import com.izza.kalahapplication.model.Player;

@Component
public class GameInfoService {
	


	private static Map<Integer, GameInfo> gameInfoList = new ConcurrentHashMap<>();
	
	public Optional<GameInfo> getGameInfo(int playerId) {
		
		return gameInfoList.values().stream()
				.filter(g -> {
					return (g.playerOne() != null && g.playerOne().id() == playerId) || (g.playerTwo() != null && g.playerTwo().id() == playerId);
				}).findAny();
	}
	
	public GameInfo setGamePlayer(Player player, Game game) {
		
		Optional<GameInfo> userGameInfo = gameInfoList.values().stream()
				.filter(g -> {
					return g.game().id() == game.id();
				}).findAny();
		
		GameInfo gameInfo;
		
		if (userGameInfo.isPresent()) {
			gameInfo = new GameInfo(
					userGameInfo.get().id(),
					game,
					userGameInfo.get().playerOne(),
					player,
					userGameInfo.get().whosPlaying(),
					null
				);
			
			//set player 2
			gameInfoList.put(
				userGameInfo.get().id(), 
				gameInfo
			);
		} else {
			//set player 1
			gameInfo = new GameInfo(
					game.id(),
					game,
					player,
					null,
					player,
					null
				);
			gameInfoList.put(
				game.id(), gameInfo
				
			);
		}
		
		return gameInfo;
		
	}

	
	public Player getWhosPlaying(int gameId) {
		return gameInfoList.values().stream()
				.filter(g -> {
					return g.game().id() == gameId;
				}).findAny().get().whosPlaying();
	}
}
