package main.logistikKomponente.dataAccessLayer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSPORTAUFTRAG")
public class Transportauftrag {

    @Id
    @GeneratedValue
    @Column(name = "TRANSPORTAUFTRAG_ID")
    private Integer transportauftragsNr;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUSGANGSDATUM")
    private Date ausgangsdatum;

    @Column(name = "LIEFERRUNG")
    private Boolean lieferrungErfolgt;

    @Column(name = "TRANSPORTDIENSTLEISTER")
    private String transportdienstleister;

    public Integer getTransportauftragsNr() {
        return transportauftragsNr;
    }

    public void setTransportauftragsNr(Integer transportauftragsNr) {
        this.transportauftragsNr = transportauftragsNr;
    }

    public Date getAusgangsdatum() {
        return ausgangsdatum;
    }

    public void setAusgangsdatum(Date ausgangsdatum) {
        this.ausgangsdatum = ausgangsdatum;
    }

    public Boolean getLieferrungErfolgt() {
        return lieferrungErfolgt;
    }

    public void setLieferrungErfolgt(Boolean lieferrungErfolgt) {
        this.lieferrungErfolgt = lieferrungErfolgt;
    }

    public String getTransportdienstleister() {
        return transportdienstleister;
    }

    public void setTransportdienstleister(String transportdienstleister) {
        this.transportdienstleister = transportdienstleister;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transportauftrag that = (Transportauftrag) o;

        if (!ausgangsdatum.equals(that.ausgangsdatum)) return false;
        if (!lieferrungErfolgt.equals(that.lieferrungErfolgt)) return false;
        if (!transportauftragsNr.equals(that.transportauftragsNr)) return false;
        if (!transportdienstleister.equals(that.transportdienstleister)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transportauftragsNr.hashCode();
        result = 31 * result + ausgangsdatum.hashCode();
        result = 31 * result + lieferrungErfolgt.hashCode();
        result = 31 * result + transportdienstleister.hashCode();
        return result;
    }
}
