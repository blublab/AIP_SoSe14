package main.fertigungKomponente.dataAccessLayer;

import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(fetch=FetchType.EAGER)
	private List<Vorgang> vorgaenge;

	@OneToOne
	private Bauteil bauteil;

	public int getFertigungsplanNr() {
		return fertigungsplanNr;
	}

	public List<Vorgang> getVorgaenge() {
		return vorgaenge;
	}

	public void setVorgaenge(List<Vorgang> vorgang) {
		this.vorgaenge = vorgang;
	}

	public Bauteil getBauteil() {
		return bauteil;
	}

	public void setBauteil(Bauteil bauteil) {
		this.bauteil = bauteil;
	}
	
	public Fertigungsplan() {
		this.vorgaenge = new ArrayList<Vorgang>();
	}
	
	public boolean addVorgang(Vorgang v) {
		assert v != null;
		return this.vorgaenge.add(v);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bauteil == null) ? 0 : bauteil.hashCode());
		result = prime * result + fertigungsplanNr;
		result = prime * result + ((vorgaenge == null) ? 0 : vorgaenge.hashCode());
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
		if (vorgaenge == null) {
			if (other.vorgaenge != null)
				return false;
		} else if (!compareVorgaenge(other.getVorgaenge()))
			return false;
		return true;
	}
	
	private boolean compareVorgaenge(List<Vorgang> other) {
		int i = 0;
		for (Vorgang v : vorgaenge) {
			if(!v.equals(other.get(i))) {
				return false;
			}
			i++;
		}
		return true;
	}

}
