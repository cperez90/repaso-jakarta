package org.daw.repasojdbc.service;

import org.daw.repasojdbc.dto.DeckDto;
import org.daw.repasojdbc.model.Card;
import org.daw.repasojdbc.model.Deck;

import java.util.List;

public interface DeckService {
    Deck crearDeck(int count) throws Exception;
    List<Card> drawnCards(int count ,Deck deck) throws Exception;
    List<Deck> findAll();
    Deck findById(String id);
}
