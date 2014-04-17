package main.util;

import main.auftragKomponente.dataAccessLayer.Auftrag;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	private static Configuration getInitializedConfiguration() {
		Configuration configuration = new Configuration();

		// Begin configuration

		// Properties
		// configuration.setProperty("connection.url",
		// "jdbc:mysql://localhost:3306/test");
		// configuration.setProperty("connection.username", "root");
	  	// configuration.setProperty("connection.password", "root");
		// configuration.setProperty("connection.driver_class",
		// "com.mysql.jdbc.Driver");
		// configuration.setProperty("dialect",
		// "org.hibernate.dialect.MySQLDialect");
		// configuration.setProperty("show_sql", "true");
		// configuration.setProperty("hibernate.connection.pool_size", "1");

		// AuftragKomponente
		configuration.addAnnotatedClass(Auftrag.class);

		// End configuration

		// <debug>
//		configuration.buildMappings();
//		Iterator<PersistentClass> it = configuration.getClassMappings();
//		while (it.hasNext()) {
//			PersistentClass e = it.next();
//			System.out.println(e);
//			System.out.println(e.getTable());
//		}
		// </debug>

		configuration.configure("hibernate.cfg.xml");
		return configuration;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration cfg = getInitializedConfiguration();

				StandardServiceRegistryBuilder sSRB = new StandardServiceRegistryBuilder();
				sSRB.applySettings(cfg.getProperties());
				StandardServiceRegistry sSR = sSRB.build();

				sessionFactory = cfg.buildSessionFactory(sSR);
			} catch (Throwable ex) {
				System.err.println("Initital SessionFactory creation failed: "
						+ ex);
			}
		}
		return sessionFactory;
	}
}
