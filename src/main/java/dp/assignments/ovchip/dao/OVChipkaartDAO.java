package dp.assignments.ovchip.dao;

import dp.assignments.ovchip.database.HibernateUtil;
import dp.assignments.ovchip.domain.OVChipkaart;
import dp.assignments.ovchip.domain.Reiziger;
import dp.assignments.ovchip.interfaces.IOVChipkaartDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class OVChipkaartDAO implements IOVChipkaartDAO {

    private final Session session;

    public OVChipkaartDAO(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        return HibernateUtil.executeTransaction(session -> {
            session.merge(ovChipkaart);
            return null;
        }, session) != null;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        return HibernateUtil.executeTransaction(session -> {
            session.merge(ovChipkaart);
            return null;
        }, session) != null;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        return HibernateUtil.executeTransaction(session -> {
            session.delete(ovChipkaart);
            return null;
        }, session) != null;
    }

    @Override
    public OVChipkaart findByID(int kaartNummer) throws SQLException {
        try {
            return session.get(OVChipkaart.class, kaartNummer);
        } catch (Exception e) {
            System.err.println("Failed to find OVChipkaart with card number: " + kaartNummer + ". Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        try {
            String hql = "FROM OVChipkaart WHERE reiziger = :reiziger";
            Query<OVChipkaart> query = session.createQuery(hql, OVChipkaart.class);
            query.setParameter("reiziger", reiziger);

            List<OVChipkaart> result = query.list();
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Failed to find OVChip cards by Reiziger ID: " + reiziger.getId() + ". Error: " + e.getMessage());
            return Collections.emptyList();
        }
    }


    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        String hql = "FROM OVChipkaart";
        Query<OVChipkaart> query = session.createQuery(hql, OVChipkaart.class);
        return query.list();
    }
}
