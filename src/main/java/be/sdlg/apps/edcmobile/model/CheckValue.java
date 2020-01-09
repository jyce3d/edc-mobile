package be.sdlg.apps.edcmobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table (name="CHECK_VALUE")
public class CheckValue {
	Long id;
	String value;
	protected RangeCheck rangeCheck;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CHECK_VALUE_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@ManyToOne
	public RangeCheck getRangeCheck() {
		return rangeCheck;
	}
	public void setRangeCheck(RangeCheck rangeCheck) {
		this.rangeCheck = rangeCheck;
	}
	
	

}