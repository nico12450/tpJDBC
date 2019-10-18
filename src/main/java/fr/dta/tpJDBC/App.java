package fr.dta.tpJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class App 
{
	
	
	private static String url = "jdbc:postgresql://localhost:5432/tpJDBC";
	
    public static void main( String[] args )
    {
    	
    	try(Connection conn = DriverManager.getConnection(url, "postgres", "nicomdp"); Statement stmt = conn.createStatement()){
    		
    		stmt.executeUpdate("drop table if exists achat");
    		stmt.executeUpdate("drop table if exists client");
    		stmt.executeUpdate("drop table if exists book");
    		
    		stmt.executeUpdate("create table book (id bigserial primary key, title varchar(255) not null, author varchar(255))");
    		stmt.executeUpdate("create table client (id bigserial primary key, lastname varchar(255) not null, firstname varchar(255), gender varchar(20), id_livre_pref bigint, foreign key(id_livre_pref) references book(id))");   		
    		stmt.executeUpdate("create table achat (id_client bigint, id_book bigint, foreign key(id_client) references client(id), foreign key(id_book) references book(id))");
    	
    	}
    	
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    	
    	Book b1 = new Book("livre test", "auteur test");
    	insererBook(b1);
    	
    	Client c1 = new Client("Gamel","Nicolas",Genre.Male,b1);
    	
    	insererClient(c1);
    	
    	clientAcheteLivre(c1, b1);
    	
    	ArrayList<String> l = getClientByBook(b1.getTitre());
    	System.out.println(l);
    	
    	ArrayList<String> l2 = getBookByCLient(c1.getNom());
    	System.out.println(l2);
    		
    	
    }
    
    public static void insererBook(Book b){
		
		try(Connection conn = DriverManager.getConnection(url, "postgres", "nicomdp"); 
				PreparedStatement stmt = conn.prepareStatement("insert into book(title,author) values(?,?)", Statement.RETURN_GENERATED_KEYS)){
    		
    		stmt.setString(1, b.getTitre());
    		stmt.setString(2, b.getAuthor());
    		stmt.executeUpdate();
    		ResultSet GK = stmt.getGeneratedKeys();
    		GK.next();
    		b.setId(GK.getLong("id"));

    	}
    	
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
		
	}
    
	public static void insererClient(Client c){
		
		if(c.getLivrePref()!=null) {
			
			try(Connection conn = DriverManager.getConnection(url, "postgres", "nicomdp");
					PreparedStatement stmt = conn.prepareStatement("insert into client(lastname,firstname,gender,id_livre_pref) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
	    		
	    		stmt.setString(1, c.getNom());
	    		stmt.setString(2, c.getPrenom());
	    		stmt.setString(3, c.getGenre().toString());
	    		stmt.setLong(4, c.getLivrePref().getId());
	    		stmt.executeUpdate();
	    		ResultSet GK = stmt.getGeneratedKeys();
	    		GK.next();
	    		c.setId(GK.getLong("id"));

	    	}
	    	
	    	catch(Exception e) {
	    		System.out.println(e.getMessage());
	    	}
			
		}
		
		else {
			
			try(Connection conn = DriverManager.getConnection(url, "postgres", "nicomdp");
					PreparedStatement stmt = conn.prepareStatement("insert into client(lastname,firstname,gender) values(?,?,?)", Statement.RETURN_GENERATED_KEYS)){
	    		
	    		stmt.setString(1, c.getNom());
	    		stmt.setString(2, c.getPrenom());
	    		stmt.setString(3, c.getGenre().toString());
	    		stmt.executeUpdate();
	    		ResultSet GK = stmt.getGeneratedKeys();
	    		GK.next();
	    		c.setId(GK.getLong("id"));

	    	}
	    	
	    	catch(Exception e) {
	    		System.out.println(e.getMessage());
	    	}
			
		}
		
	}

	public static ArrayList<String> getClientByBook(String titre) {
		
		ResultSet r = null;
		ArrayList<String> l = new ArrayList<String>();
		
		try(Connection conn = DriverManager.getConnection(url, "postgres", "nicomdp"); PreparedStatement stmt = conn.prepareStatement("select lastname from client join achat on achat.id_client = client.id join book on achat.id_book = book.id where book.title = ?")){

			stmt.setString(1, titre);
			r = stmt.executeQuery();		
			
			while(r.next()) {
				
				String nomClient = r.getString("lastname");
				l.add(nomClient);
				
				
			}
			
    	}
    	
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
		
		return l;
		
	}
	
	public static ArrayList<String> getBookByCLient(String nom) {
		
		ResultSet r = null;
		ArrayList<String> l = new ArrayList<String>();
		
		try(Connection conn = DriverManager.getConnection(url, "postgres", "nicomdp"); PreparedStatement stmt = conn.prepareStatement("select title from client join achat on achat.id_client = client.id join book on achat.id_book = book.id where client.lastname = ?")){

			stmt.setString(1, nom);
			r = stmt.executeQuery();		
			
			while(r.next()) {
				
				String nomClient = r.getString("title");
				l.add(nomClient);
				
				
			}
			
    	}
    	
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
		
		return l;
		
	}
	
	public static void clientAcheteLivre(Client c, Book b) {
		
		try(Connection conn = DriverManager.getConnection(url, "postgres", "nicomdp"); PreparedStatement stmt = conn.prepareStatement("insert into achat(id_client,id_book) values(?,?)")){
    		
			stmt.setLong(1, c.getId());
			stmt.setLong(2, b.getId());
			stmt.executeUpdate();

    	}
    	
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
		
	}
    
}
