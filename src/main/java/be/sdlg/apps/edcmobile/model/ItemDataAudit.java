package be.sdlg.apps.edcmobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ITEMS_DATA_AUDIT")
@PrimaryKeyJoinColumn(name = "AUDIT_RECORD_ID")
public class ItemDataAudit extends AuditRecord {
	protected String oldValue;
	protected String newValue;
	
	protected Long oldIsNull;
	protected Long newIsnull;
	
	protected String oldCodeListValue;
	protected String newCodeListValue;
	// Non CDSIC
	protected Long oldStatus;
	protected Long newStatus;
	
	//protected ItemGroupDataAudit itemGroupDataAudit;
	
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
	@Column(name = "OLD_VALUE")
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	@Column(name = "NEW_VALUE")
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	@Column(name = "OLD_ISNULL")
	public Long getOldIsNull() {
		return oldIsNull;
	}
	public void setOldIsNull(Long oldIsNull) {
		this.oldIsNull = oldIsNull;
	}
	@Column(name = "NEW_ISNULL")
	public Long getNewIsnull() {
		return newIsnull;
	}
	public void setNewIsnull(Long newIsnull) {
		this.newIsnull = newIsnull;
	}
	@Column(name = "OLD_CODELIST_VALUE")
	public String getOldCodeListValue() {
		return oldCodeListValue;
	}
	public void setOldCodeListValue(String oldCodeListValue) {
		this.oldCodeListValue = oldCodeListValue;
	}
	@Column(name = "NEW_CODELIST_VALUE")
	public String getNewCodeListValue() {
		return newCodeListValue;
	}
	public void setNewCodeListValue(String newCodeListValue) {
		this.newCodeListValue = newCodeListValue;
	}
	/*@ManyToOne
	public ItemGroupDataAudit getItemGroupDataAudit() {
		return itemGroupDataAudit;
	}
	public void setItemGroupDataAudit(ItemGroupDataAudit itemGroupDataAudit) {
		this.itemGroupDataAudit = itemGroupDataAudit;
	}*/
	
	
}