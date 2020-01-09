package be.sdlg.apps.edcmobile.svc;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.sdlg.apps.edcmobile.data.ClinicalDataRepository;
import be.sdlg.apps.edcmobile.data.MetadataVersionRepository;
import be.sdlg.apps.edcmobile.data.SignatureDefRepository;
import be.sdlg.apps.edcmobile.data.StudyRepository;
import be.sdlg.apps.edcmobile.model.AdminData;
import be.sdlg.apps.edcmobile.model.ClinicalData;
import be.sdlg.apps.edcmobile.model.CodeList;
import be.sdlg.apps.edcmobile.model.CodeListItem;
import be.sdlg.apps.edcmobile.model.Decode;
import be.sdlg.apps.edcmobile.model.Description;
import be.sdlg.apps.edcmobile.model.FormDef;
import be.sdlg.apps.edcmobile.model.FormRef;
import be.sdlg.apps.edcmobile.model.ItemDef;
import be.sdlg.apps.edcmobile.model.ItemGroupDef;
import be.sdlg.apps.edcmobile.model.ItemGroupRef;
import be.sdlg.apps.edcmobile.model.ItemRef;
import be.sdlg.apps.edcmobile.model.MetadataVersion;
import be.sdlg.apps.edcmobile.model.Protocol;
import be.sdlg.apps.edcmobile.model.Question;
import be.sdlg.apps.edcmobile.model.SignatureDef;
import be.sdlg.apps.edcmobile.model.Study;
import be.sdlg.apps.edcmobile.model.StudyEventData;
import be.sdlg.apps.edcmobile.model.StudyEventDef;
import be.sdlg.apps.edcmobile.model.StudyEventPred;
import be.sdlg.apps.edcmobile.model.StudyEventRef;
import be.sdlg.apps.edcmobile.model.SubjectData;
import be.sdlg.apps.edcmobile.model.TranslatedText;

@Service
public class StudyDesignSvc implements StudyDesignSvcIntf {
	@Autowired
	private StudyRepository studyDao;
	@Autowired
	private ClinicalDataRepository clinicalDataDao;
	@Autowired
	private MetadataVersionRepository metadataVersionDao;
	@Autowired
	private SignatureDefRepository signatureDefDao;


	
	public Study findStudy(Long id) {
		Study s2;
		Optional<Study> s = studyDao.findById(id);
		if (s.isPresent()) s2=s.get(); else s2=null;
		return s2;
	}

	public void saveStudy(Study study) {
		studyDao.save(study);
		
	}


	public Set<Study> getStudyList() {
		// TODO Auto-generated method stub
		Set<Study> studyList = new HashSet<Study>(0);
		studyDao.findAll().forEach(i -> studyList.add(i));
		return studyList;
	}
	private String getDefaultDecode(Decode decode) {
		Locale locale = new Locale("en");
		return getDecode(decode, locale);
		
	}
	
	public String getDecode( Decode decode, Locale locale) {
		if (locale == null) 
			return getDefaultDecode(decode);
	
		
		for (TranslatedText tt : decode.getTranslatedTextList()) {
			if (tt.getLanguageTag().contains(locale.getLanguage())) {
				
				return tt.getTranslatedText();
			}
		}
		if (locale.getLanguage().contains("en"))
			return null;
		else return getDefaultDecode(decode);
		
	}
	private String getDefaultDescription(Description description) {
		Locale locale = new Locale("en");
		return getDescription(description, locale);
	}
	public String getDescription(Description description, Locale locale) {
		if (description==null) return null;
		if (locale == null) 
			return getDefaultDescription(description);
	
		
		for (TranslatedText tt : description.getTranslatedTextList()) {
			if (tt.getLanguageTag().contains(locale.getLanguage())) {
				
				return tt.getTranslatedText();
			}
		}
		if (locale.getLanguage().contains("en"))
			return null;
		else return getDefaultDescription(description);
	}
	private String getDefaultQuestion(Question question) {
		Locale locale = new Locale("en");
		return getQuestion(question, locale);
	}
	public String getQuestion(Question question, Locale locale) {
		if (question == null) return null;
		if (locale == null) 
			return getDefaultQuestion(question);
	
		
		for (TranslatedText tt : question.getTranslatedTextList()) {
			if (tt.getLanguageTag().contains(locale.getLanguage())) {
				
				return tt.getTranslatedText();
			}
		}
		if (locale.getLanguage().contains("en"))
			return null;
		else return getDefaultQuestion(question);
	}


	public Description createDescription(String text, Locale locale) {
		Description descr = new Description();
		TranslatedText tt = new TranslatedText();
		tt.setLanguageTag(locale.getLanguage());
		tt.setTranslatedText(text);
		tt.setDecodable(descr);
		
		descr.getTranslatedTextList().add(tt);
		return descr;
	}
	
