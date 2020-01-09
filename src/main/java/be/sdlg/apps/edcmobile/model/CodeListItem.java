package be.sdlg.apps.edcmobile.model;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CODELIST_ITEMS")
public class CodeListItem {
	protected Long id;
	protected Decode decode;
	protected String codedValue;
	protected Float rank;
	protected CodeList codeList;
	@ManyToOne
	public CodeList getCodeList() {
		return codeList;
	}
	public void setCodeList(CodeList codeList) {
		this.codeList = codeList;
	}
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="codeListItem", fetch=FetchType.LAZY)
	public Decode getDecode() {
		return decode;
	}
	public void setDecode(Decode decode) {
		this.decode = decode;
	}
	
	public String getCodedValue() {
		return codedValue;
	}
	public void setCodedValue(String codedValue) {
		this.codedValue = codedValue;
	}

	public Float getRank() {
		return rank;
	}
	public void setRank(Float rank) {
		this.rank = rank;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CODELIST_ITEM_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}