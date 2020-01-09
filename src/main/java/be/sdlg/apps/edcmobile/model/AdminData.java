package be.sdlg.apps.edcmobile.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ADMIN_DATA")
@Inheritance(strategy = InheritanceType.JOINED)
public class AdminData {
	protected Long id;
	protected Study study;
	protected Set<Location> locationList; 
	protected Set<SignatureDef> signatureDefList;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ADMIN_DATA_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@OneToOne
	public Study getStudy() {
		return study;
	}
	public void setStudy(Study study) {
		this.study = study;
	}
	@OneToMany (cascade = CascadeType.ALL, mappedBy="adminData", fetch=FetchType.LAZY)
	public Set<Location> getLocationList() {
		if (locationList == null) locationList = new HashSet<Location>(0);
		return locationList;
	}
	public void setLocationList(Set<Location> locationList) {
		this.locationList = locationList;
	}
	@OneToMany (cascade = CascadeType.ALL, mappedBy="adminData", fetch=FetchType.LAZY)
	public Set<SignatureDef> getSignatureDefList() {
		if (signatureDefList == null) signatureDefList = new HashSet<SignatureDef>(0);
		return signatureDefList;
	}
	public void setSignatureDefList(Set<SignatureDef> signatureDefList) {
		this.signatureDefList = signatureDefList;
	}
	
	

}