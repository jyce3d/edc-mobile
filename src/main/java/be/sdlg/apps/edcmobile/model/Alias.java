package be.sdlg.apps.edcmobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="ALIASES")
public class Alias {
	protected Long id;
	protected String context; // application domain of the name
	protected String name;
	protected ClinicalDef clinicalDef;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ALIAS_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ManyToOne
	public ClinicalDef getClinicalDef() {
		return clinicalDef;
	}
	public void setClinicalDef(ClinicalDef clinicalDef) {
		this.clinicalDef = clinicalDef;
	}

}