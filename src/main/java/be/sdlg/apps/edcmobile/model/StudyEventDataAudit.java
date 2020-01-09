package be.sdlg.apps.edcmobile.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name="STUDYEVENTS_DATA_AUDIT")
@PrimaryKeyJoinColumn(name="AUDIT_RECORD_ID")
public class StudyEventDataAudit extends AuditRecord {
	protected String oldStudyEventRepeatKey;
	protected String newStudyEventRepeatKey;
	protected Date oldEffectiveDate;
	protected Date newEffectiveDate;
	
	@Column(name="OLD_STUDYEVENT_REPEATKEY")
	public String getOldStudyEventRepeatKey() {
		return oldStudyEventRepeatKey;
	}
	public void setOldStudyEventRepeatKey(String oldStudyEventRepeatKey) {
		this.oldStudyEventRepeatKey = oldStudyEventRepeatKey;
	}
	@Column(name="NEW_STUDYEVENT_REPEATKEY")
	public String getNewStudyEventRepeatKey() {
		return newStudyEventRepeatKey;
	}
	public void setNewStudyEventRepeatKey(String newStudyEventRepeatKey) {
		this.newStudyEventRepeatKey = newStudyEventRepeatKey;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="OLD_EFFECTIVE_DATE")
	public Date getOldEffectiveDate() {
		return oldEffectiveDate;
	}
	public void setOldEffectiveDate(Date oldEffectiveDate) {
		this.oldEffectiveDate = oldEffectiveDate;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="NEW_EFFECTIVE_DATE")
	public Date getNewEffectiveDate() {
		return newEffectiveDate;
	}
	public void setNewEffectiveDate(Date newEffectiveDate) {
		this.newEffectiveDate = newEffectiveDate;
	}
	
	
	
	
}