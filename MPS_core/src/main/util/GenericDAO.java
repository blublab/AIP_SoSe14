package main.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GenericDAO<T> implements IGenericDao<T> {
	private final Class<T> type;

	public GenericDAO(Class<T> type) {
		assert type != null;
		this.type = type;
	}

	@Override
	public void create(T newInstance) {
		assert newInstance != null;
		Session session = startSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(newInstance);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T read(int id) {
		assert id > 0;
		Session session = startSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Object o = session.get(type, id);
			tx.commit();
			assert o != null;
			return (T) o;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
            e.printStackTrace();
		} finally {
			session.close();
		}
        return null;
	}

	@Override
	public T update(T transientObject) {
		assert transientObject != null;
		Session session = startSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.merge(transientObject);
			tx.commit();
			return transientObject;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
            e.printStackTrace();
		} finally {
			session.close();
		}
        return null;
    }

	@Override
	public void delete(T persistentObject) {
		assert persistentObject != null;
		Session session = startSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(persistentObject);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
            e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private Session startSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> readAll() {
		Session session = startSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("From " + type.getName());
			List<T> o = query.list();
			tx.commit();
			assert o != null;
			return o;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
            e.printStackTrace();
		} finally {
			session.close();
		}
        return null;
	}
}
