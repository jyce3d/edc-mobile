package be.sdlg.apps.edcmobile.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "FORMS_DATA_AUDIT")
@PrimaryKeyJoinColumn(name = "AUDIT_RECORD_ID")
public class FormDataAudit extends AuditRecord {
	protected String oldFormRepeatKey;
	protected String newFormRepeatKey;
// Non CDSIC
	//protected Set<ItemGroupDataAudit> itemGroupDataAuditList;
	

	protected Long oldStatus;
	protected Long newStatus;	
	
	
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

	@Column(name = "OLD_FORM_REPEATKEY")
	public String getOldFormRepeatKey() {
		return oldFormRepeatKey;
	}

	public void setOldFormRepeatKey(String oldFormRepeatKey) {
		this.oldFormRepeatKey = oldFormRepeatKey;
	}

	@Column(name = "NEW_FORM_REPEATKEY")
	public String getNewFormRepeatKey() {
		return newFormRepeatKey;
	}

	public void setNewFormRepeatKey(String newFormRepeatKey) {
		this.newFormRepeatKey = newFormRepeatKey;
	}
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy="formDataAudit", fetch=FetchType.LAZY)
	public Set<ItemGroupDataAudit> getItemGroupDataAuditList() {
		if (itemGroupDataAuditList == null) itemGroupDataAuditList = new HashSet<ItemGroupDataAudit>(0);
		return itemGroupDataAuditList;
	}

	public void setItemGroupDataAuditList(
			Set<ItemGroupDataAudit> itemGroupDataAuditList) {
		this.itemGroupDataAuditList = itemGroupDataAuditList;
	}*/
	
	

}