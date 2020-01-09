package be.sdlg.apps.edcmobile.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="DB_LOCATIONS")
public class DBLocation {
	protected Long id;
	protected String name;
	protected Set<Location> locationList;
	@OneToMany (cascade = CascadeType.ALL, mappedBy="dbLocation", fetch=FetchType.LAZY)
	public Set<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(Set<Location> locationList) {
		this.locationList = locationList;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DB_LOCATION_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
