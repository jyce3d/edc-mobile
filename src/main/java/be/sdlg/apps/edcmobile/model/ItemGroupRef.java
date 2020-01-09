package be.sdlg.apps.edcmobile.model;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="ITEMGROUPS_REF")
@PrimaryKeyJoinColumn(name="CLINICAL_REF_ID")
public class ItemGroupRef extends ClinicalRef {
 protected FormDef formDef;
 protected ItemGroupDef itemGroupDef;
@ManyToOne 
public FormDef getFormDef() {
	return formDef;
}
public void setFormDef(FormDef formDef) {
	this.formDef = formDef;
}
@ManyToOne
public ItemGroupDef getItemGroupDef() {
	return itemGroupDef;
}
public void setItemGroupDef(ItemGroupDef itemGroupDef) {
	this.itemGroupDef = itemGroupDef;
}
 
}