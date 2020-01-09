package be.sdlg.apps.edcmobile.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table (name="ELECTRONIC_RECORDS")
@Inheritance(strategy = InheritanceType.JOINED)
public class ElectronicRecord {
	public static final int ACTIVE_YES = 1;
	public static final int ACTIVE_NO = 0;

	protected Long id;
	protected Set<Annotation> annotationList;
	protected Set<Signature> signatureList;
	protected Set<AuditRecord> auditRecordList;
	// outside CDISC
	protected Long active;
	protected Long oldActive;
	protected Date lastExportTime;
	protected ClinicalData clinicalData;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ELECTRONIC_RECORD_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="electronicRecord", fetch=FetchType.LAZY)
	public Set<Annotation> getAnnotationList() {
		if (annotationList == null) 
			annotationList = new HashSet<Annotation>(0);
		return annotationList;
	}
	public void setAnnotationList(Set<Annotation> annotationList) {
		this.annotationList = annotationList;
	}
	@OneToMany(cascade=CascadeType.ALL, mappedBy="electronicRecord", fetch=FetchType.LAZY)
	public Set<Signature> getSignatureList() {
		if (signatureList == null)
			signatureList = new HashSet<Signature>(0);
		return signatureList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="electronicRecord", fetch=FetchType.LAZY)
	public Set<AuditRecord> getAuditRecordList() {
		if (auditRecordList==null)
			auditRecordList = new HashSet<AuditRecord>(0);
		return auditRecordList;
	}
	public void setAuditRecordList(Set<AuditRecord> auditRecordList) {
		this.auditRecordList = auditRecordList;
	}
	public void setSignatureList(Set<Signature> signatureList) {
		this.signatureList = signatureList;
	}
	public Long getActive() {
		return active;
	}
	public void setActive(Long active) {
		this.active = active;
	}
	@Transient
	public Long getOldActive() {
		return oldActive;
	}
	public void setOldActive(Long oldActive) {
		this.oldActive = oldActive;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastExportTime() {
		return lastExportTime;
	}
	public void setLastExportTime(Date lastExportTime) {
		this.lastExportTime = lastExportTime;
	}
	@ManyToOne
	public ClinicalData getClinicalData() {
		return clinicalData;
	}
	public void setClinicalData(ClinicalData clinicalData) {
		this.clinicalData = clinicalData;
	}
	

}
