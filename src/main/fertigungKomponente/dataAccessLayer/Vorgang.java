package main.fertigungKomponente.dataAccessLayer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "VORGANG")
public class Vorgang {

	public enum ArtTyp {
		BEREITSTELLUNG, MONTAGE
	}
	
	@Column(name = "VORGANGS_ART")
	private Vorgang.ArtTyp vorgangsArt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RUESTZEIT")
	private Date ruestzeit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MASCHIENEN_ZEIT")
	private Date maschienenZeit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PERSONEN_ZEIT")
	private Date personenZeit;

	public Vorgang.ArtTyp getVorgangTyp() {
		return vorgangsArt;
	}

	public void setVorgangTyp(Vorgang.ArtTyp vorgangTyp) {
		this.vorgangsArt = vorgangTyp;
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

}
