package be.sdlg.apps.edcmobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="ANNOTATIONS")
public class Annotation {
	public static final int Sponsor = 1;
	public static final int Site = 2;
	
	protected Long id;
	protected Long seqNum;
	protected String comment;
	protected Long sponsorOrSite;
	protected ElectronicRecord electronicRecord;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ANNOTATION_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="SEQ_NUM")
	public Long getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}
	@Column(name="COMMENT")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Column(name="SPONSOR_OR_SITE")
	public Long getSponsorOrSite() {
		return sponsorOrSite;
	}
	public void setSponsorOrSite(Long sponsorOrSite) {
		this.sponsorOrSite = sponsorOrSite;
	}
	@ManyToOne
	public ElectronicRecord getElectronicRecord() {
		return electronicRecord;
	}
	public void setElectronicRecord(ElectronicRecord electronicRecord) {
		this.electronicRecord = electronicRecord;
	}
	
}