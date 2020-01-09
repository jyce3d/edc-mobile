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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table (name="LOCATIONS")
public class Location {
//	(Sponsor | Site | CRO | Lab | Other)
	public static final int LOCATION_TYPE_SPONSOR = 1;
	public static final int LOCATION_TYPE_SITE = 2;
	public static final int LOCATION_TYPE_CRO =3;
	public static final int LOCATION_TYPE_LAB = 4;
	public static final int LOCATION_TYPE_OTHER = 5;
	
	protected Long id;
	protected DBLocation dbLocation;
	protected Long locationType;
	protected Set<UserToLocation> userToLocationList;
	protected Set<MetadataVersionRef> metadataVersionRefList;
	protected Set<SubjectData> subjectDataList;
	protected AdminData adminData;

	
	// Non CDISC data
	protected String shortName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="siteRef", fetch=FetchType.LAZY)
	public Set<SubjectData> getSubjectDataList() {
		if (subjectDataList == null)
			subjectDataList = new HashSet<SubjectData>(0);
		return subjectDataList;
	}
	public void setSubjectDataList(Set<SubjectData> subjectDataList) {
		this.subjectDataList = subjectDataList;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LOCATION_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getLocationType() {
		return locationType;
	}
	public void setLocationType(Long locationType) {
		this.locationType = locationType;
	}
	
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@OneToMany (cascade = CascadeType.ALL, mappedBy="location", fetch=FetchType.LAZY)
	public Set<MetadataVersionRef> getMetadataVersionRefList() {
		if (metadataVersionRefList ==null) metadataVersionRefList = new HashSet<MetadataVersionRef>(0);
		return metadataVersionRefList;
	}
	public void setMetadataVersionRefList(Set<MetadataVersionRef> metadataVersionRefList) {
		this.metadataVersionRefList = metadataVersionRefList;
	}
	
	@ManyToOne
	public DBLocation getDbLocation() {
		return dbLocation;
	}
	public void setDbLocation(DBLocation dbLocation) {
		this.dbLocation = dbLocation;
	}
	@OneToMany (cascade = CascadeType.ALL, mappedBy="location", fetch=FetchType.LAZY)
	public Set<UserToLocation> getUserToLocationList() {
		if (userToLocationList==null) userToLocationList= new HashSet<UserToLocation>(0);
		return userToLocationList;
	}
	public void setUserToLocationList(Set<UserToLocation> userToLocationList) {
		this.userToLocationList = userToLocationList;
	}
	@ManyToOne
	public AdminData getAdminData() {
		return adminData;
	}
	public void setAdminData(AdminData adminData) {
		this.adminData = adminData;
	}
	
	
	
	
	

}