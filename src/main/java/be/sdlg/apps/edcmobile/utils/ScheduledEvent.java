package be.sdlg.apps.edcmobile.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import be.sdlg.apps.edcmobile.model.StudyEventData;
import be.sdlg.apps.edcmobile.model.StudyEventRef;

public class ScheduledEvent {
	protected Date scheduledDate;
	protected StudyEventRef studyEventRef;
	protected Set<ScheduledEvent> previousEvents;
	protected Set<ScheduledEvent> nextEvents;
	protected StudyEventData studyEventData;
	protected boolean visited;
	
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public Date getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public StudyEventRef getStudyEventRef() {
		return studyEventRef;
	}
	public void setStudyEventRef(StudyEventRef studyEventRef) {
		this.studyEventRef = studyEventRef;
	}
	public Set<ScheduledEvent> getPreviousEvents() {
		if (previousEvents == null) previousEvents = new HashSet<ScheduledEvent>(0);
		return previousEvents;
	}
	public void setPreviousEvents(Set<ScheduledEvent> previousEvents) {
		this.previousEvents = previousEvents;
	}
	public Set<ScheduledEvent> getNextEvents() {
		if (nextEvents == null) nextEvents = new HashSet<ScheduledEvent>(0);
		return nextEvents;
	}
	public void setNextEvents(Set<ScheduledEvent> nextEvents) {
		this.nextEvents = nextEvents;
	}
	public StudyEventData getStudyEventData() {
		return studyEventData;
	}
	public void setStudyEventData(StudyEventData studyEventData) {
		this.studyEventData = studyEventData;
	}
	
	
}

