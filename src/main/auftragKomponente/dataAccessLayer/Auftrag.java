package main.auftragKomponente.dataAccessLayer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.type.DateType;

@Entity
@Table(name = "AUFTRAG")
public class Auftrag {
	@Id
	@GeneratedValue
	@Column(name = "AUFTRAG_ID")
	private long auftragsNr;
	// @Column(name = "AUFTRAG_ABGESCHLOSSEN")
	private boolean istAbgeschlossen;
	// @Column(name = "BEAUFTRAG_AM")
	private DateType beauftragtAm;

	public long getAuftragsNr() {
		return auftragsNr;
	}

	public void setAuftragsNr(long auftragsNr) {
		this.auftragsNr = auftragsNr;
	}

	public boolean isIstAbgeschlossen() {
		return istAbgeschlossen;
	}

	public void setIstAbgeschlossen(boolean istAbgeschlossen) {
		this.istAbgeschlossen = istAbgeschlossen;
	}

	public DateType getBeauftragtAm() {
		return beauftragtAm;
	}

	public void setBeauftragtAm(DateType beauftragtAm) {
		this.beauftragtAm = beauftragtAm;
	}

}
