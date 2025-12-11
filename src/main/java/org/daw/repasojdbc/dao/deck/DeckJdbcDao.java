package org.daw.repasojdbc.dao.deck;

import org.daw.repasojdbc.model.Deck;
import org.daw.repasojdbc.util.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeckJdbcDao implements DeckDao{
    public static Connection connection;

    static {
        try {
            connection = JdbcConnector.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Deck addDeck(Deck newDeck) {
        String sql = "INSERT INTO decks (id, remaining) VALUES (?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1,newDeck.getId());
            pst.setInt(2,newDeck.getRemaining());
            int affected = pst.executeUpdate();
            if (affected == 0) {
                throw new SQLException("No se insertó ninguna fila.");
            }
            return newDeck;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Deck findById(String id) {
        String sql = "SELECT * FROM decks WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, id);
            ResultSet result = pst.executeQuery();
            if (result.next()){
                String deckId = result.getString("id");
                int deckRemaining = result.getInt("remaining");
                Deck deck = new Deck(deckId,deckRemaining);
                return deck;
            }else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Deck> findAll() {
        List<Deck> decks = new ArrayList<>();
        String sql = "SELECT * FROM decks";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            ResultSet result = pst.executeQuery();

            while(result.next()) {
                String deckId = result.getString("id");
                int deckRemaining = result.getInt("remaining");
                Deck deck = new Deck(deckId, deckRemaining);
                decks.add(deck);
            }

            return decks;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Deck updateDeck(Deck deck) {
        String sql = "UPDATE decks SET  remaining = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)){
            pst.setInt(1, deck.getRemaining());
            pst.setString(2, deck.getId());
            int updated = pst.executeUpdate();
            if (updated == 0){
                throw new RuntimeException("No se encontro la deck con Id "+deck.getId());
            }
            return deck;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la deck",e);
        }
    }
}
