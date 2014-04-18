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
	@Column(name="STUECKLISTENPOSITIOn_ID")
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
}
