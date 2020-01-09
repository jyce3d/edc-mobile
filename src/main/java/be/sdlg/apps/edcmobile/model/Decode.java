package be.sdlg.apps.edcmobile.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table (name="DECODE")
@PrimaryKeyJoinColumn(name="TRANSLATABLE_ID")
public class Decode extends Translatable {
	protected CodeListItem codeListItem;
	@ManyToOne
	public CodeListItem getCodeListItem() {
		return codeListItem;
	}

	public void setCodeListItem(CodeListItem codeListItem) {
		this.codeListItem = codeListItem;
	}
	

}