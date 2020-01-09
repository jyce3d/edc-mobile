package be.sdlg.apps.edcmobile.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="RANGE_CHECKS")
public class RangeCheck {
	public static final int CMP_LT =1;
	public static final int CMP_LE =2;
	public static final int CMP_GT =3;
	public static final int CMP_GE =4;
	public static final int CMP_EQ =5;
	public static final int CMP_NE =6;
	public static final int CMP_IN =7;
	public static final int CMP_NOTIN =8;
	
	public static final int SH_SOFT = 1;
	public static final int SH_HAR = 2;
	
	protected Long id;
	protected Set<CheckValue> checkValue;
	protected Long comparator;
	protected Long softHard;
	protected MeasurementUnit measurementUnit;
	protected Set<ErrorMessage> errorMessage;
	protected ItemDef itemDef;
	@ManyToOne
	public ItemDef getItemDef() {
		return itemDef;
	}
	public void setItemDef(ItemDef itemDef) {
		this.itemDef = itemDef;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RANGE_CHECK_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public MeasurementUnit getMeasurementUnit() {
		return measurementUnit;
	}
	public void setMeasurementUnit(MeasurementUnit measurementUnit) {
		this.measurementUnit = measurementUnit;
	}
	public Long getComparator() {
		return comparator;
	}
	public void setComparator(Long comparator) {
		this.comparator = comparator;
	}
	public Long getSoftHard() {
		return softHard;
	}
	public void setSoftHard(Long softHard) {
		this.softHard = softHard;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="rangeCheck", fetch=FetchType.LAZY)
	public Set<ErrorMessage> getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(Set<ErrorMessage> errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}