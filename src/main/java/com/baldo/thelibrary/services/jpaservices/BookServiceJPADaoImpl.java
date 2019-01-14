package com.baldo.thelibrary.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.baldo.thelibrary.domain.Book;
import com.baldo.thelibrary.services.BookService;

@Service
public class BookServiceJPADaoImpl extends AbstractJpaDaoService implements BookService {

	@Override
	public List<Book> listAll() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("from Book", Book.class).getResultList();

	}

	@Override
	public Book getById(Integer id) {
		EntityManager em = emf.createEntityManager();

		return em.find(Book.class, id);
	}

	@Override
	public Book saveOrUpdate(Book book) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Book savedBook = em.merge(book);
		em.getTransaction().commit();
		em.close();

		return savedBook;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.remove(em.find(Book.class, id));
		em.getTransaction().commit();
		em.close();
	}

}
