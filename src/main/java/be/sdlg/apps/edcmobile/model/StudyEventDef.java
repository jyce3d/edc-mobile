package be.sdlg.apps.edcmobile.model;


import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name="STUDYEVENTS_DEF")
@PrimaryKeyJoinColumn(name="CLINICAL_DEF_ID")
public class StudyEventDef extends ClinicalDef {
	
	public static final int TYPE_SCHEDULED=1;
	public static final int TYPE_UNSCHEDULED=2;
	public static final int TYPE_COMMON=3;
	
	public static final int REPEATING_YES =1;
	public static final int REPEATING_NO= 0;
		

	protected Long repeating;
	protected Long type;
	protected String category;	//Screening, pre-treatment, treatment, follow-up
	protected Set<FormRef> formRefList;
	protected Set<StudyEventRef> studyEventRefList;
	protected Set<StudyEventData> studyEventDataList;
	protected MetadataVersion metadataVersion;
	
	@Basic
	public Long getRepeating() {
		return repeating;
	}

	public void setRepeating(Long repeating) {
		this.repeating = repeating;
	}
	@Basic
	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
	@Basic
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="studyEventDef", fetch=FetchType.LAZY)
	public Set<FormRef> getFormRefList() {
		if (formRefList == null) formRefList = new HashSet<FormRef>(0);
		return formRefList;
	}

	public void setFormRefList(Set<FormRef> formRefList) {
		this.formRefList = formRefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="studyEventDef", fetch=FetchType.LAZY)
	public Set<StudyEventRef> getStudyEventRefList() {
		if (studyEventRefList == null) studyEventRefList = new HashSet<StudyEventRef>(0);
		return studyEventRefList;
	}

	public void setStudyEventRefList(Set<StudyEventRef> studyEventRefList) {
		this.studyEventRefList = studyEventRefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="studyEventDef", fetch=FetchType.LAZY)
	public Set<StudyEventData> getStudyEventDataList() {
		if (studyEventDataList == null) studyEventDataList = new HashSet<StudyEventData>(0);
		return studyEventDataList;
	}

	public void setStudyEventDataList(Set<StudyEventData> studyEventDataList) {
		this.studyEventDataList = studyEventDataList;
	}
	
	@ManyToOne
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}

	public void setMetadataVersion(MetadataVersion metadataVersion) {
		this.metadataVersion = metadataVersion;
	}


}