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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SIGNATURES_DEF")

public class SignatureDef  {
	public static final int Digital =1;
	public static final int Electronic = 2;
	
	// SignatureUsage
	public static final int CRF_SIGNATURE=1;

	protected Long id;
	protected AdminData adminData;
	protected Long methodology;
	protected String meaning;
	protected String legalReason;
	// Non CDISC
	protected Long signatureUsage;

	protected Set<Signature> signatures;

	@Column(name ="METHODOLOGY")
	public Long getMethodology() {
		return methodology;
	}

	public void setMethodology(Long methodology) {
		this.methodology = methodology;
	}
	@Column(name="MEANING")
	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	@Column(name="LEGAL_REASON")
	public String getLegalReason() {
		return legalReason;
	}

	public void setLegalReason(String legalReason) {
		this.legalReason = legalReason;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="signatureDef", fetch=FetchType.LAZY)
	public Set<Signature> getSignatures() {
		if (signatures == null)
			signatures = new HashSet<Signature>(0);
		return signatures;
	}

	public void setSignatures(Set<Signature> signatures) {
		this.signatures = signatures;
	}

	public Long getSignatureUsage() {
		return signatureUsage;
	}

	public void setSignatureUsage(Long signatureUsage) {
		this.signatureUsage = signatureUsage;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SIGNATURE_DEF_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public AdminData getAdminData() {
		return adminData;
	}

	public void setAdminData(AdminData adminData) {
		this.adminData = adminData;
	}
	

	
	
}