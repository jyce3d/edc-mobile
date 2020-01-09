package be.sdlg.apps.edcmobile.svc;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.annotation.Description;

import be.sdlg.apps.edcmobile.model.ClinicalData;
import be.sdlg.apps.edcmobile.model.DBUser;
import be.sdlg.apps.edcmobile.model.Decode;
import be.sdlg.apps.edcmobile.model.FormData;
import be.sdlg.apps.edcmobile.model.FormDataAudit;
import be.sdlg.apps.edcmobile.model.FormDef;
import be.sdlg.apps.edcmobile.model.Location;
import be.sdlg.apps.edcmobile.model.MetadataVersion;
import be.sdlg.apps.edcmobile.model.Question;
import be.sdlg.apps.edcmobile.model.SignatureDef;
import be.sdlg.apps.edcmobile.model.Study;
import be.sdlg.apps.edcmobile.model.StudyEventData;
import be.sdlg.apps.edcmobile.model.StudyEventDef;
import be.sdlg.apps.edcmobile.model.SubjectData;

public interface EdcSvcIntf {
	public SubjectData findSubject(Long subjectId);
	public List<StudyEventData> findStudyEventDataBySubject(Long subjectId);

	public StudyEventData findStudyEventDataById(Long subjectEventDataId);
	public List<StudyEventData> findStudyEventDataBySubjectAndStudyEventDef(Long subjectId, Long studyEventDefId);
	public List<FormData> findFormDataBySubjectIdByFormDefId(Long studyEventDefId, Long formDefId, Long subjectId);
	public void saveFormData(MetadataVersion mv,
			Long subjectDataId, StudyEventDef studyEventDef, Long studyEventDataId, FormDef formDef, Long formDataId,
			Long status, String reasonForUpdate,  Map<String, String> itemsDef, Map<String, String> itemsDisp, Map<String, String> itemsDirty,  String fullName, SignatureDef signatureDef, DBUser user, Location location) throws Exception;
	public FormData findFormDataById(Long id);
	public StudyEventData createStudyEventData(MetadataVersion mv, Long subjectDataId, StudyEventDef studyEventDef, String studyEventRepeatKey, Date effectiveDate, Long active, 
			 String fullName) throws Exception;
	public FormData createFormData(MetadataVersion mv, StudyEventData studyEventData, FormDef formDef,
			String formRepeatKey, Long status, Long active, String fullName, FormDataAudit formDataAudit)
			throws Exception;
	public List<SubjectData> findSubjectDataList(ClinicalData clinicalData);

}

