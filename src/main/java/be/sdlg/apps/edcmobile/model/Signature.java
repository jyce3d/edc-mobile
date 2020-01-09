package be.sdlg.apps.edcmobile.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name="SIGNATURES")
public class Signature {
	public static final int SIGNED = 1;
	public static final int REVOKED = 2;

	protected Long id;
	protected Date dateTimeStamp;
	protected SignatureDef signatureDef;
	protected DBUser userRef;
	protected Location locationRef;
	protected ElectronicRecord electronicRecord;
	// outside CDISC
	protected Long status;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SIGNATURE_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Temporal(TemporalType.DATE)
	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}
	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
	@ManyToOne
	public SignatureDef getSignatureDef() {
		return signatureDef;
	}
	public void setSignatureDef(SignatureDef signatureDef) {
		this.signatureDef = signatureDef;
	}
	@ManyToOne
	public DBUser getUserRef() {
		return userRef;
	}
	public void setUserRef(DBUser userRef) {
		this.userRef = userRef;
	}
	@ManyToOne
	public Location getLocationRef() {
		return locationRef;
	}
	public void setLocationRef(Location locationRef) {
		this.locationRef = locationRef;
	}
	
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	@ManyToOne
	public ElectronicRecord getElectronicRecord() {
		return electronicRecord;
	}
	public void setElectronicRecord(ElectronicRecord electronicRecord) {
		this.electronicRecord = electronicRecord;
	}
	
	
	
	
}