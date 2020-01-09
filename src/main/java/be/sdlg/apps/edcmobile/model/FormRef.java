package be.sdlg.apps.edcmobile.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="FORMS_REF")
@PrimaryKeyJoinColumn(name="CLINICAL_REF_ID")
public class FormRef extends ClinicalRef {
	protected StudyEventDef studyEventDef;
	protected FormDef formDef;

	@ManyToOne
	public StudyEventDef getStudyEventDef() {
		return studyEventDef;
	}

	public void setStudyEventDef(StudyEventDef studyEventDef) {
		this.studyEventDef = studyEventDef;
	}
	@ManyToOne
	public FormDef getFormDef() {
		return formDef;
	}

	public void setFormDef(FormDef formDef) {
		this.formDef = formDef;
	}
	
}
	
