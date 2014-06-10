package main.fertigungKomponente.dataAccessLayer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STUECKLISTENPOSITION")
public class Stuecklistenposition {
	@Id
	@GeneratedValue
	@Column(name="STUECKLISTENPOSITION_ID")
	private int stuecklistpositionNr;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "MENGE")
	private int menge;
	
	
	public int getStuecklistpositionNr() {
		return stuecklistpositionNr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + menge;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + stuecklistpositionNr;
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
		Stuecklistenposition other = (Stuecklistenposition) obj;
		if (menge != other.menge)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stuecklistpositionNr != other.stuecklistpositionNr)
			return false;
		return true;
	}
}
