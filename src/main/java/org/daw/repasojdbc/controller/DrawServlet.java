package org.daw.repasojdbc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.daw.repasojdbc.model.Card;
import org.daw.repasojdbc.model.Deck;
import org.daw.repasojdbc.service.DeckService;
import org.daw.repasojdbc.service.DeckServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "drawServlet", value = "/draw")
public class DrawServlet extends HttpServlet {
    private DeckService service;

    @Override
    public void init() throws ServletException {
        service = new DeckServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String deckId = request.getParameter("deckId");
        int numberOfCards = Integer.parseInt(request.getParameter("numberOfCards"));
        Deck deck = service.findById(deckId);
        try {
            service.drawnCards(numberOfCards,deck);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("deck", deck);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
