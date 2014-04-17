package main.fertigungKomponente.dataAccessLayer;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	@OneToMany
	private List<Stuecklistenposition> stuecklistenposition;
	
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

	public List<Stuecklistenposition> getStuecklistenposition() {
		return stuecklistenposition;
	}

	public void setStuecklistenposition(List<Stuecklistenposition> stuecklistenposition) {
		this.stuecklistenposition = stuecklistenposition;
	}


	public Stueckliste getStueckliste() {
		return stueckliste;
	}

	public void setStueckliste(Stueckliste stueckliste) {
		this.stueckliste = stueckliste;
	}
}
