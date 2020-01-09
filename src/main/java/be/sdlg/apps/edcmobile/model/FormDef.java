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

@Entity
@Table (name="FORMS_DEF")
@PrimaryKeyJoinColumn(name="CLINICAL_DEF_ID")
public class FormDef extends ClinicalDef {
	protected Long repeating;
	protected Set<FormRef> formRefList;
	protected Set<FormData> formDataList;
	protected Set<ItemGroupRef> itemGroupRefList;
	protected MetadataVersion metadataVersion;
	@Basic
	public Long getRepeating() {
		return repeating;
	}

	public void setRepeating(Long repeating) {
		this.repeating = repeating;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="formDef", fetch=FetchType.LAZY)
	public Set<FormRef> getFormRefList() {
		if (formRefList==null) formRefList = new HashSet<FormRef>(0);
		return formRefList;
	}

	public void setFormRefList(Set<FormRef> formRefList) {
		this.formRefList = formRefList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="formDef", fetch=FetchType.LAZY)	
	public Set<FormData> getFormDataList() {
		if (formDataList==null) formDataList = new HashSet<FormData>(0);
		return formDataList;
	}

	public void setFormDataList(Set<FormData> formDataList) {
		this.formDataList = formDataList;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="formDef", fetch=FetchType.LAZY)	
	public Set<ItemGroupRef> getItemGroupRefList() {
		if (itemGroupRefList==null) itemGroupRefList = new HashSet<ItemGroupRef>(0);
		return itemGroupRefList;
	}

	public void setItemGroupRefList(Set<ItemGroupRef> itemGroupRefList) {
		this.itemGroupRefList = itemGroupRefList;
	}
	
	@ManyToOne
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}

	public void setMetadataVersion(MetadataVersion metadataVersion) {
		this.metadataVersion = metadataVersion;
	}

	
    
}