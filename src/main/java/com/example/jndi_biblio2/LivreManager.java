package com.example.jndi_biblio2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LivreManager", urlPatterns = {"/LivreManager"})
public class LivreManager extends HttpServlet {
    private DataSource dataSource;

    public void init() throws ServletException {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/gestionLivresDB");
        } catch (NamingException e) {
            throw new ServletException("Cannot find DataSource", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                listLivres(request, response);
            } else if (action.equals("showForm")) {
                showForm(request, response);
            } else if (action.equals("delete")) {
                deleteLivre(request, response);
            } else if (action.equals("list")) {
                listLivres(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            addLivre(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    private void addLivre(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String isbn = request.getParameter("isbn");
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        double prix = Double.parseDouble(request.getParameter("prix"));
        int anneePublication = Integer.parseInt(request.getParameter("annee_publication"));

        String sql = "INSERT INTO livres (isbn, titre, auteur, prix, annee_publication) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            stmt.setString(2, titre);
            stmt.setString(3, auteur);
            stmt.setDouble(4, prix);
            stmt.setInt(5, anneePublication);
            stmt.executeUpdate();
        }

        response.sendRedirect(request.getContextPath() + "/LivreManager?action=list");
    }

    private void listLivres(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Livre livre = new Livre(
                        rs.getString("isbn"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getDouble("prix"),
                        rs.getInt("annee_publication")
                );
                livres.add(livre);
            }
        }
        request.setAttribute("livres", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listlivre.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteLivre(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String isbn = request.getParameter("isbn");
        String sql = "DELETE FROM livres WHERE isbn = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            stmt.executeUpdate();
        }
        response.sendRedirect("LivreManager?action=list");
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("ajouterLivre.jsp");
        dispatcher.forward(request, response);
    }
}
