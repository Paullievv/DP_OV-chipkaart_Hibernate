package dp.assignments.ovchip.database;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;

public class HibernateUtil {
        private static final SessionFactory sessionFactory;

        static {
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }

        public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }

        public static void shutdown() {
            getSessionFactory().close();
        }

        public static boolean executeTransaction(Consumer<Session> operation, Session session) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                operation.accept(session);
                transaction.commit();
                return true;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println("Error during transaction: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
}
