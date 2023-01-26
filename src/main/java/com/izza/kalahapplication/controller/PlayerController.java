package com.izza.kalahapplication.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.graphql.data.method.annotation.MutationMapping;

import org.springframework.stereotype.Controller;

import com.izza.kalahapplication.model.Player;
import com.izza.kalahapplication.service.PlayerService;

@Controller
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@MutationMapping
	public Player addNewPlayer() {
		return playerService.addNewPlayer();
		
	}
}
