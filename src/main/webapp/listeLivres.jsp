<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Livres</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container">
    <h1 class="my-4">Gestion des Livres</h1>
    <a href="LivreManager?action=showForm" class="btn btn-primary mb-3">Ajouter un livre</a>
    
    <table class="table table-bordered">
        <thead class="thead-dark">
            <tr>
                <th>ISBN</th>
                <th>Titre</th>
                <th>Auteur</th>
                <th>Prix</th>
                <th>Année de publication</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="livre" items="${livres}">
                <tr>
                    <td>${livre.isbn}</td>
                    <td>${livre.titre}</td>
                    <td>${livre.auteur}</td>
                    <td>${livre.prix}</td>
                    <td>${livre.annee_publication}</td>
                    <td>
                        <a href="LivreManager?action=delete&isbn=${livre.isbn}" class="btn btn-danger">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
>