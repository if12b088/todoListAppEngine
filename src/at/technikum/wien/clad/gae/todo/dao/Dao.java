package at.technikum.wien.clad.gae.todo.dao;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import at.technikum.wien.clad.gae.todo.model.Todo;

public enum Dao {
	INSTANCE;

	public void add(String email, String summary, String description,
			String url, Integer importance) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Todo todo = new Todo(email, summary, description, url, importance);
			em.persist(todo);
			em.close();
		}
	}

	public Set<String> getAllUsers() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t.author from Todo t group by t.author");
		Set<String> list = new HashSet<String>();
		for (Object author : q.getResultList()) {
			list.add((String) author);
		}
		em.close();
		return list;
	}

	public List<Todo> getOtherTodos(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Todo t where t.author <> :userId and t.finished = false");
		q.setParameter("userId", userId);
		return q.getResultList();
	}

	public List<Todo> getTodosByEmail(String email) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Todo t where t.author = :email and t.finished = false");
		q.setParameter("email", email);
		List<Todo> todos = (List<Todo>) q.getResultList();
		em.close();
		return todos;
	}

	public List<Todo> getFinishedTodosByEmail(String email) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Todo t where t.author = :email and t.finished = true");
		q.setParameter("email", email);
		List<Todo> todos = (List<Todo>) q.getResultList();
		em.close();
		// List<Todo> sortedList = new ArrayList<Todo>();
		// for (Todo todo : todos) {
		//
		// }
		Collections.sort(todos);
		return todos;
	}

	public void finish(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Todo todo = em.find(Todo.class, id);
			todo.setFinishDate(new Date());
			todo.setFinished(true);
			em.persist(todo);
		} finally {
			em.close();
		}
	}

	public void remove(long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Todo todo = em.find(Todo.class, id);
			em.remove(todo);
		} finally {
			em.close();
		}
	}

	public void updateAuthor(long id, String user) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Todo todo = em.find(Todo.class, id);
			todo.setAuthor(user);
			em.merge(todo);
		} finally {
			em.close();
		}

	}
}