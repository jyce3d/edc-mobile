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
@Table (name="METADATAVERSION_REF")
public class MetadataVersionRef {
	protected Long id;
	protected Location location;
	protected Study study;
	protected MetadataVersion metadataVersion;
	protected Date effectiveDate;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="METADATAVERSION_REF_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	@ManyToOne
	public Study getStudy() {
		return study;
	}
	public void setStudy(Study study) {
		this.study = study;
	}
	@ManyToOne
	public MetadataVersion getMetadataVersion() {
		return metadataVersion;
	}
	public void setMetadataVersion(MetadataVersion metadataVersion) {
		this.metadataVersion = metadataVersion;
	}
	@Temporal(TemporalType.DATE)
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	
}
