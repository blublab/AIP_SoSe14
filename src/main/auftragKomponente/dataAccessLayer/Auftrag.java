package main.auftragKomponente.dataAccessLayer;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + auftragsNr;
		result = prime * result
				+ ((beauftragtAm == null) ? 0 : beauftragtAm.hashCode());
		result = prime * result + (istAbgeschlossen ? 1231 : 1237);
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
		return true;
	}

}
