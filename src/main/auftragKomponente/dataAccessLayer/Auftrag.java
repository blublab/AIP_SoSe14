package main.auftragKomponente.dataAccessLayer;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "AUFTRAG")
public class Auftrag {
	@Id
	@GeneratedValue
	@Column(name = "AUFTRAG_ID")
	private int auftragsNr;

	@Column(name = "AUFTRAG_ABGESCHLOSSEN")
	private boolean istAbgeschlossen;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BEAUFTRAG_AM")
	private Date beauftragtAm;

	@OneToOne
	private Angebot angebot;

	@Column(name = "KUNDENRECHNUNG_ID")
	private int kundenrechnungsNr;

	@Column(name = "LIEFERUNG_ID")
	private int lieferungsNr;

	public int getAuftragsNr() {
		return auftragsNr;
	}

	public void setAuftragsNr(int auftragsNr) {
		this.auftragsNr = auftragsNr;
	}

	public boolean isIstAbgeschlossen() {
		return istAbgeschlossen;
	}

	public void setIstAbgeschlossen(boolean istAbgeschlossen) {
		this.istAbgeschlossen = istAbgeschlossen;
	}

	public Date getBeauftragtAm() {
		return beauftragtAm;
	}

	public void setBeauftragtAm(Date beauftragtAm) {
		this.beauftragtAm = beauftragtAm;
	}

	public Angebot getAngebot() {
		return angebot;
	}

	public void setAngebot(Angebot angebot) {
		this.angebot = angebot;
	}

	public int getKundenrechnungsNr() {
		return kundenrechnungsNr;
	}

	public void setKundenrechnungsNr(int kundenrechnungsNr) {
		this.kundenrechnungsNr = kundenrechnungsNr;
	}

	public int getLieferungsNr() {
		return lieferungsNr;
	}

	public void setLieferungsNr(int lieferungsNr) {
		this.lieferungsNr = lieferungsNr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((angebot == null) ? 0 : angebot.hashCode());
		result = prime * result + auftragsNr;
		result = prime * result
				+ ((beauftragtAm == null) ? 0 : beauftragtAm.hashCode());
		result = prime * result + (istAbgeschlossen ? 1231 : 1237);
		result = prime * result + kundenrechnungsNr;
		result = prime * result + lieferungsNr;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auftrag other = (Auftrag) obj;
		if (angebot == null) {
			if (other.angebot != null)
				return false;
		} else if (!angebot.equals(other.angebot))
			return false;
		if (auftragsNr != other.auftragsNr)
			return false;
		Timestamp ts = new Timestamp(beauftragtAm.getTime());
		ts.setNanos(0);
		Timestamp otherTS = new Timestamp(other.beauftragtAm.getTime());
		otherTS.setNanos(0);
		if (beauftragtAm == null) {
			if (other.beauftragtAm != null)
				return false;
		} else if (ts.compareTo(otherTS) != 0)
			return false;
		if (istAbgeschlossen != other.istAbgeschlossen)
			return false;
		if (kundenrechnungsNr != other.kundenrechnungsNr)
			return false;
		if (lieferungsNr != other.lieferungsNr)
			return false;
		return true;
	}
}