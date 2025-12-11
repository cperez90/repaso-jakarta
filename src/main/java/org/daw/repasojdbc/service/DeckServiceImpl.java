package org.daw.repasojdbc.service;

import com.google.gson.JsonObject;
import org.daw.repasojdbc.dao.card.CardDao;
import org.daw.repasojdbc.dao.card.CardJdbcDao;
import org.daw.repasojdbc.dao.deck.DeckDao;
import org.daw.repasojdbc.dao.deck.DeckJdbcDao;
import org.daw.repasojdbc.model.Card;
import org.daw.repasojdbc.model.Deck;
import org.daw.repasojdbc.util.ApiProvider;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DeckServiceImpl implements DeckService{

    private static final URI BASE_URL = URI.create("https://www.deckofcardsapi.com/api/deck/");
    DeckDao deckDao = new DeckJdbcDao();
    CardDao cardDao = new CardJdbcDao();

    @Override
    public Deck crearDeck(int count) throws Exception {
        JsonObject json = ApiProvider.parseToJson(ApiProvider.requestApi(BASE_URL+"new/shuffle/?deck_count="+count));
        Deck deck = new Deck();
        deck.setId(json.get("deck_id").getAsString());
        deck.setRemaining(json.get("remaining").getAsInt());

        deckDao.addDeck(deck);
        return deckDao.findById(deck.getId());
    }

    @Override
    public List<Card> drawnCards(int count, Deck deck) throws Exception {
        JsonObject json = ApiProvider.parseToJson(ApiProvider.requestApi(BASE_URL+deck.getId()+"/draw/?count="+count));
        List<JsonObject> list = ApiProvider.getListJsonObjects(json,"cards");
        List<Card> cards = new ArrayList<>();
        for (JsonObject c : list){
            Card card = new Card();

            card.setCode(c.get("code").getAsString());
            card.setImage(c.get("image").getAsString());
            card.setValue(c.get("value").getAsString());
            card.setSuit(c.get("suit").getAsString());
            card.setDeck(deck);
            cards.add(card);
            cardDao.addCard(card);
        }
        deck.setRemaining(json.get("remaining").getAsInt());
        deck.setDrawnCards(cards);
        deckDao.updateDeck(deck);
        return cards;
    }

    @Override
    public List<Deck> findAll() {
        return deckDao.findAll();
    }

    @Override
    public Deck findById(String id) {
        return deckDao.findById(id);
    }
}
