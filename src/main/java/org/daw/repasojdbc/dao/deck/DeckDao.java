package org.daw.repasojdbc.dao.deck;

import org.daw.repasojdbc.model.Deck;

import java.util.List;

public interface DeckDao {

    Deck addDeck(Deck newDeck);
    Deck findById(String id);
    List<Deck> findAll();
    Deck updateDeck(Deck deck);
}
