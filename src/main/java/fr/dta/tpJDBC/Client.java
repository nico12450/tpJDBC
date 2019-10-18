package fr.dta.tpJDBC;

import java.sql.Connection;
import java.sql.DriverManager;

public class Client {
	
	private String nom;
	private String prenom;
	private Genre genre;
	private Book livrePref;
	private Long id;

	public Client(String nom, String prenom, Genre genre) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.genre = genre;
	}
	
	public Client(String nom, String prenom, Genre genre, Book b) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.genre = genre;
		this.livrePref = b;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public Genre getGenre() {
		return genre;
	}
	
	public void setId(Long l) {
		this.id = l;
	}

	public long getId() {
		return id;
	}
	
	public Book getLivrePref() {
		return livrePref;
	}

	public void setLivrePref(Book b) {
		
		this.livrePref = b;
		
	}

	@Override
	public String toString() {
		return "Client [nom=" + nom + ", prenom=" + prenom + ", genre=" + genre + ", id=" + id + "]";
	}
	

}
