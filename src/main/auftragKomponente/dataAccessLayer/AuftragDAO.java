package main.auftragKomponente.dataAccessLayer;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import main.util.HibernateUtil;

public class AuftragDAO {

	public <T> Long saveAuftrag(Class<T> cls) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long genID = null;
		try {
			transaction = session.beginTransaction();
			Object obj = new Object();
			try {
				cls.cast(obj);
			} catch (ClassCastException e) {
				System.err.println("Cast failed! " + e);
			}
			genID = (Long) session.save(obj);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return genID;
	}
}
