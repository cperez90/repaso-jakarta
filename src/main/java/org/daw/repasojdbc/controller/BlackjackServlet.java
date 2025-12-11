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

@WebServlet(name = "blackjackServlet", value = "/blackjack")
public class BlackjackServlet extends HttpServlet {

    private DeckService service;

    @Override
    public void init() throws ServletException {
        this.service = new DeckServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        System.out.println(id);

        Deck deck = service.findById(id);

        request.setAttribute("deck", deck);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
