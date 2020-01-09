package be.sdlg.apps.edcmobile.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="PROTOCOL")
//@PrimaryKeyJoinColumn(name="CLINICAL_DEF_ID")
public class Protocol  {
	protected Long id;
	protected MetadataVersion metadataVersion;
	protected Set<StudyEventRef> studyEventRefList;

	@OneToOne(mappedBy="protocol", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}
	public void setMetadataVersion(MetadataVersion metadataVersion) {
		this.metadataVersion = metadataVersion;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="protocol", fetch=FetchType.LAZY)	
	public Set<StudyEventRef> getStudyEventRefList() {
		if (studyEventRefList == null) studyEventRefList = new HashSet<StudyEventRef>(0);
		return studyEventRefList;
	}

	public void setStudyEventRefList(Set<StudyEventRef> studyEventRefList) {
		this.studyEventRefList = studyEventRefList;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PROTOCOL_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}