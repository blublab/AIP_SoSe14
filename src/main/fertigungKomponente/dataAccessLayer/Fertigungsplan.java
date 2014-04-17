package main.fertigungKomponente.dataAccessLayer;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FERTIGUNGSPLAN")
public class Fertigungsplan {
	@Id
	@GeneratedValue
	@Column(name = "FERTIGUNGSPLAN_ID")
	private int fertigungsplanNr;
	
	@OneToMany
	private Set<Vorgang> vorgang;

	public int getFertigungsplanNr() {
		return fertigungsplanNr;
	}

	public Set<Vorgang> getVorgang() {
		return vorgang;
	}

	public void setVorgang(Set<Vorgang> vorgang) {
		this.vorgang = vorgang;
	}

}
