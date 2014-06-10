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

	@Override
	public String toString() {
		return "Fertigungsauftrag [fertigungsauftragsNr="
				+ fertigungsauftragsNr + ", bauteil=" + bauteil.getBauteilNr() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + auftragNr;
		result = prime * result + ((bauteil == null) ? 0 : bauteil.hashCode());
		result = prime * result + fertigungsauftragsNr;
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
		Fertigungsauftrag other = (Fertigungsauftrag) obj;
		if (auftragNr != other.auftragNr)
			return false;
		if (bauteil == null) {
			if (other.bauteil != null)
				return false;
		} else if (!bauteil.equals(other.bauteil))
			return false;
		if (fertigungsauftragsNr != other.fertigungsauftragsNr)
			return false;
		return true;
	}
}
