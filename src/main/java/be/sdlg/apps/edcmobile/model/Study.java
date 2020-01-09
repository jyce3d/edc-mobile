package be.sdlg.apps.edcmobile.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table (name="STUDIES")
public class Study {
	protected static final int UNIT_DAY =1;
	protected static final int UNIT_WEEK =2;
	protected static final int UNIT_MONTH = 3;
	
	protected Long id;

	protected Set<MetadataVersion> metadataVersionList;



	protected AdminData adminData;
	
	protected String studyName;
	protected String studyDescription;
	protected String protocolName;
	// Non CDISC
	protected Date studyStartDate;

	//Transient
	protected int subjectCount;
	

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	@Transient
	public int getSubjectCount() {
		return subjectCount;
	}

	public void setSubjectCount(int subjectCount) {
		this.subjectCount = subjectCount;
	}

	@Temporal(TemporalType.DATE)
	public Date getStudyStartDate() {
		return studyStartDate;
	}

	public void setStudyStartDate(Date studyStartDate) {
		this.studyStartDate = studyStartDate;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STUDY_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@OneToMany (cascade = CascadeType.ALL, mappedBy="study", fetch=FetchType.LAZY)
	public Set<MetadataVersion> getMetadataVersionList() {
		if (metadataVersionList == null) metadataVersionList = new HashSet<MetadataVersion>(0);
		return metadataVersionList;
	}

	public void setMetadataVersionList(Set<MetadataVersion> metadataVersionList) {
		this.metadataVersionList = metadataVersionList;
	}
	@Basic
	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	@Basic
	public String getStudyDescription() {
		return studyDescription;
	}

	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
	}
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="study", fetch=FetchType.LAZY)
	public AdminData getAdminData() {
		return adminData;
	}

	public void setAdminData(AdminData adminData) {
		this.adminData = adminData;
	}
	

}