<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un Livre</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container">
    <h1 class="my-4">Ajouter un Livre</h1>
    
    <form action="LivreManager" method="post" class="row g-3">
        <div class="col-md-6">
            <label for="isbn" class="form-label">ISBN</label>
            <input type="text" name="isbn" id="isbn" class="form-control" required>
        </div>
        <div class="col-md-6">
            <label for="titre" class="form-label">Titre</label>
            <input type="text" name="titre" id="titre" class="form-control" required>
        </div>
        <div class="col-md-6">
            <label for="auteur" class="form-label">Auteur</label>
            <input type="text" name="auteur" id="auteur" class="form-control" required>
        </div>
        <div class="col-md-3">
            <label for="prix" class="form-label">Prix</label>
            <input type="number" step="0.01" name="prix" id="prix" class="form-control" required>
        </div>
        <div class="col-md-3">
            <label for="annee_publication" class="form-label">Année de publication</label>
            <input type="number" name="annee_publication" id="annee_publication" class="form-control" required>
        </div>
        <div class="col-12">
            <button type="submit" class="btn btn-success">Ajouter</button>
            <a href="LivreManager?action=list" class="btn btn-secondary">Retour à la liste</a>
        </div>
    </form>
</body>
</html>
    