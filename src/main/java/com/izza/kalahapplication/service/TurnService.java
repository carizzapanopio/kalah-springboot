package com.izza.kalahapplication.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izza.kalahapplication.model.Game;
import com.izza.kalahapplication.model.GameInfo;
import com.izza.kalahapplication.model.House;
import com.izza.kalahapplication.model.Player;
import com.izza.kalahapplication.model.Turn;
import com.izza.kalahapplication.model.TurnSet;

@Component
public class TurnService {
	

    AtomicInteger turnId = new AtomicInteger(0);

    private static Map<Integer, Turn> turnList = new ConcurrentHashMap<>();
    

	@Autowired
	private GameInfoService gameInfoService;
    
	public void initializeTurn(Game game, Player player) {
		
    	Map <Integer, House> houseValues = new HashMap<>();
    	for (int i = 1; i <= 6; i++) {
    		houseValues.put(i, new House(i, 4));
    	}
    	int zoneValue = 0;
    	
    	Turn turn = new Turn(
    			turnId.incrementAndGet(),
    			game,
    			houseValues.values(),
    			zoneValue,
    			player
    			);
    	
    	turnList.put(turnId.get(), turn);
	}
	
	public Turn getTurn(int playerId, int gameId) {
		return turnList.values().stream()
		.filter(turn -> {
			return turn.game().id() == gameId && turn.player().id() == playerId;
		}).findAny().get();
	}
	
	
	public TurnSet play(int playerId, int gameId, int houseId) {
		
		if (gameInfoService.getGameInfo(playerId).isPresent()) {

			GameInfo gameInfo = gameInfoService.getGameInfo(playerId).get();
			Player playerOne = gameInfo.playerOne();
			Player playerTwo = gameInfo.playerTwo();
			
			Turn playerOneTurn = this.getTurn(playerOne.id(), gameId);
			Turn playerTwoTurn = this.getTurn(playerTwo.id(), gameId);
			
			Player whosPlaying = gameInfoService.getWhosPlaying(gameId);
			
			Turn currentPlayerTurn = playerOne.id() == playerId ? playerOneTurn : playerTwoTurn;

		}
		
		return new TurnSet(null, null);
		
		
	}
}