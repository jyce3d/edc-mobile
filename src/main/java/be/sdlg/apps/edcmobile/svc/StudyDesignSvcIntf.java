package be.sdlg.apps.edcmobile.svc;


import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import be.sdlg.apps.edcmobile.model.ClinicalData;
import be.sdlg.apps.edcmobile.model.CodeList;
import be.sdlg.apps.edcmobile.model.CodeListItem;
import be.sdlg.apps.edcmobile.model.Decode;
import be.sdlg.apps.edcmobile.model.Description;
import be.sdlg.apps.edcmobile.model.FormDef;
import be.sdlg.apps.edcmobile.model.ItemDef;
import be.sdlg.apps.edcmobile.model.ItemGroupDef;
import be.sdlg.apps.edcmobile.model.MetadataVersion;
import be.sdlg.apps.edcmobile.model.Protocol;
import be.sdlg.apps.edcmobile.model.Question;
import be.sdlg.apps.edcmobile.model.SignatureDef;
import be.sdlg.apps.edcmobile.model.Study;
import be.sdlg.apps.edcmobile.model.StudyEventDef;
import be.sdlg.apps.edcmobile.model.StudyEventPred;

public interface StudyDesignSvcIntf {
	public Study findStudy(Long id);
	public Set<Study> getStudyList();
	public void saveStudy(Study study);
	public String getDescription(Description description, Locale locale);
	public String getDecode( Decode decode, Locale locale);
	public String getQuestion(Question question, Locale locale);
	public Description createDescription(String text, Locale locale);
	public Question createQuestion(String text, Locale locale);
	public MetadataVersion findByIdFromStudy(Study study, Long id);
	public StudyEventDef findByIdStudyEventDef(MetadataVersion mv, Long id);
	public FormDef findByIdFormDef(MetadataVersion mv, Long id) ;
	public ItemGroupDef findByIdItemGroupDef(MetadataVersion mv, Long id) ;
	public ItemDef findByIdItemDef(MetadataVersion mv, Long id);
	public void saveClinicalData(ClinicalData clinicalData);
	public MetadataVersion findCurrentMetadataVersion(Long studyId);
	public SignatureDef findSignatureDefByStudyId(Long studyId, Long signatureUsage);
	public SignatureDef createSignatureDef(Study study, String meaning,
			String legalReason, Long methodology, Long signatureUsage) throws Exception;
	public Study createStudy(String studyName, String studyDescription, String protocolName, String metadataVersionName, Long version, Date startDate) ;
	public StudyEventDef createStudyEventDef(String name, String description, MetadataVersion mv, Long orderNumber, StudyEventPred predecessor, Locale locale);
	public FormDef createFormDef(String name, String description, MetadataVersion mv, Long orderNumber, StudyEventDef studyEventDef, Locale locale);
	public ItemGroupDef createItemGroupDef(String domain, String name, String sasName, String description, MetadataVersion mv, Long orderNumber, FormDef formDef, Locale locale);
	public ItemDef createItemDef(String name, String sasName, String question, Long itemType, CodeList codeList, Long mandatory, Long length, MetadataVersion mv, Long orderNumber, ItemGroupDef itemGroupDef, Locale locale);
	public CodeList createCodeList(String name, String sasFormatName, String description, MetadataVersion mv, Long itemType, Locale locale);
	public void addCodeListItem(CodeList codeList, String codedValue, String decodeValue, Float rank, Locale locale );
}
