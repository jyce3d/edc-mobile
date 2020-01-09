package be.sdlg.apps.edcmobile.model;


/***
 * This class is not a CDISC standard, it is a common base class for all elements packaged into a protocol.
 * 
 * Basically this covers entities having in common : aliases and description. 
 * Those elements are generally the XXXDef.
 */
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CLINICAL_DEF")
@Inheritance(strategy = InheritanceType.JOINED)
public class ClinicalDef {
	public static final int REPEATING_YES=1;
	public static final int REPEATING_NO=2;

	protected Long id;
	protected Set<Alias> aliasList;
	protected Description description;
	protected String name;
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CLINICAL_DEF_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="clinicalDef", fetch=FetchType.LAZY)
	public Set<Alias> getAliasList() {
		if (aliasList == null) aliasList = new HashSet<Alias>(0);
		return aliasList;
	}
	public void setAliasList(Set<Alias> aliasList) {
		this.aliasList = aliasList;
	}
	@OneToOne(cascade=CascadeType.ALL, mappedBy="clinicalDef", fetch=FetchType.LAZY)
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}
	@Basic
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}