	public Question createQuestion(String text, Locale locale) {
		Question question = new Question();
		TranslatedText tt = new TranslatedText();
		tt.setLanguageTag(locale.getLanguage());
		tt.setTranslatedText(text);
		tt.setDecodable(question);
		
		question.getTranslatedTextList().add(tt);
		return question;
	}
	
	protected void addDecode(CodeListItem cli, String text, Locale locale) {
		Decode decode= new Decode();
		TranslatedText tt = new TranslatedText();
		tt.setLanguageTag(locale.getLanguage());
		tt.setTranslatedText(text);
		tt.setDecodable(decode);
		
		decode.getTranslatedTextList().add(tt);
		cli.setDecode(decode);
		decode.setCodeListItem(cli);
		
	}
	
	public MetadataVersion findByIdFromStudy(Study study, Long id) {
		for (MetadataVersion meta : study.getMetadataVersionList()) {
			if (meta.getId().equals(id)) {
				return meta;
			}
		}
		return null;
	}
	
	public StudyEventDef findByIdStudyEventDef(MetadataVersion mv, Long id) {
		for (StudyEventDef sd : mv.getStudyEventDefList()) {
			if (sd.getId().equals(id)) return sd;
		}
		return null;
	}
	
	public FormDef findByIdFormDef(MetadataVersion mv, Long id) {
		for (FormDef fd : mv.getFormDefList()) {
			if (fd.getId().equals(id)) return fd;
		}
		return null;
	}
	public ItemGroupDef findByIdItemGroupDef(MetadataVersion mv, Long id) {
		for (ItemGroupDef igd : mv.getItemGroupDefList()) {
			if (igd.getId().equals(id)) return igd;
		}
		return null;
	}
	public ItemDef findByIdItemDef(MetadataVersion mv, Long id) {
		for (ItemDef ids: mv.getItemDefList()) {
			if (ids.getId().equals(id)) return ids;
			
		}
		return null;
	}


	public void saveClinicalData(ClinicalData clinicalData) {
		clinicalDataDao.save(clinicalData);
		
	}
	public MetadataVersion findCurrentMetadataVersion(Study study) {
		Set<MetadataVersion> mvSet = study.getMetadataVersionList();
		List<MetadataVersion> mvList = new ArrayList<MetadataVersion>();
		for (MetadataVersion mv : mvSet)
			mvList.add(mv);
		mvList.sort(Comparator.comparing(MetadataVersion::getId));
		return mvList.get(0);
	}
	public MetadataVersion findCurrentMetadataVersion(Long studyId) {
		Set<MetadataVersion> metadataVersionList =  metadataVersionDao.find(studyId);
		if (metadataVersionList !=null)
			return  metadataVersionList.iterator().next();
		return null;
	}
	public SignatureDef findSignatureDefByStudyId(Long studyId, Long signatureUsage) {
		List<SignatureDef> sdList = signatureDefDao.findSignatureDefByStudyId(studyId, signatureUsage);
		if (sdList!=null)
			 return sdList.iterator().next();
		return null;
	}
	public SignatureDef createSignatureDef(Study study, String meaning,
			String legalReason, Long methodology, Long signatureUsage) throws Exception {
	 SignatureDef signatureDef = new SignatureDef();
	 try {

		 signatureDef.setLegalReason(legalReason);
		 signatureDef.setMeaning(meaning);
		 signatureDef.setMethodology(methodology);
		 signatureDef.setSignatureUsage(signatureUsage);
		 signatureDef.setAdminData(study.getAdminData());
		 study.getAdminData().getSignatureDefList().add(signatureDef);
		 study.setAdminData(signatureDef.getAdminData());;
		 signatureDefDao.save(signatureDef);
	 } catch (Exception e) {
		 signatureDef = null;
		 if (study==null) throw new Exception("StudyDesign:CreateSignatureDef : Study cannot be null");
		 else throw e;
	 }
	 return signatureDef;
	}
	
