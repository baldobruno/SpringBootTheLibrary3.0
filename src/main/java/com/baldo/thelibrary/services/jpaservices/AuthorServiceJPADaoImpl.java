package com.baldo.thelibrary.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.baldo.thelibrary.domain.Author;
import com.baldo.thelibrary.services.AuthorService;

@Service
public class AuthorServiceJPADaoImpl extends AbstractJpaDaoService implements AuthorService {

	@Override
	public List<Author> listAll() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("from Author", Author.class).getResultList();
	}

	@Override
	public Author getById(Integer id) {
		EntityManager em = emf.createEntityManager();

		return em.find(Author.class, id);

	}

	@Override
	public Author saveOrUpdate(Author author) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Author savedAuthor = em.merge(author);
		em.getTransaction().commit();

		return savedAuthor;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.remove(em.find(Author.class, id));
		em.getTransaction().commit();

	}

}
