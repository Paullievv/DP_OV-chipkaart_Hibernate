package dp.assignments.ovchip.dao;

import dp.assignments.ovchip.database.HibernateUtil;
import dp.assignments.ovchip.domain.Reiziger;
import dp.assignments.ovchip.interfaces.IReizigerDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public class ReizigerDAO implements IReizigerDAO {

    private final Session session;

    public ReizigerDAO(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        return HibernateUtil.executeTransaction(session -> session.save(reiziger), session);
    }

    @Override
    public boolean update(Reiziger reiziger) {
        return HibernateUtil.executeTransaction(session -> session.update(reiziger), session);
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        return HibernateUtil.executeTransaction(session -> session.delete(reiziger), session);
    }

    @Override
    public Reiziger findById(int id) {
        try {
            return session.get(Reiziger.class, id);
        } catch (Exception e) {
            System.err.println("Failed to find Reiziger by id: " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) {
        try {
            String hql = "FROM Reiziger WHERE geboortedatum = :date";
            Query<Reiziger> query = session.createQuery(hql, Reiziger.class);
            query.setParameter("date", date);
            return query.list();
        } catch (Exception e) {
            System.err.println("Failed to find Reizigers by geboortedatum: " + date + ". Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        String hql = "FROM Reiziger";
        Query<Reiziger> query = session.createQuery(hql, Reiziger.class);
        return query.list();
    }
}
