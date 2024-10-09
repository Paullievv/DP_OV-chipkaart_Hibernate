package dp.assignments.ovchip.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static <T> T executeTransaction(SessionConsumer<T> consumer, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T result = consumer.accept(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @FunctionalInterface
    public interface SessionConsumer<T> {
        T accept(Session session);
    }

    public static void shutdown() {
        getSession().close();
    }
}
