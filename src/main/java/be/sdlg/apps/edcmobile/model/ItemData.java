package be.sdlg.apps.edcmobile.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="ITEMS_DATA")
@PrimaryKeyJoinColumn(name="ELECTRONIC_RECORD_ID")
public class ItemData extends ElectronicRecord {
	public static final int ISNULL_YES =1;
	public static final int ISNULL_NO=0;
	protected ItemGroupData itemGroupData;
	protected ItemDef itemDef;
	
	protected String value;
	protected Long isNull;	
	// Non CDISC
	protected String codeListValue;
	protected Long status;
	protected Date statusDate;
	// Transient
	protected boolean dirty;
	protected String oldValue;
	protected Long oldIsNull;
	protected String oldCodeListValue;
	protected Long oldStatus;
	protected Date oldStatusDate;
	@Transient
	public Long getOldIsNull() {
		return oldIsNull;
	}
	public void setOldIsNull(Long oldIsNull) {
		this.oldIsNull = oldIsNull;
	}
	@Transient
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	@Transient
	public String getOldCodeListValue() {
		return oldCodeListValue;
	}
	public void setOldCodeListValue(String oldCodeListValue) {
		this.oldCodeListValue = oldCodeListValue;
	}
	@Transient
	public Long getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(Long oldStatus) {
		this.oldStatus = oldStatus;
	}
	@Transient
	public Date getOldStatusDate() {
		return oldStatusDate;
	}
	public void setOldStatusDate(Date oldStatusDate) {
		this.oldStatusDate = oldStatusDate;
	}
	@Transient
	public boolean isDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	@Column(name="STATUS")
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	@ManyToOne
	public ItemGroupData getItemGroupData() {
		return itemGroupData;
	}
	public void setItemGroupData(ItemGroupData itemGroupData) {
		this.itemGroupData = itemGroupData;
	}
	@ManyToOne
	public ItemDef getItemDef() {
		return itemDef;
	}
	public void setItemDef(ItemDef itemDef) {
		this.itemDef = itemDef;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(name="IS_NULL")
	public Long getIsNull() {
		return isNull;
	}
	public void setIsNull(Long isNull) {
		this.isNull = isNull;
	}
	public String getCodeListValue() {
		return codeListValue;
	}
	public void setCodeListValue(String codeListValue) {
		this.codeListValue = codeListValue;
	}
	
	

}