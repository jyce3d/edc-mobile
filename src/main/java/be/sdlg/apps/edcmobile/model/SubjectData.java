package be.sdlg.apps.edcmobile.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
@Table (name="SUBJECT_DATA")
@PrimaryKeyJoinColumn(name="ELECTRONIC_RECORD_ID")
public class SubjectData extends ElectronicRecord {
	
	public static final int SUBJECT_SCREENING =1;
	public static final int SUBJECT_ENROLLED =2;
	public static final int SUBJECT_WITHDRAWN =3;
	public static final int SUBJECT_DROPPEDOUT = 4;
	
	protected String subjectKey;
	// investigator is set when the subject is created.
	protected DBUser investigatorRef;
	protected Location siteRef;
	
	
	protected Set<StudyEventData> studyEventDataList;

	// outside CDISC
	protected Long status;
	protected Date statusDate;
	
	@Column(name="SUBJECT_KEY")
	public String getSubjectKey() {
		return subjectKey;
	}
	public void setSubjectKey(String subjectKey) {
		this.subjectKey = subjectKey;
	}
	@ManyToOne
	public DBUser getInvestigatorRef() {
		return investigatorRef;
	}
	public void setInvestigatorRef(DBUser investigatorRef) {
		this.investigatorRef = investigatorRef;
	}
	@ManyToOne
	public Location getSiteRef() {
		return siteRef;
	}
	public void setSiteRef(Location siteRef) {
		this.siteRef = siteRef;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="subjectData", fetch=FetchType.LAZY)
	public Set<StudyEventData> getStudyEventDataList() {
		if (studyEventDataList == null) studyEventDataList = new HashSet<StudyEventData>(0);
		return studyEventDataList;
	}
	public void setStudyEventDataList(Set<StudyEventData> studyEventDataList) {
		this.studyEventDataList = studyEventDataList;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	
	

}