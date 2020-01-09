package be.sdlg.apps.edcmobile.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="FORMS_DATA")
@PrimaryKeyJoinColumn(name="ELECTRONIC_RECORD_ID")
public class FormData extends ElectronicRecord {
	
	public static final int STATUS_DRAFT=1;
	public static final int STATUS_READY_TO_SIGN=2;
	public static final int STATUS_SIGNED = 3;
	
	protected StudyEventData studyEventData;
	protected FormDef formDef;
	protected String formRepeatKey;
	protected Long status;
	protected Date statusDate;
	protected Set<ItemGroupData> itemGroupDataList;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	@Column(name="STATUS")
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	@ManyToOne
	public StudyEventData getStudyEventData() {
		return studyEventData;
	}
	public void setStudyEventData(StudyEventData studyEventData) {
		this.studyEventData = studyEventData;
	}
	@ManyToOne
	public FormDef getFormDef() {
		return formDef;
	}
	public void setFormDef(FormDef formDef) {
		this.formDef = formDef;
	}
	@Basic
	@Column(name="FORM_REPEATKEY")
	public String getFormRepeatKey() {
		return formRepeatKey;
	}
	public void setFormRepeatKey(String formRepeatKey) {
		this.formRepeatKey = formRepeatKey;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="formData", fetch=FetchType.LAZY)
	public Set<ItemGroupData> getItemGroupDataList() {
		if (itemGroupDataList == null) itemGroupDataList = new HashSet<ItemGroupData>(0);
		return itemGroupDataList;
	}
	public void setItemGroupDataList(Set<ItemGroupData> itemGroupDataList) {
		this.itemGroupDataList = itemGroupDataList;
	}
	
	
	

}