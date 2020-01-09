package be.sdlg.apps.edcmobile.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.sdlg.apps.edcmobile.model.StudyEventData;
public interface StudyEventDataRepository  extends CrudRepository<StudyEventData, Long> {
	@Query("SELECT sed from be.sdlg.apps.edcmobile.model.StudyEventData sed JOIN sed.subjectData sd where sd.id = :subjectDataId")
	public List<StudyEventData> findStudyEventDataBySubjectId(@Param("subjectDataId") Long subjectDataId);
	
	@Query("SELECT sed from be.sdlg.apps.edcmobile.model.StudyEventData sed JOIN sed.subjectData sd where sd.id = :subjectDataId and sed.studyEventDef.id= :studyEventDefId")
	public List<StudyEventData> findStudyEventDataBySubjectIdAndStudyEventId(@Param("subjectDataId") Long subjectDataId, @Param("studyEventDefId") Long studyEventDefId);
}
