package main.buchhaltungKomponente.dataAccessLayer;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "KUNDENRECHNUNG")
public class Kundenrechnung {
//	@OneToOne
//	private Auftrag auftrag;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "kundenrechnung")
	private Set<Zahlungseingang> zahlungseingaenge;
	
	@Id
	@GeneratedValue
	@Column(name = "KUNDENRECHNUNG_ID")
	private int kundenrechnungNr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATUM")
	private Date erstelltAm;

	// TODO WaehrungsTyp definieren?
	@Column(name = "BETRAG")
	private int betrag;
	
	@Column
	private boolean istBezahlt;
	
	@Column(name = "AUFTRAG_ID")
	private int auftragsNr;

	public Set<Zahlungseingang> getZahlungseingaenge() {
		return this.zahlungseingaenge;
	}

	public void setZahlungseingaenge(Set<Zahlungseingang> ze) {
		this.zahlungseingaenge = ze;
	}

	
	public int getAuftragsNr() {
		return auftragsNr;
	}

	public void setAuftragsNr(int auftragsNr) {
		this.auftragsNr = auftragsNr;
	}

	public int getKundenrechnungNr() {
		return kundenrechnungNr;
	}

	public void setKundenrechnungNr(int kundenrechnungNr) {
		this.kundenrechnungNr = kundenrechnungNr;
	}

	public Date getErstelltAm() {
		return erstelltAm;
	}

	public void setErstelltAm(Date erstelltAm) {
		this.erstelltAm = erstelltAm;
	}
	
	public int getBetrag() {
		return betrag;
	}
	
	public void setBetrag(int newBetrag) {
		this.betrag = newBetrag;
	}

	public boolean isIstBezahlt() {
		return istBezahlt;
	}

	public void setIstBezahlt(boolean istBezahlt) {
		this.istBezahlt = istBezahlt;
	}

	@Override
	public String toString() {
		return "Kundenrechnung [kundenrechnungNr=" + kundenrechnungNr
				+ ", erstelltAm=" + erstelltAm + ", istBezahlt=" + istBezahlt
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((erstelltAm == null) ? 0 : erstelltAm.hashCode());
		result = prime * result + (istBezahlt ? 1231 : 1237);
		result = prime * result + kundenrechnungNr;
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
		Kundenrechnung other = (Kundenrechnung) obj;
		if (erstelltAm == null) {
			if (other.erstelltAm != null)
				return false;
		} else if (!erstelltAm.equals(other.erstelltAm))
			return false;
		if (istBezahlt != other.istBezahlt)
			return false;
		if (kundenrechnungNr != other.kundenrechnungNr)
			return false;
		return true;
	}
}
