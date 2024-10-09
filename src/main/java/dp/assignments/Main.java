package dp.assignments;

import dp.assignments.ovchip.dao.OVChipkaartDAO;
import dp.assignments.ovchip.dao.ProductDAO;
import dp.assignments.ovchip.database.HibernateUtil;
import dp.assignments.ovchip.domain.OVChipkaart;
import dp.assignments.ovchip.domain.Product;
import dp.assignments.ovchip.domain.Reiziger;
import dp.assignments.ovchip.dao.ReizigerDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Session session = openSession();
        OVChipkaartDAO odao = new OVChipkaartDAO(session);

//        testReizigerDAO(new ReizigerDAO(session));
//        testOVChipkaartDAO(odao);
        testProductDAO(new ProductDAO(session), odao);

        HibernateUtil.shutdown();
    }

    // Connection with Hibernte
    public static Session openSession() throws SQLException {
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        return sessionFactory.openSession();
    }

    private static void testOVChipkaartDAO(OVChipkaartDAO ovChipkaartDAO) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle OV-chipkaarten op uit de database
        List<OVChipkaart> ovChipkaarten = ovChipkaartDAO.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OV-chipkaarten:");
        for (OVChipkaart oc : ovChipkaarten) {
            System.out.println(oc);
        }
        System.out.println();

        Reiziger reiziger = new Reiziger(99, "D", "", "Pijl", Date.valueOf("2003-06-11"));
        OVChipkaart ovChipkaart = new OVChipkaart(839204811, Date.valueOf("2028-07-25"), 2, 0.00, reiziger);

        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " OV-chipkaarten, na OVChipkaartDAO.save() ");
        ovChipkaartDAO.save(ovChipkaart);
        ovChipkaarten = ovChipkaartDAO.findAll();
        System.out.println(ovChipkaarten.size() + " OV-chipkaarten\n");

        // Update een bestaande OV-chipkaart en persisteer deze in de database
        System.out.println("[Test] Update het saldo van OV-chipkaart met nummer 839204811");
        ovChipkaart.setSaldo(150.0);
        ovChipkaartDAO.update(ovChipkaart);
        OVChipkaart updatedKaart = ovChipkaartDAO.findByID(839204811);
        System.out.println("Saldo na update: " + updatedKaart.getSaldo());
        System.out.println();

        // Vind OV-chipkaart aan de hand van ID
        System.out.println("[Test] Vind OV-chipkaart met nummer 839204811");
        OVChipkaart ocById = ovChipkaartDAO.findByID(839204811);
        if (ocById != null) {
            System.out.println("OV-chipkaart gevonden: " + ocById);
        } else {
            System.out.println("Geen OV-chipkaart gevonden met nummer 1");
        }
        System.out.println();

        // Vind OV-chipkaart aan de hand van reiziger
        List<OVChipkaart> ovChipkaartenByReiziger = ovChipkaartDAO.findByReiziger(reiziger);
        if (!ovChipkaartenByReiziger.isEmpty()) {
            System.out.println("Gevonden OV-chipkaarten:");
            for (OVChipkaart oc : ovChipkaartenByReiziger) {
                System.out.println(oc);
            }
        } else {
            System.out.println("Geen OV-chipkaarten gevonden voor Reiziger met ID " + reiziger.getId());
        }


        System.out.println("\n---------- Einde test OVChipkaartDAO -------------");
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update een bestaande reiziger en persisteer deze in de database
        System.out.println("[Test] Update de geboortedatum van Reiziger met ID 77");
        sietske.setGeboortedatum(java.sql.Date.valueOf("1985-05-25"));
        rdao.update(sietske);
        Reiziger updatedReiziger = rdao.findById(77);
        System.out.println("Geboortedatum na update: " + updatedReiziger.getGeboortedatum());
        System.out.println();

        // Delete een bestaande reiziger
        System.out.println("[Test] Verwijder reiziger met ID 77");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Aantal reizigers na ReizigerDAO.delete(): " + reizigers.size() + "\n");

        // Vind reiziger aan de hand van ID
        System.out.println("[Test] Vind reiziger met ID 1");
        Reiziger reizigerById = rdao.findById(1);
        if (reizigerById != null) {
            System.out.println("Reiziger gevonden: " + reizigerById);
        } else {
            System.out.println("Geen reiziger gevonden met ID 1");
        }
        System.out.println();

        // Vind reiziger aan de hand van geboortedatum
        System.out.println("[Test] Vind reizigers met geboortedatum '2003-06-11'");
        List<Reiziger> reizigersByDate = rdao.findByGbdatum(java.sql.Date.valueOf("2003-06-11"));
        if (reizigersByDate.isEmpty()) {
            System.out.println("Geen reizigers gevonden met geboortedatum '2003-06-11'");
        } else {
            System.out.println("Gevonden reizigers:");
            for (Reiziger r : reizigersByDate) {
                System.out.println(r);
            }
        }

        System.out.println("\n---------- Einde test ReizigerDAO -------------");
    }

    private static void testProductDAO(ProductDAO productDAO, OVChipkaartDAO ovChipkaartDAO) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");

        List<Product> products = productDAO.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println();

        Reiziger reiziger = new Reiziger(1, "G", "van", "Rijn", Date.valueOf("2002-09-17"));
        OVChipkaart ovChipkaart = new OVChipkaart(839204811, Date.valueOf("2028-07-25"), 2, 0.00, reiziger);
        ovChipkaartDAO.save(ovChipkaart);

        Product product = new Product(101, "Treinkaartje", "Een kaartje voor de trein", new BigDecimal("25.00"));

        System.out.print("[Test] Eerst " + products.size() + " producten, na ProductDAO.save() ");
        productDAO.save(product);
        products = productDAO.findAll();
        System.out.println(products.size() + " producten\n");

        System.out.println("[Test] Update de prijs van product met nummer 101");
        product.setPrijs(new BigDecimal("30.00"));
        productDAO.update(product);
        System.out.println();

        System.out.println("[Test] Vind product met nummer 101");
        Product foundProduct = productDAO.findById(101);
        if (foundProduct != null) {
            System.out.println("Product gevonden: " + foundProduct);
        } else {
            System.out.println("Geen product gevonden met nummer 101");
        }
        System.out.println();

        System.out.println("[Test] Koppel product met nummer 101 aan OV-chipkaart met nummer 839204811");
        ovChipkaart.addProduct(product);
        ovChipkaartDAO.update(ovChipkaart);
        List<Product> productsForChipkaart = productDAO.findByOVChipkaart(ovChipkaart);
        System.out.println("Gevonden producten voor OV-chipkaart 839204811:");
        for (Product p : productsForChipkaart) {
            System.out.println(p);
        }
        System.out.println();

        System.out.println("[Test] Verwijder product met nummer 101");
        ovChipkaartDAO.delete(ovChipkaart);
        productDAO.delete(product);
        products = productDAO.findAll();
        System.out.println("Aantal producten na ProductDAO.delete(): " + products.size());

        System.out.println("\n---------- Einde test ProductDAO -------------");
    }
}