package main.buchhaltungKomponente.dataAccessLayer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ZAHLUNGSEINGANG")
public class Zahlungseingang {
		
	@Id
	@GeneratedValue
	@Column(name = "ZAHLUNGSEINGANG_ID")
	private int zahlungseingangNr;

	@ManyToOne
	private Kundenrechnung kundenrechnung;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EINGANGSDATUM")
	private Date eingegangenAm;
	
	@Column
	private int betrag;

	public Kundenrechnung getKundenrechnung() {
		return kundenrechnung;
	}

	public void setKundenrechnung(Kundenrechnung kundenrechnung) {
		this.kundenrechnung = kundenrechnung;
	}

	public int getZahlungseingangNr() {
		return zahlungseingangNr;
	}

	public void setZahlungseingangNr(int zahlungseingangNr) {
		this.zahlungseingangNr = zahlungseingangNr;
	}

	public Date getEingegangenAm() {
		return eingegangenAm;
	}

	public void setEingegangenAm(Date eingegangenAm) {
		this.eingegangenAm = eingegangenAm;
	}

	public int getBetrag() {
		return betrag;
	}

	public void setBetrag(int betrag) {
		this.betrag = betrag;
	}

	@Override
	public String toString() {
		return "Zahlungseingang [kundenrechnung=" + kundenrechnung
				+ ", zahlungseingangNr=" + zahlungseingangNr
				+ ", eingegangenAm=" + eingegangenAm + ", betrag=" + betrag
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + betrag;
		result = prime * result
				+ ((eingegangenAm == null) ? 0 : eingegangenAm.hashCode());
		result = prime * result
				+ ((kundenrechnung == null) ? 0 : kundenrechnung.hashCode());
		result = prime * result + zahlungseingangNr;
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
		Zahlungseingang other = (Zahlungseingang) obj;
		if (betrag != other.betrag)
			return false;
		if (eingegangenAm == null) {
			if (other.eingegangenAm != null)
				return false;
		} else if (!eingegangenAm.equals(other.eingegangenAm))
			return false;
		if (kundenrechnung == null) {
			if (other.kundenrechnung != null)
				return false;
		} else if (!kundenrechnung.equals(other.kundenrechnung))
			return false;
		if (zahlungseingangNr != other.zahlungseingangNr)
			return false;
		return true;
	}
}
