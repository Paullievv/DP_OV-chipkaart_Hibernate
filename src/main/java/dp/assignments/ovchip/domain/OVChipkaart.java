package dp.assignments.ovchip.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {

    @Id
    @Column(name = "kaart_nummer")
    private int kaartNummer;
    @Column(name = "geldig_tot")
    private Date geldigTot;
    private int klasse;
    private double saldo;
    @ManyToOne
    @JoinColumn(name = "reiziger_id", referencedColumnName = "reiziger_id")
    private Reiziger reiziger;

    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, double saldo, Reiziger reiziger) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public OVChipkaart() {

    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString() {
        return "OVChipkaart: (" + kaartNummer +
                ", geldig tot: " + geldigTot +
                ", klasse: " + klasse +
                ", saldo: " + saldo +
                ", reiziger: " + (reiziger != null ? reiziger.toString() : "null") +
                ')';
    }
}
