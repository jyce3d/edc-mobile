package be.sdlg.apps.edcmobile.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Column;
@Entity
@Table (name="ITEMGROUPS_DEF")
@PrimaryKeyJoinColumn(name="CLINICAL_DEF_ID")
public class ItemGroupDef extends ClinicalDef {
	public static final int REFERENCE_DATA_YES = 1;
	public static final int REFERENCE_DATA_NO = 0;

	public static final int REPEATING_YES = 1;
	public static final int REPEATING_NO = 0;

	protected Long repeating;
	protected Long referenceData;
	protected String sasDatasetName; // Max8
	protected String domain;
	protected String origin;
	protected String comment;
	protected String purpose;
	
	protected Set<ItemGroupRef> itemGroupRefList;
	protected Set<ItemRef> itemRefList;
	protected Set<ItemGroupData> itemGroupDataList;
	protected MetadataVersion metadataVersion;
	@Basic
	public Long getRepeating() {
		return repeating;
	}
	public void setRepeating(Long repeating) {
		this.repeating = repeating;
	}
	@Basic
	public Long getReferenceData() {
		return referenceData;
	}
	public void setReferenceData(Long referenceData) {
		this.referenceData = referenceData;
	}
	@Basic
	//TODO : size=8
	@Column(name="SAS_DATASET_NAME")
	public String getSasDatasetName() {
		return sasDatasetName;
	}
	public void setSasDatasetName(String sasDatasetName) {
		this.sasDatasetName = sasDatasetName;
	}
	@Basic
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	@Basic
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@Basic
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Basic
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="itemGroupDef", fetch=FetchType.LAZY)
	public Set<ItemGroupRef> getItemGroupRefList() {
		if (itemGroupRefList == null) itemGroupRefList = new HashSet<ItemGroupRef>(0);
		return itemGroupRefList;
	}
	public void setItemGroupRefList(Set<ItemGroupRef> itemGroupRefList) {
		this.itemGroupRefList = itemGroupRefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="itemGroupDef", fetch=FetchType.LAZY)
	public Set<ItemRef> getItemRefList() {
		if (itemRefList==null) itemRefList = new HashSet<ItemRef>(0);
		return itemRefList;
	}
	public void setItemRefList(Set<ItemRef> itemRefList) {
		this.itemRefList = itemRefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="itemGroupDef", fetch=FetchType.LAZY)
	public Set<ItemGroupData> getItemGroupDataList() {
		if (itemGroupDataList == null) itemGroupDataList = new HashSet<ItemGroupData>(0);
		return itemGroupDataList;
	}
	public void setItemGroupDataList(Set<ItemGroupData> itemGroupDataList) {
		this.itemGroupDataList = itemGroupDataList;
	}
	@ManyToOne
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}

	public void setMetadataVersion(MetadataVersion metadataVersion) {
		this.metadataVersion = metadataVersion;
	}

}