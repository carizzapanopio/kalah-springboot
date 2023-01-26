package com.izza.kalahapplication.controller;

import java.util.Collection;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.izza.kalahapplication.model.Game;
import com.izza.kalahapplication.model.GameInfo;
import com.izza.kalahapplication.service.GameService;

@Controller
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@QueryMapping
	public GameInfo getCurrentGame(@Argument int playerId) {
		 return gameService.getCurrentGame(playerId);
	}
	
	@QueryMapping
	public Game getGameById(@Argument int gameId) {
		return gameService.getGameById(gameId);
	}
	

	@SubscriptionMapping
	public Publisher<Game> getPlayerOngoingGame() {
		return gameService.getPlayerOngoingGame();
	}
	
	@QueryMapping
	public Collection<Game> getGames() {
		return gameService.getGames();
	}
}

