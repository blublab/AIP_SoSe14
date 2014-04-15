package main.auftragKomponente.dataAccessLayer;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.type.DateType;

@Entity
@SequenceGenerator(name = "auftrag_seq")
public class Auftrag implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auftrag_seq")
	private int auftragsNr;
	private boolean istAbgeschlossen;
	private DateType beauftragtAm;

	
	public boolean isIstAbgeschlossen() {
		return istAbgeschlossen;
	}

	public void setIstAbgeschlossen(boolean istAbgeschlossen) {
		this.istAbgeschlossen = istAbgeschlossen;
	}

	public DateType getBeauftragtAm() {
		return beauftragtAm;
	}

	public void setBeauftragtAm(DateType beauftragtAm) {
		this.beauftragtAm = beauftragtAm;
	}

	public int getAuftragsNr() {
		return auftragsNr;
	}


	
}
