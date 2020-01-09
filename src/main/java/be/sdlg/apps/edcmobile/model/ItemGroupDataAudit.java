package be.sdlg.apps.edcmobile.model;

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

@Entity
@Table(name = "ITEMGROUPS_DATA_AUDIT")
@PrimaryKeyJoinColumn(name = "AUDIT_RECORD_ID")
public class ItemGroupDataAudit extends AuditRecord {
	protected String oldItemGroupRepeatKey;
	protected String newItemGroupRepeatKey;
	
	// NOn cdisc
//	protected FormDataAudit formDataAudit;
	protected Long oldStatus;
	protected Long newStatus;
//	protected Set<ItemDataAudit> itemDataAuditList;
	
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
	@Column(name = "OLD_ITEMGROUP_REPEATKEY")
	public String getOldItemGroupRepeatKey() {
		return oldItemGroupRepeatKey;
	}
	public void setOldItemGroupRepeatKey(String oldItemGroupRepeatKey) {
		this.oldItemGroupRepeatKey = oldItemGroupRepeatKey;
	}
	@Column(name = "NEW_ITEMGROUP_REPEATKEY")
	public String getNewItemGroupRepeatKey() {
		return newItemGroupRepeatKey;
	}
	public void setNewItemGroupRepeatKey(String newItemGroupRepeatKey) {
		this.newItemGroupRepeatKey = newItemGroupRepeatKey;
	}
/*	@ManyToOne
	public FormDataAudit getFormDataAudit() {
		return formDataAudit;
	}
	public void setFormDataAudit(FormDataAudit formDataAudit) {
		this.formDataAudit = formDataAudit;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="itemGroupDataAudit", fetch=FetchType.LAZY)
	public Set<ItemDataAudit> getItemDataAuditList() {
		if (itemDataAuditList == null) itemDataAuditList = new HashSet<ItemDataAudit>(0);
		return itemDataAuditList;
	}
	public void setItemDataAuditList(Set<ItemDataAudit> itemDataAuditList) {
		this.itemDataAuditList = itemDataAuditList;
	}*/
	
	
	
	
}