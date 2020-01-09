package be.sdlg.apps.edcmobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="SUBJECT_DATA_AUDIT")
@PrimaryKeyJoinColumn(name="AUDIT_RECORD_ID")
public class SubjectDataAudit extends AuditRecord {
	protected String newSubjectKey;
	protected String oldSubjectKey;
		
	protected Long oldStatus ;
	protected Long newStatus;
	
	protected Long oldSiteRefId;
	protected Long newSiteRefId;
	
	protected Long oldInvestigatorRefId;
	protected Long newInvestigatorRefId;
		
	@Column(name="NEW_KEY")
	public String getNewSubjectKey() {
		return newSubjectKey;
	}
	public void setNewSubjectKey(String newSubjectKey) {
		this.newSubjectKey = newSubjectKey;
	}
	@Column(name="OLD_KEY")
	public String getOldSubjectKey() {
		return oldSubjectKey;
	}
	public void setOldSubjectKey(String oldSubjectKey) {
		this.oldSubjectKey = oldSubjectKey;
	}
	@Column(name="OLD_STATUS")
	public Long getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(Long oldStatus) {
		this.oldStatus = oldStatus;
	}
	@Column(name="NEW_STATUS")
	public Long getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(Long newStatus) {
		this.newStatus = newStatus;
	}
	@Column(name="OLD_SITE_REF_ID")
	public Long getOldSiteRefId() {
		return oldSiteRefId;
	}
	public void setOldSiteRefId(Long oldSiteRefId) {
		this.oldSiteRefId = oldSiteRefId;
	}
	@Column(name="NEW_SITE_REF_ID")
	public Long getNewSiteRefId() {
		return newSiteRefId;
	}
	public void setNewSiteRefId(Long newSiteRefId) {
		this.newSiteRefId = newSiteRefId;
	}
	@Column(name="OLD_INVESTIGATOR_ID")
	public Long getOldInvestigatorRefId() {
		return oldInvestigatorRefId;
	}
	public void setOldInvestigatorRefId(Long oldInvestigatorRefId) {
		this.oldInvestigatorRefId = oldInvestigatorRefId;
	}
	@Column(name="NEW_INVESTIGATOR_ID")
	public Long getNewInvestigatorRefId() {
		return newInvestigatorRefId;
	}
	public void setNewInvestigatorRefId(Long newInvestigatorRefId) {
		this.newInvestigatorRefId = newInvestigatorRefId;
	}

	
	

}