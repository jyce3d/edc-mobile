package be.sdlg.apps.edcmobile.model;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="STUDYEVENTS_DATA")
@PrimaryKeyJoinColumn(name="ELECTRONIC_RECORD_ID")
public class StudyEventData extends ElectronicRecord {
	protected SubjectData subjectData;  // lie avec le sujet associé
	protected String studyEventRepeatKey;
	protected StudyEventDef studyEventDef; // Lien entre le StudyEventDef et le subject associé
	protected Set<FormData> formDataList;
	// NON CDISC
	protected Date effectiveDate;
	
	@Temporal(TemporalType.DATE)
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	@ManyToOne
	public SubjectData getSubjectData() {
		return subjectData;
	}
	public void setSubjectData(SubjectData subjectData) {
		this.subjectData = subjectData;
	}
	@Basic
	@Column(name="STUDYEVENT_REPEATKEY")
	public String getStudyEventRepeatKey() {
		return studyEventRepeatKey;
	}
	public void setStudyEventRepeatKey(String studyEventRepeatKey) {
		this.studyEventRepeatKey = studyEventRepeatKey;
	}
	@ManyToOne
	public StudyEventDef getStudyEventDef() {
		return studyEventDef;
	}
	public void setStudyEventDef(StudyEventDef studyEventDef) {
		this.studyEventDef = studyEventDef;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="studyEventData", fetch=FetchType.LAZY)
	public Set<FormData> getFormDataList() {
		if (formDataList == null) formDataList = new HashSet<FormData>(0);
		return formDataList;
	}
	public void setFormDataList(Set<FormData> formDataList) {
		this.formDataList = formDataList;
	}
	
	
	
	

}