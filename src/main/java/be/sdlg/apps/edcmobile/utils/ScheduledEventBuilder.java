package be.sdlg.apps.edcmobile.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.sdlg.apps.edcmobile.model.Protocol;
import be.sdlg.apps.edcmobile.model.StudyEventData;
import be.sdlg.apps.edcmobile.model.StudyEventPred;
import be.sdlg.apps.edcmobile.model.StudyEventRef;

public class ScheduledEventBuilder {
	private void recurDate(ScheduledEvent curSe, Date calculatedDate, Map<Long, StudyEventData> studyEventDataList) {
		Date maxDate = null;
		Date currentDate = null;
		Calendar c = Calendar.getInstance();
		int days;
		Date effectiveDate;
		if (! curSe.isVisited()){
			curSe.setVisited(true);

			for (ScheduledEvent se : curSe.getNextEvents()) {
				se.setStudyEventData(studyEventDataList.get(se.getStudyEventRef().getStudyEventDef().getId()));
				if (se.getStudyEventData() !=null && se.getStudyEventData().getEffectiveDate()!=null)
				effectiveDate = se.getStudyEventData().getEffectiveDate();
					else effectiveDate=calculatedDate;
				days = Integer.valueOf( se.getStudyEventRef().getGapWithPredecessor().toString());
				c.setTime(effectiveDate);
				c.add(Calendar.DATE, days  );
				currentDate = c.getTime();
				se.setScheduledDate(currentDate);		
				if (maxDate == null) maxDate = se.getScheduledDate();
				else if (maxDate.before(se.getScheduledDate())) //TODO : check if compare ref or value
					maxDate = se.getScheduledDate();
			}
			for (ScheduledEvent se : curSe.getNextEvents()) 
				recurDate(se, maxDate,studyEventDataList);
		}
		
	}
	private void recurBuild(Protocol protocol, ScheduledEvent sePrev) {
		for (StudyEventRef sr : protocol.getStudyEventRefList()) {
			for (StudyEventPred p : sr.getPredecessorList()) {
				if (sePrev.getStudyEventRef().getId() == p.getPredecessorId()) {
					sr.setVisited(true);
					ScheduledEvent se =new ScheduledEvent();
					se.setStudyEventRef(sr);
					se.getPreviousEvents().add(se);
					sePrev.getNextEvents().add(se);
					recurBuild(protocol, se);
				}
			}
		}
	}
	public Set<ScheduledEvent> build(Protocol protocol, Date baseline, Set<StudyEventData> sedList) {
		Set<ScheduledEvent> rootEvents = new HashSet<ScheduledEvent>(0);
// Define the graph
		for (StudyEventRef sr : protocol.getStudyEventRefList()) {
			if (sr.getVisited()==false && sr.getPredecessorList().size()> 0) {
				StudyEventPred pred = (StudyEventPred) sr.getPredecessorList().iterator().next();
				if (pred.getPredecessorId() == null ) {
					sr.setVisited(true);
					ScheduledEvent se = new ScheduledEvent();
					se.setStudyEventRef(sr);
					rootEvents.add(se);
					recurBuild(protocol, se);
				}
			}
				
		}
		// retrieve the studyevent data for a given subject
		// construct the hashmap
		Map<Long, StudyEventData> studyEventDataList = new HashMap<Long,StudyEventData>(); 
		for (StudyEventData sed : sedList) 
			studyEventDataList.put(sed.getStudyEventDef().getId(), sed);
			
		
// calculate dates
		for (ScheduledEvent se : rootEvents) {
			se.setScheduledDate(baseline);
			se.setStudyEventData(studyEventDataList.get(se.getStudyEventRef().getStudyEventDef().getId()));
			recurDate(se, baseline,  studyEventDataList);
		}
		resetGraph(rootEvents);
		return rootEvents;
		
	}
	public void resetGraph(Set<ScheduledEvent> seList) {
		for (ScheduledEvent se : seList) {
			se.setVisited(false);
			resetGraph(se.getNextEvents());
		}

	}
}