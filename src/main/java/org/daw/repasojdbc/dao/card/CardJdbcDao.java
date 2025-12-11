package org.daw.repasojdbc.dao.card;

import org.daw.repasojdbc.model.Card;
import org.daw.repasojdbc.util.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CardJdbcDao implements CardDao{
    public static Connection connection;

    static {
        try {
            connection = JdbcConnector.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Card addCard(Card newCard) {
        String sql = "INSERT INTO cards (code,image,value,suit,deck_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1,newCard.getCode());
            pst.setString(2,newCard.getImage());
            pst.setString(3,newCard.getValue());
            pst.setString(4,newCard.getSuit());
            pst.setString(5,newCard.getDeck().getId());
            int affected = pst.executeUpdate();
            if (affected == 0) {
                throw new SQLException("No se insertó ninguna fila.");
            }
            return newCard;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
