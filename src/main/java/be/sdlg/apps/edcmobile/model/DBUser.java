package be.sdlg.apps.edcmobile.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;


@Entity
@Table (name="DB_USERS")
public class DBUser {
	protected Long id;
	protected String name;
	protected String password;
	protected String signature;
	protected boolean enabled;
	protected String userMail;
	protected Date lastLoginDate;
	protected String firstName;
	protected String lastName;
	
	protected Date creationDate;
	protected Set<Authority> authorityList;
	protected Set<User> studyUserList;
	protected Set<SubjectData> subjectDataList;


	
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="ENABLED")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Column(name="USER_MAIL", nullable=false, length=25)
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATION_DATE", nullable=false)
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user", orphanRemoval=true, fetch=FetchType.LAZY)
	public Set<Authority> getAuthorityList() {
		if (authorityList==null) authorityList=new HashSet<Authority>(0);
		return authorityList;
	}
	public void setAuthorityList(Set<Authority> authorityList) {
		this.authorityList = authorityList;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="investigatorRef", fetch=FetchType.LAZY)
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
	@Column(name="USER_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DBUser [name=" + name + "]";
	}
	@Basic
	@Column(name="USER_NAME", unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@OneToMany (cascade=CascadeType.ALL, mappedBy="dbUser", fetch=FetchType.LAZY)
	public Set<User> getStudyUserList() {
		if (studyUserList==null) studyUserList= new HashSet<User>(0);
		return studyUserList;
	}

	public void setStudyUserList(Set<User> studyUserList) {
		this.studyUserList = studyUserList;
	}
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	


}