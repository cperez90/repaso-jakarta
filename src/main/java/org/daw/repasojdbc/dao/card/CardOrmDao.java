package org.daw.repasojdbc.dao.card;

import jakarta.persistence.EntityManager;
import org.daw.repasojdbc.model.Card;
import org.daw.repasojdbc.util.ConnectionManager;

public class CardOrmDao implements CardDao{
    @Override
    public Card addCard(Card newCard) {
        EntityManager em = ConnectionManager.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(newCard);
            em.getTransaction().commit();
            return newCard;
        }catch (Exception e){
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al agregar la carta");
        }finally {
            em.close();
        }
    }
}
