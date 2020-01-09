package be.sdlg.apps.edcmobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHORITIES")
public class Authority {

	protected String authority;
	protected Long id;
	protected DBUser user;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="AUTHORITY_ID", unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "AUTHORITY")
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@ManyToOne
	public DBUser getUser() {
		return user;
	}
	public void setUser(DBUser user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Authority [authority=" + authority + ", id=" + id + ", user="
				+ user + "]";
	}

	
	
}