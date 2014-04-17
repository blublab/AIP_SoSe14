package main.fertigungKomponente.dataAccessLayer;

import javax.persistence.CascadeType;
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
	@Column(name = "AUFTRAG_ID")
	private int auftragsNr;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Bauteil bauteil;

	
	public int getAuftragsNr() {
		return auftragsNr;
	}

	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}

}
