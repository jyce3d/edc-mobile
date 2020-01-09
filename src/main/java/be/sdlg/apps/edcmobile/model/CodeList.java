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

@Entity
@Table (name="CODELIST")
@PrimaryKeyJoinColumn(name="CLINICAL_DEF_ID")
public class CodeList extends ClinicalDef {
	public static final int DATATYPE_INT=1;
	public static final int DATATYPE_FLOAT = 2;
	public static final int DATATYPE_TEXT = 3;
	
	protected Set<ItemDef> itemDefList;
	protected Set<ItemRef> itemRefList;
	protected Set<CodeListItem> codeListItems;
	
	protected Long dataType;
	protected String sasFormatName;
	protected MetadataVersion metadataVersion;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="codeList", fetch=FetchType.LAZY)
	public Set<ItemDef> getItemDefList() {
		if (itemDefList == null) itemDefList = new HashSet<ItemDef>(0);
		return itemDefList;
	}
	public void setItemDefList(Set<ItemDef> itemDefList) {
		this.itemDefList = itemDefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="roleCodeList", fetch=FetchType.LAZY)
	public Set<ItemRef> getItemRefList() {
		if (itemRefList == null) itemRefList =new HashSet<ItemRef>(0);
		return itemRefList;
	}
	public void setItemRefList(Set<ItemRef> itemRefList) {
		this.itemRefList = itemRefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="codeList", fetch=FetchType.LAZY)
	public Set<CodeListItem> getCodeListItems() {
		if (codeListItems == null) codeListItems = new HashSet<CodeListItem>(0);
		return codeListItems;
	}
	public void setCodeListItems(Set<CodeListItem> codeListItems) {
		this.codeListItems = codeListItems;
	}
	public Long getDataType() {
		return dataType;
	}
	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}
	public String getSasFormatName() {
		return sasFormatName;
	}
	public void setSasFormatName(String sasFormatName) {
		this.sasFormatName = sasFormatName;
	}
	@ManyToOne
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}

	public void setMetadataVersion(MetadataVersion metadataVersion) {
		this.metadataVersion = metadataVersion;
	}

	
	

}