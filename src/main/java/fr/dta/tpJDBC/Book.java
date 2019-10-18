package fr.dta.tpJDBC;

public class Book {
	
	private String titre;
	private String author;
	private Long id;
	
	public Book(String titre, String author) {
		super();
		this.titre = titre;
		this.author = author;
	}

	public String getTitre() {
		return titre;
	}

	public String getAuthor() {
		return author;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Book [titre=" + titre + ", author=" + author + ", id=" + id + "]";
	}
	

}
