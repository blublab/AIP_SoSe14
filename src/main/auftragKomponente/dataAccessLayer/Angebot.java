package main.auftragKomponente.dataAccessLayer;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ANGEBOT")
public class Angebot {

	// TODO state pattern implementieren?
	public enum StatusTyp {
		ANGELEGT, ANGENOMMEN, ABGELAUFEN, AGESCHLOSSEN;
	}

	@Id
	@GeneratedValue
	@Column(name = "ANGEBOT_ID")
	private int angebotNr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GUELTIG_AB")
	private Date gueltigAb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GUELTIG_BIS")
	private Date gueltigBis;

	// TODO WaehrungsTyp definieren?
	@Column(name = "PREIS")
	private int preis;

	@Column(name = "STATUS")
	private Angebot.StatusTyp status;

	@OneToOne
	private Auftrag auftrag;

	@Column(name = "GESCHAEFTSPARTNER_ID")
	private int kundenNr;

	@Column(name = "BAUTEIL_ID")
	private int BauteilNr;

	public int getAngebotNr() {
		return angebotNr;
	}

	public void setAngebotNr(int angebotNr) {
		this.angebotNr = angebotNr;
	}

	public Date getGueltigAb() {
		return gueltigAb;
	}

	public void setGueltigAb(Date gueltigAb) {
		this.gueltigAb = gueltigAb;
	}

	public Date getGueltigBis() {
		return gueltigBis;
	}

	public void setGueltigBis(Date gueltigBis) {
		this.gueltigBis = gueltigBis;
	}

	public int getPreis() {
		return preis;
	}

	public void setPreis(int preis) {
		this.preis = preis;
	}

	public Angebot.StatusTyp getStatus() {
		return status;
	}

	public void setStatus(Angebot.StatusTyp status) {
		this.status = status;
	}

	public Auftrag getAuftrag() {
		return auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}

	public int getKundenNr() {
		return kundenNr;
	}

	public void setKundenNr(int kundenNR) {
		this.kundenNr = kundenNR;
	}

	public int getBauteilNr() {
		return BauteilNr;
	}

	public void setBauteilNr(int bauteilNr) {
		BauteilNr = bauteilNr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + BauteilNr;
		result = prime * result + angebotNr;
		result = prime * result + ((auftrag == null) ? 0 : auftrag.getAuftragsNr()); // nur + auftragNr, da sonst bei auftrag.hashCode() -> ZYKLUS 
		result = prime * result
				+ ((gueltigAb == null) ? 0 : gueltigAb.hashCode());
		result = prime * result
				+ ((gueltigBis == null) ? 0 : gueltigBis.hashCode());
		result = prime * result + kundenNr;
		result = prime * result + preis;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Angebot other = (Angebot) obj;
		if (BauteilNr != other.BauteilNr)
			return false;
		if (angebotNr != other.angebotNr)
			return false;
		if (auftrag == null) {
			if (other.auftrag != null)
				return false;
		} else if (auftrag.getAuftragsNr() != other.auftrag.getAuftragsNr()) //prüfung auf ID, da sonst ZYKLUS
			return false;
		Timestamp tsAb = new Timestamp(gueltigAb.getTime());
		tsAb.setNanos(0);
		Timestamp otherTsAb = new Timestamp(other.gueltigAb.getTime());
		otherTsAb.setNanos(0);
		if (gueltigAb == null) {
			if (other.gueltigAb != null)
				return false;
		} else if (!tsAb.equals(otherTsAb))
			return false;
		Timestamp tsBis = new Timestamp(gueltigBis.getTime());
		tsBis.setNanos(0);
		Timestamp otherTsBis = new Timestamp(other.gueltigBis.getTime());
		otherTsBis.setNanos(0);
		if (gueltigBis == null) {
			if (other.gueltigBis != null)
				return false;
		} else if (!tsBis.equals(otherTsBis))
			return false;
		if (kundenNr != other.kundenNr)
			return false;
		if (preis != other.preis)
			return false;
		if (status != other.status)
			return false;
		return true;
	}
}
