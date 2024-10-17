package com.example.jndi_biblio2;

public class Livre {
    private String isbn;
    private String titre;
    private String auteur;
    private double prix;
    private int anneePublication;

    public Livre(String isbn, String titre, String auteur, double prix, int anneePublication) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.prix = prix;
        this.anneePublication = anneePublication;
    }

    // Getters and setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getAnneePublication() { return anneePublication; }
    public void setAnneePublication(int anneePublication) { this.anneePublication = anneePublication; }
}

