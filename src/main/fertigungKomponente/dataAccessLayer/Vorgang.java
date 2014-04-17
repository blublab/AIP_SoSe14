package main.fertigungKomponente.dataAccessLayer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "VORGANG")
public class Vorgang {

	@Column(name="VORGANG_TYP")
	private VorgangTyp vorgangTyp;
	
	//TODO: verify DATE type
	@Column(name="RUESTZEIT")
	private Date ruestzeit;
	
	//TODO: verify DATE type
	@Column(name ="MASCHIENEN_ZEIT")
	private Date maschienenZeit;
	
	//TODO: verify DATE type
	@Column(name="PERSONEN_ZEIT")
	private Date personenZeit;

	public VorgangTyp getVorgangTyp() {
		return vorgangTyp;
	}

	public void setVorgangTyp(VorgangTyp vorgangTyp) {
		this.vorgangTyp = vorgangTyp;
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
