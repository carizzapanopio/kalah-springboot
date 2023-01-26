package com.izza.kalahapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.izza.kalahapplication.model.Turn;
import com.izza.kalahapplication.model.TurnSet;
import com.izza.kalahapplication.service.TurnService;

@Controller
public class TurnController {

	@Autowired
	private TurnService turnService;
	
	@QueryMapping
	public Turn getTurn(@Argument int playerId, @Argument int gameId ) {
		return turnService.getTurn(playerId, gameId);
	}

	@MutationMapping
	public TurnSet play(@Argument int playerId, @Argument int gameId, @Argument int houseId ) {
		return turnService.play(playerId, gameId, houseId);
	}
}
