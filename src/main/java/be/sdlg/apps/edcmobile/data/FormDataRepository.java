package be.sdlg.apps.edcmobile.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.sdlg.apps.edcmobile.model.FormData;

public interface FormDataRepository extends CrudRepository<FormData, Long>{
	@Query("SELECT fda from be.sdlg.apps.edcmobile.model.FormData fda JOIN fda.studyEventData sed JOIN "
			+ "		sed.subjectData sd "
			+ "where fda.formDef.id =:formDefId and sd.id= :subjectId and sed.studyEventDef.id= :studyEventDefId")
	List<FormData> findFormDataByStudyEventDataByStudyEventDefBySubject(@Param("studyEventDefId") Long studyEventDefId, @Param("formDefId") Long formDefId, 
			@Param("subjectId") Long subjectId);

}
