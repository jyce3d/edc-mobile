package be.sdlg.apps.edcmobile.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="AUDIT_RECORD")
@Inheritance(strategy = InheritanceType.JOINED)

public class AuditRecord {
	//EditPoint
	public static final int Monitoring = 1;
	public static final int DataManagement = 2;
	public static final int DBAudit = 3;
	
	//UsedImputationMethod
	public static final int Yes = 1;
	public static final int No = 2;

	// Operations definition	
	public static final int CREATE_SUBJECT = 1;
	public static final int UPDATE_SUBJECT = 2;
	public static final int SIGN_SUBJECT = 3;
	public static final int CREATE_STUDYEVENT = 4;
	public static final int UPDATE_STUDYEVENT = 5;
	public static final int CREATE_FORM = 6;
	public static final int UPDATE_FORM = 7;
	public static final int CREATE_ITEMGROUP = 8;
	public static final int UPDATE_ITEMGROUP = 9;
	public static final int CREATE_ITEM = 10;
	public static final int UPDATE_ITEM = 11;
	
	protected Long id;
	protected Date dateTimeStamp;
	protected Long editPoint;
	protected String reasonForChange;
	protected String sourceId;
	protected String userName;
	protected String locationName;
	
	protected Long usedImputationMethod;
	protected ElectronicRecord electronicRecord;
	protected ClinicalData clinicalData;
	protected Long operation; // Non CDISC but indicates the operation type
	protected Long oldActive ;
	protected Long newActive ;
	
	protected String signator;
	protected String signLocation;
	protected Long signStatus;
	
	protected Date lastExportTime;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="AUDIT_RECORD_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}
	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
	@Temporal(TemporalType.TIMESTAMP)	
	public Date getLastExportTime() {
		return lastExportTime;
	}
	public void setLastExportTime(Date lastExportTime) {
		this.lastExportTime = lastExportTime;
	}
	public Long getEditPoint() {
		return editPoint;
	}
	public void setEditPoint(Long editPoint) {
		this.editPoint = editPoint;
	}
	public String getReasonForChange() {
		return reasonForChange;
	}
	public void setReasonForChange(String reasonForChange) {
		this.reasonForChange = reasonForChange;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Long getUsedImputationMethod() {
		return usedImputationMethod;
	}
	public void setUsedImputationMethod(Long usedImputationMethod) {
		this.usedImputationMethod = usedImputationMethod;
	}

	public Long getOperation() {
		return operation;
	}
	public void setOperation(Long operation) {
		this.operation = operation;
	}
	public Long getOldActive() {
		return oldActive;
	}
	public void setOldActive(Long oldActive) {
		this.oldActive = oldActive;
	}
	public Long getNewActive() {
		return newActive;
	}
	public void setNewActive(Long newActive) {
		this.newActive = newActive;
	}
	public String getSignator() {
		return signator;
	}
	public void setSignator(String signator) {
		this.signator = signator;
	}
	public String getSignLocation() {
		return signLocation;
	}
	public void setSignLocation(String signLocation) {
		this.signLocation = signLocation;
	}
	public Long getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Long signStatus) {
		this.signStatus = signStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	@ManyToOne
	public ElectronicRecord getElectronicRecord() {
		return electronicRecord;
	}
	public void setElectronicRecord(ElectronicRecord electronicRecord) {
		this.electronicRecord = electronicRecord;
	}
	@ManyToOne
	public ClinicalData getClinicalData() {
		return clinicalData;
	}
	public void setClinicalData(ClinicalData clinicalData) {
		this.clinicalData = clinicalData;
	}

	
	

	
	
}