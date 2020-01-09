package be.sdlg.apps.edcmobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="LNK_USERS_LOCATIONS")
public class UserToLocation {
	protected Long id;
	protected User user;
	protected Location location;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LNK_USER_LOCATIONS_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
}
