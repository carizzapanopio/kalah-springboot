package com.izza.kalahapplication.service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.reactivestreams.Publisher;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izza.kalahapplication.model.Game;
import com.izza.kalahapplication.model.GameInfo;
import com.izza.kalahapplication.model.Player;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class GameService {
	
	private static Map<Integer, Game> gameData = new ConcurrentHashMap<>();
	AtomicInteger gameId = new AtomicInteger(0);
	
	@Autowired
	private PlayerService playerService;
	


	@Autowired
	private GameInfoService gameInfoService;
	

	@Autowired
	private TurnService turnService;
	

    @Autowired
    private Sinks.Many<Game> gameSink;

    @Autowired
    private Flux<Game> gameFlux;
	
	
    public Game getGameById(int gameId) {
    	System.out.println("getgamebyid"+ gameData.get(gameId));
    	return gameData.get(gameId);
    }
    
    public Collection<Game> getGames() {
    	return gameData.values();
    }
    
	public GameInfo getCurrentGame(int playerId) {
		Game game;
		
		GameInfo gameInfo;
		Player player = playerService.getPlayerById(playerId);
		if (gameInfoService.getGameInfo(playerId).isPresent()) {
			gameInfo = gameInfoService.getGameInfo(playerId).get();
			System.out.println("Player has a current game: " + gameInfo.game() );
			
		} else {
			Optional<Game> waitingGame = gameData.values()
			.stream()
			.filter(g -> g.status() == "waiting")
			.findFirst();
			
			if (waitingGame.isPresent()) {

				game = new Game(waitingGame.get().id(), "ongoing");
				System.out.println("Setting player 2 for game " + game );
				gameInfo = gameInfoService.setGamePlayer(player, game);
				
				gameSink.tryEmitNext(game); //will trigger subscription
			} else {
				game = new Game(gameId.incrementAndGet(), "waiting");

				System.out.println("Setting player 1 for game " + game );
				gameInfo = gameInfoService.setGamePlayer(player, game);
			}

			turnService.initializeTurn(game, player);
			
			gameData.put(game.id(), game);
		}

		System.out.print(gameInfo);
		return gameInfo;	
	}


    public Publisher<Game> getPlayerOngoingGame() {
        return gameFlux.map(g -> prompt(g));
    }

    public Game prompt(Game g) {
        System.out.println(g.id() + " is now ongoing");
        return g;
    }
	
	
}
