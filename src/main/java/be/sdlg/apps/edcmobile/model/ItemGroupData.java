package be.sdlg.apps.edcmobile.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "ITEMGROUPS_DATA")
@PrimaryKeyJoinColumn(name = "ELECTRONIC_RECORD_ID")
public class ItemGroupData extends ElectronicRecord {
	protected FormData formData;
	protected String itemGroupRepeatKey;
	protected Set<ItemData> itemDataList; 
	protected ItemGroupDef itemGroupDef;
	protected Long status;
	protected Date statusDate;
	
	//Transient
	protected String oldItemGroupRepeatKey;
	protected Long oldStatus;
	protected Date oldStatusDate;
	
	@Transient
	public String getOldItemGroupRepeatKey() {
		return oldItemGroupRepeatKey;
	}

	public void setOldItemGroupRepeatKey(String oldItemGroupRepeatKey) {
		this.oldItemGroupRepeatKey = oldItemGroupRepeatKey;
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
	public FormData getFormData() {
		return formData;
	}

	public void setFormData(FormData formData) {
		this.formData = formData;
	}

	public String getItemGroupRepeatKey() {
		return itemGroupRepeatKey;
	}

	public void setItemGroupRepeatKey(String itemGroupRepeatKey) {
		this.itemGroupRepeatKey = itemGroupRepeatKey;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="itemGroupData", fetch=FetchType.LAZY)
	public Set<ItemData> getItemDataList() {
		if (itemDataList == null) itemDataList = new HashSet<ItemData>(0);
		return itemDataList;
	}

	public void setItemDataList(Set<ItemData> itemDataList) {
		this.itemDataList = itemDataList;
	}
	@ManyToOne
	public ItemGroupDef getItemGroupDef() {
		return itemGroupDef;
	}

	public void setItemGroupDef(ItemGroupDef itemGroupDef) {
		this.itemGroupDef = itemGroupDef;
	}
	

}