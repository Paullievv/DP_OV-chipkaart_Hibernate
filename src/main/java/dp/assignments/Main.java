package dp.assignments;

import dp.assignments.ovchip.dao.OVChipkaartDAO;
import dp.assignments.ovchip.database.HibernateUtil;
import dp.assignments.ovchip.domain.OVChipkaart;
import dp.assignments.ovchip.domain.Reiziger;
import dp.assignments.ovchip.dao.ReizigerDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        testReizigerDAO(new ReizigerDAO(openSession()));
        testOVChipkaartDAO(new OVChipkaartDAO(openSession()));

        HibernateUtil.shutdown();
    }

    // Connection with Hibernte
    public static Session openSession() throws SQLException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
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
}