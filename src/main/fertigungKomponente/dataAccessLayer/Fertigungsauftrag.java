package main.fertigungKomponente.dataAccessLayer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FERTIGUNGSAUFTRAG")
public class Fertigungsauftrag {

	@Id
	@GeneratedValue
	@Column(name = "FERTIGUNGSAUFTRAG_ID")
	private int fertigungsauftragsNr;

	@OneToOne
	private Bauteil bauteil;

	@Column(name = "AUFTRAG_ID")
	private int auftragNr;

	public int getFertigungsauftragsNr() {
		return fertigungsauftragsNr;
	}

	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}

	public int getAuftragNr() {
		return auftragNr;
	}

	public void setAuftragNr(int auftragNr) {
		this.auftragNr = auftragNr;
	}

}
