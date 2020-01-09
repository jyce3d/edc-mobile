package be.sdlg.apps.edcmobile.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
@Table (name="STUDYEVENTS_REF")
@PrimaryKeyJoinColumn(name="CLINICAL_REF_ID")
public class StudyEventRef extends ClinicalRef {
	public Protocol protocol;
	public StudyEventDef studyEventDef; 
	public Long orderNumber;
	// Non CDISC
	protected Set<StudyEventPred> predecessorList; //Previous study event ref with a link on studyeventdef
	protected Long gapWithPredecessor;
	protected Long minTolerance;
	protected Long maxTolerance;
	protected Boolean visited;
	
	
	@ManyToOne
	public StudyEventDef getStudyEventDef() {
		return studyEventDef;
	}

	public void setStudyEventDef(StudyEventDef studyEventDef) {
		this.studyEventDef = studyEventDef;
	}

	@ManyToOne
	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="studyEventRef", fetch=FetchType.LAZY)
	public Set<StudyEventPred> getPredecessorList() {
		if (predecessorList == null) predecessorList = new HashSet<StudyEventPred>(0);
		return predecessorList;
	}
	public void setPredecessorList(Set<StudyEventPred> predecessorList) {
		this.predecessorList = predecessorList;
	}
	@Column(name="GAP_WITH_PREV")
	public Long getGapWithPredecessor() {
		return gapWithPredecessor;
	}
	public void setGapWithPredecessor(Long gapWithPredecessor) {
		this.gapWithPredecessor = gapWithPredecessor;
	}
	@Column(name="MIN_TOL")
	public Long getMinTolerance() {
		return minTolerance;
	}

	public void setMinTolerance(Long minTolerance) {
		this.minTolerance = minTolerance;
	}
	@Column(name="MAX_TOL")
	public Long getMaxTolerance() {
		return maxTolerance;
	}

	public void setMaxTolerance(Long maxTolerance) {
		this.maxTolerance = maxTolerance;
	}

	public Boolean getVisited() {
		if (visited==null) visited=false;
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}


	
}