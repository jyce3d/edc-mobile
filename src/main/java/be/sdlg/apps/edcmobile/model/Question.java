package be.sdlg.apps.edcmobile.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="QUESTIONS")
@PrimaryKeyJoinColumn(name="TRANSLATABLE_ID")
public class Question extends Translatable {
	protected ItemDef itemDef;
	@OneToOne
	public ItemDef getItemDef() {
		return itemDef;
	}

	public void setItemDef(ItemDef itemDef) {
		this.itemDef = itemDef;
	}
	

}