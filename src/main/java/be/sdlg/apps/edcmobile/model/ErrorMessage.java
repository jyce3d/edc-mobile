package be.sdlg.apps.edcmobile.model;



import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="ERROR_MESSAGES")
@PrimaryKeyJoinColumn(name="TRANSLATABLE_ID")
public class ErrorMessage extends Translatable {
	protected RangeCheck rangeCheck;
	@ManyToOne
	public RangeCheck getRangeCheck() {
		return rangeCheck;
	}

	public void setRangeCheck(RangeCheck rangeCheck) {
		this.rangeCheck = rangeCheck;
	}
	
}