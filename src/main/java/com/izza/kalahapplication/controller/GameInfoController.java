package com.izza.kalahapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.izza.kalahapplication.model.Player;
import com.izza.kalahapplication.service.GameInfoService;

@Controller
public class GameInfoController {

	@Autowired
	private GameInfoService gameInfoService;
	

	@QueryMapping
	public Player getWhosPlaying(@Argument int gameId ) {
		return gameInfoService.getWhosPlaying(gameId);
	}
	
	
}
