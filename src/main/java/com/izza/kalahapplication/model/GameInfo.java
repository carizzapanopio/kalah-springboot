package com.izza.kalahapplication.model;

public record GameInfo(int id, Game game, Player playerOne, Player playerTwo, Player whosPlaying, Player winner) {

}
