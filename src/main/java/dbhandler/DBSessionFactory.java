package dbhandler;

import entity.CookieSession;
import entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class DBSessionFactory {
        private static SessionFactory sessionFactory;

        private DBSessionFactory() {}

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
                    Configuration configuration = new Configuration();
                    Properties p = new Properties();
                    p.load(DBSessionFactory.class.getClassLoader().getResourceAsStream("application.properties"));
                    configuration.setProperties(p);

                    configuration.addAnnotatedClass(User.class);
                    configuration.addAnnotatedClass(CookieSession.class);

                    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                    sessionFactory = configuration.buildSessionFactory(builder.build());

                } catch (Exception e) {
                    System.out.println("Ошибка: " + e);
                }
            }
            return sessionFactory;
        }

        public void initConn() {

    }
}
