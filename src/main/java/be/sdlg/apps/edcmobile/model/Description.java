package be.sdlg.apps.edcmobile.model;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="DESCRIPTION")
@PrimaryKeyJoinColumn(name="TRANSLATABLE_ID")
public class Description extends Translatable {
	protected ClinicalDef clinicalDef;
	@OneToOne
	public ClinicalDef getClinicalDef() {
		return clinicalDef;
	}

	public void setClinicalDef(ClinicalDef clinicalDef) {
		this.clinicalDef = clinicalDef;
	}
	
	

}