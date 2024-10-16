package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.naming.InitialContext;
import jakarta.naming.NamingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.sql.DataSource;


@WebServlet("/LivreManager")
public class LivreManager extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Récupération de la DataSource via JNDI
    private Connection getDataSourceConnection() throws SQLException {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/gestionLivresDB");
            return ds.getConnection();
        } catch (NamingException e) {
            throw new SQLException("Unable to locate DataSource", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try (Connection conn = getDataSourceConnection()) {
            if ("list".equals(action)) {
                listLivres(conn, request, response);
            } else if ("delete".equals(action)) {
                deleteLivre(conn, request, response);
            } else {
                showForm(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = getDataSourceConnection()) {
            addLivre(conn, request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour ajouter un livre à la base de données
    private void addLivre(Connection conn, HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String isbn = request.getParameter("isbn");
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        double prix = Double.parseDouble(request.getParameter("prix"));
        int annee = Integer.parseInt(request.getParameter("annee_publication"));

        String sql = "INSERT INTO livre (isbn, titre, auteur, prix, annee_publication) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.setString(2, titre);
            ps.setString(3, auteur);
            ps.setDouble(4, prix);
            ps.setInt(5, annee);
            ps.executeUpdate();
        }
        response.sendRedirect("LivreManager?action=list");
    }

    // Méthode pour lister les livres
    private void listLivres(Connection conn, HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String sql = "SELECT * FROM livre";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            request.setAttribute("livres", rs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/listLivres.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Méthode pour supprimer un livre
    private void deleteLivre(Connection conn, HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String isbn = request.getParameter("isbn");
        String sql = "DELETE FROM livre WHERE isbn = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.executeUpdate();
        }
        response.sendRedirect("LivreManager?action=list");
    }

    // Méthode pour afficher le formulaire de gestion des livres
    private void showForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/livreForm.jsp");
        dispatcher.forward(request, response);
    }
}
