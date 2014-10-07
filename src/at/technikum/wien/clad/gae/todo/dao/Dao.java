package at.technikum.wien.clad.gae.todo.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import at.technikum.wien.clad.gae.todo.model.Todo;
import at.technikum.wien.clad.gae.todo.model.User;

public enum Dao {
	INSTANCE;

//	public List<Todo> listTodos() {
//		EntityManager em = EMFService.get().createEntityManager();
//		// read the existing entries
//		Query q = em
//				.createQuery("select m from Todo m where m.finished = flase");
//		List<Todo> todos = q.getResultList();
//		return todos;
//	}

	public void add(String userId, String summary, String description,
			String url, Integer importance) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Todo todo = new Todo(userId, summary, description, url, importance);
			em.persist(todo);
			em.close();
		}
	}

	public void addUser(String name, String email, String password) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			User user = new User(name, email, password);
			em.persist(user);
			em.close();
		}
	}
	
	public List<Todo> getOtherTodos(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Todo t where t.author <> :userId and t.finished = false");
		q.setParameter("userId", userId);
		List<Todo> todos = q.getResultList();
		return todos;
	}
	
	public List<Todo> getTodosByUser(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Todo t where t.author = :userId and t.finished = false");
		q.setParameter("userId", userId);
		List<Todo> todos = (List<Todo>) q.getResultList();
		em.close();
		return todos;
	}
	
	public List<Todo> getFinishedTodosByUser(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em
				.createQuery("select t from Todo t where t.author = :userId and t.finished = true order by importance");
		q.setParameter("userId", userId);
		List<Todo> todos = (List<Todo>) q.getResultList();
		em.close();
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
}