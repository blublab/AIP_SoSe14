package main.fertigungKomponente.dataAccessLayer;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "VORGANG")
public class Vorgang {

	public enum ArtTyp {
		BEREITSTELLUNG, MONTAGE
	}
	@Id
	@GeneratedValue
	@Column(name="VORGANG_ID")
	private int vorgangNr;
	
	@Column(name = "VORGANG_ART")
	private Vorgang.ArtTyp vorgangArt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RUESTZEIT")
	private Date ruestzeit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MASCHIENEN_ZEIT")
	private Date maschienenZeit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PERSONEN_ZEIT")
	private Date personenZeit;

	public int getVorgangNr() {
		return vorgangNr;
	}

	public Vorgang.ArtTyp getVorgangTyp() {
		return vorgangArt;
	}

	public void setVorgangTyp(Vorgang.ArtTyp vorgangTyp) {
		this.vorgangArt = vorgangTyp;
	}

	public Date getRuestzeit() {
		return ruestzeit;
	}

	public void setRuestzeit(Date ruestzeit) {
		this.ruestzeit = ruestzeit;
	}

	public Date getMaschienenZeit() {
		return maschienenZeit;
	}

	public void setMaschienenZeit(Date maschienenZeit) {
		this.maschienenZeit = maschienenZeit;
	}

	public Date getPersonenZeit() {
		return personenZeit;
	}

	public void setPersonenZeit(Date personenZeit) {
		this.personenZeit = personenZeit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((maschienenZeit == null) ? 0 : maschienenZeit.hashCode());
		result = prime * result
				+ ((personenZeit == null) ? 0 : personenZeit.hashCode());
		result = prime * result
				+ ((ruestzeit == null) ? 0 : ruestzeit.hashCode());
		result = prime * result
				+ ((vorgangArt == null) ? 0 : vorgangArt.hashCode());
		result = prime * result + vorgangNr;
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
		Vorgang other = (Vorgang) obj;
		
		Timestamp tsMaschienenZeit = new Timestamp(maschienenZeit.getTime());
		tsMaschienenZeit.setNanos(0);
		Timestamp otherTSMaschienenZeit = new Timestamp(other.maschienenZeit.getTime());
		otherTSMaschienenZeit.setNanos(0);
		if (maschienenZeit == null) {
			if (other.maschienenZeit != null)
				return false;
		} else if (tsMaschienenZeit.compareTo(otherTSMaschienenZeit) != 0)
			return false;
		
		Timestamp tsPersonenZeit = new Timestamp(personenZeit.getTime());
		tsPersonenZeit.setNanos(0);
		Timestamp otherTSPersonenZeit = new Timestamp(other.personenZeit.getTime());
		otherTSPersonenZeit.setNanos(0);
		if (personenZeit == null) {
			if (other.personenZeit != null)
				return false;
		} else if (tsPersonenZeit.compareTo(otherTSPersonenZeit) != 0)
			return false;
		
		Timestamp tsRuestZeit = new Timestamp(ruestzeit.getTime());
		tsRuestZeit.setNanos(0);
		Timestamp otherTSRuestZeit = new Timestamp(other.ruestzeit.getTime());
		otherTSRuestZeit.setNanos(0);
		if (ruestzeit == null) {
			if (other.ruestzeit != null)
				return false;
		} else if (tsRuestZeit.compareTo(otherTSRuestZeit) != 0)
			return false;
		
		if (vorgangArt != other.vorgangArt)
			return false;
		if (vorgangNr != other.vorgangNr)
			return false;
		return true;
	}
}
