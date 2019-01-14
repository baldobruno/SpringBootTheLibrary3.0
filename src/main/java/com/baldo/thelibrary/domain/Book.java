package com.baldo.thelibrary.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

@Entity
public class Book extends AbstractDomainClass {

	private String isbn;
	private String title;
	private BigDecimal price;
	@ManyToMany
	@JoinTable
	private List<Author> authors = new ArrayList<>();
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Publisher publisher;
	@ElementCollection(fetch = FetchType.EAGER, targetClass = BookCategory.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	@OrderBy("category ASC")
	private SortedSet<BookCategory> categories = new TreeSet<>();

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		if (authors != null) {
			this.authors = authors;
		}
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public BookCategory[] getCategories() {
		return categories.toArray(new BookCategory[0]);
	}

	public void setCategories(BookCategory[] categories) {
		this.categories.clear();
		for (BookCategory bookCategory : categories) {
			this.categories.add(bookCategory);
		}
	}

	public void addAuthor(Author author) {
		this.authors.add(author);
	}

	public boolean hasAuthor(Author author) {
		return this.authors.contains(author);
	}

	public void addCategory(BookCategory category) {
		this.categories.add(category);
	}

}
