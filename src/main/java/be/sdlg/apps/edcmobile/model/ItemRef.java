package be.sdlg.apps.edcmobile.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="ITEMS_REF")
@PrimaryKeyJoinColumn(name="CLINICAL_REF_ID")
public class ItemRef extends ClinicalRef {
	protected ItemGroupDef itemGroupDef;
	protected ItemDef itemDef;
	protected CodeList roleCodeList;
	
	protected Long keySequence;
	//TODO : add the method of derivation
	// protected Method method;
	protected String role;
	@ManyToOne
	public ItemGroupDef getItemGroupDef() {
		return itemGroupDef;
	}
	public void setItemGroupDef(ItemGroupDef itemGroupDef) {
		this.itemGroupDef = itemGroupDef;
	}
	@ManyToOne
	public ItemDef getItemDef() {
		return itemDef;
	}
	public void setItemDef(ItemDef itemDef) {
		this.itemDef = itemDef;
	}
	@ManyToOne
	public CodeList getRoleCodeList() {
		return roleCodeList;
	}
	public void setRoleCodeList(CodeList roleCodeList) {
		this.roleCodeList = roleCodeList;
	}
	public Long getKeySequence() {
		return keySequence;
	}
	public void setKeySequence(Long keySequence) {
		this.keySequence = keySequence;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}