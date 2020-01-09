package be.sdlg.apps.edcmobile.model;



import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="USERS")
public class User {
	protected Long id;
	protected DBUser dbUser;
	protected AdminData adminData;
	protected Set<UserToLocation> userToLocation;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public DBUser getDbUser() {
		return dbUser;
	}
	public void setDbUser(DBUser dbUser) {
		this.dbUser = dbUser;
	}
	@ManyToOne
	public AdminData getAdminData() {
		return adminData;
	}
	public void setAdminData(AdminData adminData) {
		this.adminData = adminData;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user", fetch=FetchType.LAZY)
	public Set<UserToLocation> getUserToLocation() {
		return userToLocation;
	}
	public void setUserToLocation(Set<UserToLocation> userToLocation) {
		this.userToLocation = userToLocation;
	}
	
}
