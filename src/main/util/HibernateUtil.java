package main.util;

import main.auftragKomponente.dataAccessLayer.Auftrag;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	private static Configuration getInitializedConfiguration() {
		Configuration configuration = new Configuration();
		configuration.configure("/hibernate.cfg.xml");

		// Begin configuration
		// Properties
		configuration.setProperty("connection.url", "jdbc:mysql://localhost:3000/test");
		configuration.setProperty("connection.username", "root");
		configuration.setProperty("connection.password", "temp4U!");
		configuration.setProperty("connection.driver_class", "com.mysql.jdbc.Driver");
		configuration.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
		configuration.setProperty("show_sql", "true");
		configuration.setProperty("hibernate.connection.pool_size", "1");

		// AuftragKomponente
		configuration.addAnnotatedClass(Auftrag.class);

		// End configuration
		
//		<debug>
//		configuration.buildMappings();
//		Iterator<PersistentClass> it = configuration.getClassMappings();
//		while (it.hasNext()) {
//			PersistentClass e = it.next();
//			System.out.println(e);
//			System.out.println(e.getTable());
//		}
//		</debug>
		
		return configuration;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = getInitializedConfiguration();
				serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				sessionFactory = configuration
						.buildSessionFactory(serviceRegistry);
			} catch (Throwable ex) {
				System.err.println("Initital SessionFactory creation failed: "
						+ ex);
			}
		}
		return sessionFactory;
	}
}
