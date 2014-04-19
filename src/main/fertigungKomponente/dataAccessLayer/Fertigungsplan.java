package main.fertigungKomponente.dataAccessLayer;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FERTIGUNGSPLAN")
public class Fertigungsplan {
	@Id
	@GeneratedValue
	@Column(name = "FERTIGUNGSPLAN_ID")
	private int fertigungsplanNr;

	@Column(name ="VORGAENGE") //TODO:Verify if name appears somewhere
	@OneToMany(fetch=FetchType.EAGER)
	private Set<Vorgang> vorgang;

	@OneToOne
	private Bauteil bauteil;

	public int getFertigungsplanNr() {
		return fertigungsplanNr;
	}

	public Set<Vorgang> getVorgang() {
		return vorgang;
	}

	public void setVorgang(LinkedHashSet<Vorgang> vorgang) {
		this.vorgang = vorgang;
	}

	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bauteil == null) ? 0 : bauteil.hashCode());
		result = prime * result + fertigungsplanNr;
		result = prime * result + ((vorgang == null) ? 0 : vorgang.hashCode());
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
		Fertigungsplan other = (Fertigungsplan) obj;
		if (bauteil == null) {
			if (other.bauteil != null)
				return false;
		} else if (!bauteil.equals(other.bauteil))
			return false;
		if (fertigungsplanNr != other.fertigungsplanNr)
			return false;
		if (vorgang == null) {
			if (other.vorgang != null)
				return false;
		} else if (!vorgang.equals(other.vorgang))
			return false;
		return true;
	}

}
