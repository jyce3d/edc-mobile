package be.sdlg.apps.edcmobile.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name="CLINICAL_DATA")
public class ClinicalData {
	protected Long id;
	protected MetadataVersion metadataVersion;
	
	protected Set<AuditRecord> auditRecordlist;
	// Non CDISC (index purpose)
	protected Set<ElectronicRecord> electronicRecordList;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CLINICALDATA_ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
/*	@OneToOne(mappedBy="clinicalData", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}*/
	@OneToOne
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}

	public void setMetadataVersion(MetadataVersion metadataVersion) {
		this.metadataVersion = metadataVersion;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy="clinicalData", fetch=FetchType.LAZY)
	public Set<AuditRecord> getAuditRecordlist() {
		if (this.auditRecordlist == null) 
			this.auditRecordlist = new HashSet<AuditRecord>(0);
		return auditRecordlist;
	}

	public void setAuditRecordlist(Set<AuditRecord> auditRecordlist) {
		this.auditRecordlist = auditRecordlist;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="clinicalData", fetch=FetchType.LAZY)
	public Set<ElectronicRecord> getElectronicRecordList() {
		if (electronicRecordList==null) 
			this.electronicRecordList = new HashSet<ElectronicRecord>(0);
		return electronicRecordList;
	}

	public void setElectronicRecordList(Set<ElectronicRecord> electronicRecordList) {
		this.electronicRecordList = electronicRecordList;
	}
	
	/*@Transient
	public List<SubjectData> getSubjectDataList() {
		List<SubjectData> subjectDataList = new ArrayList<SubjectData>();
		for (ElectronicRecord er : this.getElectronicRecordList() )
			if ( er instanceof SubjectData ) subjectDataList.add((SubjectData) er);
		return subjectDataList;
	}*/
	
	
}
