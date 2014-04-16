package main.auftragKomponente.dataAccessLayer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUFTRAG")
public class Auftrag {
	@Id
	@GeneratedValue
	@Column(name = "AUFTRAG_ID")
	private int auftragsNr;
	// @Column(name = "AUFTRAG_ABGESCHLOSSEN")
	private boolean istAbgeschlossen;
	// @Column(name = "BEAUFTRAG_AM")
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

}
