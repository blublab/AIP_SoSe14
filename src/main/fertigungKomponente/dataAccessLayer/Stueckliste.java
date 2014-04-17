package main.fertigungKomponente.dataAccessLayer;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "STUECKLISTE")
public class Stueckliste {

	// TODO: verify DATE Type
	@Column(name = "GUELTIG_AB")
	private Date gueltigAb;

	// TODO: verify DATE Type
	@Column(name = "GUELTIG_BIS")
	private Date gueltigBis;
	
	@OneToMany
	private List<Stuecklistenposition> stuecklistenposition;

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

	public void setStuecklistenposition(List<Stuecklistenposition> stuecklistenposition) {
		this.stuecklistenposition = stuecklistenposition;
	}
}
