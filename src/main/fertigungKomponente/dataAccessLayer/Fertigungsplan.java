package main.fertigungKomponente.dataAccessLayer;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	@OneToMany(cascade=CascadeType.ALL)
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

}
