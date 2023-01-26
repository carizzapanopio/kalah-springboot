package com.izza.kalahapplication.model;

import java.util.Collection;

public record Turn(
		int id,
		Game game,
		Collection<House> houseValues,
		int zoneValue,
		Player player
		) {
}
