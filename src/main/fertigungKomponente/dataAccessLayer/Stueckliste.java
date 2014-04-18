package main.fertigungKomponente.dataAccessLayer;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "STUECKLISTE")
public class Stueckliste {
	@Id
	@GeneratedValue
	@Column(name="STUECKLISTE_ID")
	private int stuecklistNr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GUELTIG_AB")
	private Date gueltigAb;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GUELTIG_BIS")
	private Date gueltigBis;

	@OneToMany
	private List<Stuecklistenposition> stuecklistenposition;

	public int getStuecklistNr() {
		return stuecklistNr;
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

	public List<Stuecklistenposition> getStuecklistenposition() {
		return stuecklistenposition;
	}

	public void setStuecklistenposition(
			List<Stuecklistenposition> stuecklistenposition) {
		this.stuecklistenposition = stuecklistenposition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((gueltigAb == null) ? 0 : gueltigAb.hashCode());
		result = prime * result
				+ ((gueltigBis == null) ? 0 : gueltigBis.hashCode());
		result = prime * result + getStuecklistNr();
		result = prime
				* result
				+ ((stuecklistenposition == null) ? 0 : stuecklistenposition
						.hashCode());
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
		Stueckliste other = (Stueckliste) obj;
		
		Timestamp tsAb = new Timestamp(gueltigAb.getTime());
		tsAb.setNanos(0);
		Timestamp otherTSAb = new Timestamp(other.gueltigAb.getTime());
		otherTSAb.setNanos(0);
		
		if (gueltigAb == null) {
			if (other.gueltigAb != null)
				return false;
		} else if (tsAb.compareTo(otherTSAb)!=0)
			return false;
		
		Timestamp tsBis = new Timestamp(gueltigBis.getTime());
		tsBis.setNanos(0);
		Timestamp otherTSBis = new Timestamp(other.gueltigBis.getTime());
		otherTSBis.setNanos(0);
		
		if (gueltigBis == null) {
			if (other.gueltigBis != null)
				return false;
		} else if (tsBis.compareTo(otherTSBis)!=0)
			return false;
		if (getStuecklistNr() != other.getStuecklistNr())
			return false;
		if (stuecklistenposition == null) {
			if (other.stuecklistenposition != null)
				return false;
		} else if (!stuecklistenposition.equals(other.stuecklistenposition))
			return false;
		return true;
	}
}
