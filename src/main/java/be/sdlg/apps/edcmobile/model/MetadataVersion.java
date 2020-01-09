package be.sdlg.apps.edcmobile.model;

/***
 * MetadataVersion packages PROTOCOL XML entity and its content = {ALIASes, StudyEventDefs, description}
 */
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Table (name="METADATAVERSIONS")
public class MetadataVersion {
	protected Long id;
	protected Study study;
	protected String name;	
	protected Protocol protocol;
	protected Set<StudyEventDef> studyEventDefList;
	protected Set<FormDef> formDefList;
	protected Set<ItemGroupDef> itemGroupDefList;
	protected Set<ItemDef> itemDefList;
	protected Set<CodeList> codeListList;
	protected ClinicalData clinicalData;
	// NON CDISC
	protected Set<MetadataVersionRef> metadataVersionRefList;
	protected Long version; // Not required by CDISC but useful to determine which is the current version of the metadataversion.

	@OneToMany(cascade = CascadeType.ALL, mappedBy="metadataVersion", fetch=FetchType.LAZY)
	public Set<MetadataVersionRef> getMetadataVersionRefList() {
		if (metadataVersionRefList==null) metadataVersionRefList = new HashSet<MetadataVersionRef>(0);
		return metadataVersionRefList;
	}
	public void setMetadataVersionRefList(Set<MetadataVersionRef> metadataVersionRefList) {
		this.metadataVersionRefList = metadataVersionRefList;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="METADATAVERSION_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public Study getStudy() {
		return study;
	}
	public void setStudy(Study study) {
		this.study = study;
	}
	
	@Basic
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	@OneToOne(cascade=CascadeType.ALL)
	//@PrimaryKeyJoinColumn
	public Protocol getProtocol() {
		return protocol;
	}
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="metadataVersion", fetch=FetchType.LAZY)
	public Set<StudyEventDef> getStudyEventDefList() {
		if (studyEventDefList==null) studyEventDefList = new HashSet<StudyEventDef>(0);
		return studyEventDefList;
	}
	public void setStudyEventDefList(Set<StudyEventDef> studyEventDefList) {
		this.studyEventDefList = studyEventDefList;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="metadataVersion", fetch=FetchType.LAZY)
	public Set<FormDef> getFormDefList() {
		if (formDefList==null) formDefList = new HashSet<FormDef>(0);		
		return formDefList;
	}
	public void setFormDefList(Set<FormDef> formDefList) {
		this.formDefList = formDefList;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="metadataVersion", fetch=FetchType.LAZY)
	public Set<ItemGroupDef> getItemGroupDefList() {
		if (itemGroupDefList==null) itemGroupDefList = new HashSet<ItemGroupDef>(0);		
		return itemGroupDefList;
	}
	public void setItemGroupDefList(Set<ItemGroupDef> itemGroupDefList) {
		this.itemGroupDefList = itemGroupDefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="metadataVersion", fetch=FetchType.LAZY)
	public Set<ItemDef> getItemDefList() {
		if (itemDefList==null) itemDefList = new HashSet<ItemDef>(0);		
		return itemDefList;
	}
	public void setItemDefList(Set<ItemDef> itemDefList) {
		this.itemDefList = itemDefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="metadataVersion", fetch=FetchType.LAZY)
	public Set<CodeList> getCodeListList() {
		if (codeListList==null) codeListList = new HashSet<CodeList>(0);		
		return codeListList;
	}
	public void setCodeListList(Set<CodeList> codeListList) {
		this.codeListList = codeListList;
	}
	@OneToOne(mappedBy="metadataVersion", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public ClinicalData getClinicalData() {
		return clinicalData;
	}
	public void setClinicalData(ClinicalData clinicalData) {
		this.clinicalData = clinicalData;
	}
	
	
}