package be.sdlg.apps.edcmobile.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CLINICAL_REF")
@Inheritance(strategy = InheritanceType.JOINED)
public class ClinicalRef {
	public static final int MANDATORY_YES =1;
	public static final int MANDATORY_NO =2;
	
	protected Long id;
	protected Long orderNumber;
	protected Long mandatory;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CLINICAL_REF_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Basic
	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	@Basic
	public Long getMandatory() {
		return mandatory;
	}

	public void setMandatory(Long mandatory) {
		this.mandatory = mandatory;
	}
	
	

}