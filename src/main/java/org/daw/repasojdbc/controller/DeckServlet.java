package org.daw.repasojdbc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.daw.repasojdbc.model.Deck;
import org.daw.repasojdbc.service.DeckService;
import org.daw.repasojdbc.service.DeckServiceImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@WebServlet(name = "deckServlet", value = "/deck")
public class DeckServlet extends HttpServlet {
    private DeckService service;

    @Override
    public void init() throws ServletException {
        this.service = new DeckServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Deck> decks = service.findAll();

        request.setAttribute("decks", decks);

        request.getRequestDispatcher("decks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deckId = request.getParameter("deckId");
        String numberOfDecks = request.getParameter("numberOfDecks");

        if (numberOfDecks != null) {
            Deck deck;
            try {
                deck = service.crearDeck(Integer.parseInt(numberOfDecks));
                request.setAttribute("deck", deck);
            } catch (Exception e) {
                throw new ServletException("Error fetching deck: " + e.getMessage(), e);
            }
        }

        response.sendRedirect("deck");

    }
}
