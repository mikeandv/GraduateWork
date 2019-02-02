package dbhandler;

import entity.CookieSession;
import entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class DBSessionFactory {
        private static SessionFactory sessionFactory;
        private static StandardServiceRegistry standardServiceRegistry;


    static{
        if (sessionFactory == null) {
            try {
                // Create StandardServiceRegistry
                standardServiceRegistry = new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
                // Create MetadataSources
                MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
                // Create Metadata
                Metadata metadata = metadataSources.getMetadataBuilder().build();
                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (standardServiceRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
                }
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

//        private DBSessionFactory() {}
//
//        public static SessionFactory getSessionFactory() {
//            if (sessionFactory == null) {
//                try {
//
//
//                    Configuration configuration = new Configuration();
//                    Properties p = new Properties();
//                    p.load(DBSessionFactory.class.getClassLoader().getResourceAsStream("application.properties"));
//                    configuration.setProperties(p);
//
//                    configuration.addAnnotatedClass(User.class);
//                    configuration.addAnnotatedClass(CookieSession.class);
//
//                    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//                    sessionFactory = configuration.buildSessionFactory(builder.build());
//
//                } catch (Exception e) {
//                    System.out.println("Ошибка: " + e);
//                }
//            }
//            return sessionFactory;
//        }
//
//        public void initConn() {
//
//    }

