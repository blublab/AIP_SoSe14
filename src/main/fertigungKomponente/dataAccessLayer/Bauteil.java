package main.fertigungKomponente.dataAccessLayer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BAUTEIL")
public class Bauteil {

	@Id
	@GeneratedValue
	@Column(name = "BAUTEIL_ID")
	private int bauteilNr;
	
	@Column(name = "NAME")
	private String name;
	
	@OneToOne
	private Fertigungsplan fertigungsplan;

	@OneToMany(fetch=FetchType.EAGER)
	private Set<Stuecklistenposition> stuecklistenposition;
	
	@OneToOne
	private Stueckliste stueckliste;
	
	public int getBauteilNr() {
		return bauteilNr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Fertigungsplan getFertigungsplan() {
		return fertigungsplan;
	}

	public void setFertigungsplan(Fertigungsplan fertigungsplan) {
		this.fertigungsplan = fertigungsplan;
	}

	public Set<Stuecklistenposition> getStuecklistenposition() {
		return stuecklistenposition;
	}

	public void setStuecklistenposition(Set<Stuecklistenposition> stuecklistenposition) {
		this.stuecklistenposition = stuecklistenposition;
	}


	public Stueckliste getStueckliste() {
		return stueckliste;
	}

	public void setStueckliste(Stueckliste stueckliste) {
		this.stueckliste = stueckliste;
	}
	
	public Bauteil() {
		this.stuecklistenposition = new HashSet<Stuecklistenposition>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bauteilNr;
		result = prime * result
				+ ((fertigungsplan == null) ? 0 : fertigungsplan.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((stueckliste == null) ? 0 : stueckliste.hashCode());
		result = prime
				* result
				+ ((stuecklistenposition == null) ? 0 : stuecklistenposition
						.hashCode());
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
		Bauteil other = (Bauteil) obj;
		if (bauteilNr != other.bauteilNr)
			return false;
		if (fertigungsplan == null) {
			if (other.fertigungsplan != null)
				return false;
		} else if (fertigungsplan.getFertigungsplanNr() != other.fertigungsplan.getFertigungsplanNr())
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stueckliste == null) {
			if (other.stueckliste != null)
				return false;
		} else if (!stueckliste.equals(other.stueckliste))
			return false;
		if (stuecklistenposition == null) {
			if (other.stuecklistenposition != null)
				return false;
		} else if (!checkStuecklistenposition(other.getStuecklistenposition()))
			return false;
		return true;
	}
	
	private boolean checkStuecklistenposition(Set<Stuecklistenposition> other) {
		if(stuecklistenposition.size() != other.size()) {
			return false;
		}
		Set<Stuecklistenposition> commonSet = new HashSet<Stuecklistenposition>(stuecklistenposition);
		commonSet.retainAll(other);
		return commonSet.size() == stuecklistenposition.size();
	}
}