	public Study createStudy(String studyName, String studyDescription, String protocolName, String metadataVersionName, Long version, Date startDate) {
		Study study = new Study();
		study.setProtocolName(protocolName);
		study.setStudyName(studyName);
		study.setStudyDescription(studyDescription);
		study.setStudyStartDate(startDate);
		
		AdminData ad = new AdminData();
		study.setAdminData(ad);
		ad.setStudy(study);
		
		Protocol protocol = new Protocol();
		
		MetadataVersion metadatav = new MetadataVersion();
		metadatav.setProtocol(protocol);
		metadatav.setName(metadataVersionName);
		metadatav.setStudy(study);
		metadatav.setVersion(version);
		
		protocol.setMetadataVersion(metadatav);

		study.getMetadataVersionList().add(metadatav);

		return study;
	}
	public StudyEventDef createStudyEventDef(String name, String description, MetadataVersion mv, Long orderNumber, StudyEventPred predecessor, Locale locale) {
		StudyEventDef sd = new StudyEventDef();
		sd.setName(name);
		sd.setMetadataVersion(mv);
		mv.getStudyEventDefList().add(sd);
		if (locale == null)
			locale = new Locale("en");
		Description descr = createDescription(description, locale);
		sd.setDescription(descr);
		descr.setClinicalDef(sd);
		if (orderNumber!=null) {
			StudyEventRef studyEventRef = new StudyEventRef();
			studyEventRef.setOrderNumber(new Long(1));
			studyEventRef.setProtocol(mv.getProtocol());
			studyEventRef.setStudyEventDef(sd);
			mv.getProtocol().getStudyEventRefList().add(studyEventRef);
			
			StudyEventPred studyEventPred = new StudyEventPred();
			if (predecessor !=null) 
				studyEventPred.setPredecessorId(predecessor.getId());
			else
				studyEventPred.setPredecessorId(null);
			studyEventPred.setStudyEventRef(studyEventRef);
			studyEventRef.getPredecessorList().add(studyEventPred);

		}
		return sd;
	}
	public FormDef createFormDef(String name, String description, MetadataVersion mv, Long orderNumber, StudyEventDef studyEventDef, Locale locale) {
		FormDef fd = new FormDef();
		fd.setName(name);
		fd.setMetadataVersion(mv);
		mv.getFormDefList().add(fd);

		mv.getFormDefList().add(fd);
		if (locale == null)
			locale = new Locale("en");

		Description descr = createDescription(description, locale);
		fd.setDescription(descr);
		descr.setClinicalDef(fd);
		
		if (studyEventDef !=null) {
			FormRef formRef = new FormRef();
			formRef.setFormDef(fd);
			formRef.setOrderNumber(orderNumber);
			formRef.setStudyEventDef(studyEventDef);
			studyEventDef.getFormRefList().add(formRef);
		}
		
		return fd;
	}
	public ItemGroupDef createItemGroupDef(String domain, String name, String sasName, String description, MetadataVersion mv, Long orderNumber, FormDef formDef, Locale locale) {
		ItemGroupDef igd = new ItemGroupDef();
		igd.setDomain(domain);
		igd.setSasDatasetName(sasName);
		igd.setName(name);
		igd.setMetadataVersion(mv);
		mv.getItemGroupDefList().add(igd);

		if (locale == null)
			locale = new Locale("en");

		Description descr = createDescription(description, locale);
		igd.setDescription(descr);
		descr.setClinicalDef(igd);
		
		if (formDef !=null) {
			ItemGroupRef itemGroupRef = new ItemGroupRef();
			itemGroupRef.setOrderNumber(orderNumber);
			itemGroupRef.setFormDef(formDef);
			itemGroupRef.setItemGroupDef(igd);
			formDef.getItemGroupRefList().add(itemGroupRef);
		}
		return igd;
	}
	public ItemDef createItemDef(String name, String sasName, String question, Long itemType, CodeList codeList, Long mandatory, Long length, MetadataVersion mv, Long orderNumber, ItemGroupDef itemGroupDef, Locale locale) {
		ItemDef id1 = new ItemDef();
		id1.setName(name);
		id1.setSasFieldName(sasName);
		id1.setDataType(itemType);
		id1.setMetadataVersion(mv);
		if (length !=null)
			id1.setLength(length);
		mv.getItemDefList().add(id1);
		if (locale == null)
			locale = new Locale("en");
		Question question1 = createQuestion(question, locale);
		id1.setQuestion(question1);
		question1.setItemDef(id1);
		id1.setCodeList(codeList);
		if (itemGroupDef !=null) {
			ItemRef itemRef1 = new ItemRef();
			itemRef1.setItemDef(id1);
			itemRef1.setItemGroupDef(itemGroupDef);
			itemRef1.setMandatory(mandatory);
			itemRef1.setOrderNumber(orderNumber);
			itemGroupDef.getItemRefList().add(itemRef1);
		}
		return id1;
	} 
	public void addCodeListItem(CodeList codeList, String codedValue, String decodeValue, Float rank, Locale locale ) {
		CodeListItem cli = null;
		if (locale == null)
			locale = new Locale("en");
		cli = new CodeListItem();
		cli.setCodeList(codeList);
		codeList.getCodeListItems().add(cli);
		cli.setCodedValue(codedValue);
		this.addDecode(cli, decodeValue, locale);
		cli.setRank(rank);
	}
	public CodeList createCodeList(String name, String sasFormatName, String description, MetadataVersion mv, Long itemType, Locale locale) {
	
		CodeList codeList = null;
		if (locale == null)
			locale = new Locale("en");
		  codeList = new CodeList();
		  codeList.setMetadataVersion(mv);
		  mv.getCodeListList().add(codeList);
		  codeList.setDataType(itemType);
		  Description descr= createDescription( description, locale);
		  codeList.setDescription(descr);
		  descr.setClinicalDef(codeList);
		  codeList.setName(name);
		  codeList.setSasFormatName(sasFormatName);
		return codeList;
	}

}
