package dp.assignments.ovchip.dao;

import dp.assignments.ovchip.database.HibernateUtil;
import dp.assignments.ovchip.domain.OVChipkaart;
import dp.assignments.ovchip.domain.Product;
import dp.assignments.ovchip.interfaces.IProductDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO implements IProductDAO {

    private final Session session;

    public ProductDAO(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        return HibernateUtil.executeTransaction(session -> {
            session.merge(product);
            return null;
        }, session) != null;
    }

    @Override
    public boolean update(Product product) {
        return HibernateUtil.executeTransaction(session -> {
            session.merge(product);
            return null;
        }, session) != null;
    }

    @Override
    public boolean delete(Product product) {
        return HibernateUtil.executeTransaction(session -> {
            session.delete(product);
            return null;
        }, session) != null;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        String hql = "SELECT p FROM Product p JOIN p.ovChipkaarten o WHERE o.kaartNummer = :kaartNummer";
        Query<Product> query = session.createQuery(hql, Product.class);
        query.setParameter("kaartNummer", ovChipkaart.getKaartNummer());
        return query.list();
    }

    @Override
    public List<Product> findAll() {
        String hql = "FROM Product";
        Query<Product> query = session.createQuery(hql, Product.class);
        return query.list();
    }

    @Override
    public Product findById(int id){
        try {
            return session.get(Product.class, id);
        } catch (Exception e) {
            System.err.println("Failed to find Product by id: " + id + ". Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
