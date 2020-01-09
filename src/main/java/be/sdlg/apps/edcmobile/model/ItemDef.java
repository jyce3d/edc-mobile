package be.sdlg.apps.edcmobile.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="ITEMS_DEF")
@PrimaryKeyJoinColumn(name="CLINICAL_DEF_ID")
public class ItemDef extends ClinicalDef {
	public static final int DATATYPE_INT=1;
	public static final int DATATYPE_FLOAT=6;
	public static final int DATATYPE_DATE=3;
	public static final Long DATATYPE_TEXT=new Long(4);
	public static final int DATATYPE_BOOLEAN=2;
	public static final int DATATYPE_URI = 5;
	public static final int DATATYPE_LABEL =99;
	
	public static final int IS_NOT_NULL = 1;
	public static final int IS_NULL = 0;
	
	public static final String BOOLEAN_TRUE ="1";
	public static final String BOOLEAN_FALSE ="0";
	
    protected Set<MeasurementUnit> measurementUnitList;
	protected Set<RangeCheck> rangeCheckList;
	protected Set<ItemRef> itemRefList;
	protected Set<ItemData> itemDataList;
	
	protected CodeList codeList; //ManytoOne
	protected Question question; 
	
	protected Long dataType;
	protected Long length;
	protected Long significantDigits;
	protected String sasFieldName;
	protected String sdsVarName;
	protected String origin;
	protected String comment;
	protected MetadataVersion metadataVersion;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="itemDef", fetch=FetchType.LAZY)
	public Set<MeasurementUnit> getMeasurementUnitList() {
		if (measurementUnitList == null) measurementUnitList = new HashSet<MeasurementUnit>(0);
		return measurementUnitList;	
	}
	public void setMeasurementUnitList(
			Set<MeasurementUnit> measurementUnitList) {
		this.measurementUnitList = measurementUnitList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="itemDef", fetch=FetchType.LAZY)
	public Set<RangeCheck> getRangeCheckList() {
		if (rangeCheckList == null) rangeCheckList = new HashSet<RangeCheck>(0);
		return rangeCheckList;
	}
	public void setRangeCheckList(Set<RangeCheck> rangeCheckList) {
		this.rangeCheckList = rangeCheckList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="itemDef", fetch=FetchType.LAZY)
	public Set<ItemRef> getItemRefList() {
		return itemRefList;
	}
	public void setItemRefList(Set<ItemRef> itemRefList) {
		this.itemRefList = itemRefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="itemDef", fetch=FetchType.LAZY)
	public Set<ItemData> getItemDataList() {
		if (itemDataList == null) itemDataList = new HashSet<ItemData>(0);
		return itemDataList;
	}
	public void setItemDataList(Set<ItemData> itemDataList) {
		this.itemDataList = itemDataList;
	}
	@ManyToOne
	public CodeList getCodeList() {
		return codeList;
	}
	public void setCodeList(CodeList codeList) {
		this.codeList = codeList;
	}
	@OneToOne(cascade = CascadeType.ALL, mappedBy="itemDef", fetch=FetchType.LAZY)
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Long getDataType() {
		return dataType;
	}

	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public Long getSignificantDigits() {
		return significantDigits;
	}
	public void setSignificantDigits(Long significantDigits) {
		this.significantDigits = significantDigits;
	}
	public String getSasFieldName() {
		return sasFieldName;
	}
	public void setSasFieldName(String sasFieldName) {
		this.sasFieldName = sasFieldName;
	}
	public String getSdsVarName() {
		return sdsVarName;
	}
	public void setSdsVarName(String sdsVarName) {
		this.sdsVarName = sdsVarName;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@ManyToOne
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}

	public void setMetadataVersion(MetadataVersion metadataVersion) {
		this.metadataVersion = metadataVersion;
	}

}