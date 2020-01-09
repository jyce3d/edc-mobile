package be.sdlg.apps.edcmobile.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.sdlg.apps.edcmobile.model.AdminData;
import be.sdlg.apps.edcmobile.model.ClinicalData;
import be.sdlg.apps.edcmobile.model.ClinicalRef;
import be.sdlg.apps.edcmobile.model.CodeList;
import be.sdlg.apps.edcmobile.model.Description;
import be.sdlg.apps.edcmobile.model.FormData;
import be.sdlg.apps.edcmobile.model.FormDef;
import be.sdlg.apps.edcmobile.model.FormRef;
import be.sdlg.apps.edcmobile.model.ItemData;
import be.sdlg.apps.edcmobile.model.ItemDef;
import be.sdlg.apps.edcmobile.model.ItemGroupData;
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
import be.sdlg.apps.edcmobile.model.User;
import be.sdlg.apps.edcmobile.svc.CDISCSvc;
import be.sdlg.apps.edcmobile.svc.EdcSvc;
import be.sdlg.apps.edcmobile.svc.StudyDesignSvc;
import be.sdlg.apps.edcmobile.utils.ScheduledEvent;
import be.sdlg.apps.edcmobile.utils.ScheduledEventBuilder;
import be.sdlg.apps.edcmobile.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	private static final org.slf4j.Logger log =
		    org.slf4j.LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private StudyDesignSvc studyDesignSvc;
	@Autowired
	private EdcSvc edcSvc;
	@Autowired
	private CDISCSvc cdiscSvc;
	
	public class FormDataDescr {
		protected String formName;
		protected Long status;
		protected Long formDefId;
		protected Long order;
		public String getFormName() {
			return formName;
		}
		public void setFormName(String formName) {
			this.formName = formName;
		}
		public Long getStatus() {
			return status;
		}
		public void setStatus(Long status) {
			this.status = status;
		}
		public Long getFormDefId() {
			return formDefId;
		}
		public void setFormDefId(Long formDefId) {
			this.formDefId = formDefId;
		}
		public Long getOrder() {
			return order;
		}
		public void setOrder(Long order) {
			this.order = order;
		}
	
		
	}
	public class SubjectEventDataDisplay {
		protected String studyEventName;
		protected String studyEventDescr;
		protected String effectiveDate;
		protected String scheduledDate;
		protected String minTolerance;
		protected String maxTolerance;
		protected Date orderDate;
		protected Long studyEventDefId;
		
		protected List<FormDataDescr> formDescrList;
		
		public Date getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(Date orderDate) {
			this.orderDate = orderDate;
		}

		public SubjectEventDataDisplay(ScheduledEvent se, Locale locale) {
			this.setStudyEventName(se.getStudyEventRef().getStudyEventDef().getName());
			this.setScheduledDate(Utils.getFormattedDate(se.getScheduledDate(), locale, false));
			this.setStudyEventDefId(se.getStudyEventRef().getStudyEventDef().getId());
			if (se.getStudyEventRef().getMinTolerance() !=null)
				this.setMinTolerance(se.getStudyEventRef().getMinTolerance().toString());
			if (se.getStudyEventRef().getMaxTolerance() !=null)
			this.setMaxTolerance(se.getStudyEventRef().getMaxTolerance().toString());
			//String descr = administrationService.getDescription(se.getStudyEventRef().getStudyEventDef().getDescription(), locale);
			this.setStudyEventDescr(se.getStudyEventRef().studyEventDef.getName());
			if (se.getStudyEventData() != null && se.getStudyEventData().getEffectiveDate() !=null) {
				this.setEffectiveDate(Utils.getFormattedDate(se.getStudyEventData().getEffectiveDate(), locale, false));
				this.orderDate = se.getStudyEventData().getEffectiveDate();
			}
			else {
				this.orderDate = se.getScheduledDate();
				this.setEffectiveDate("NAV");
			}
			formDescrList = new ArrayList<FormDataDescr>();
			for (FormRef fr : se.getStudyEventRef().getStudyEventDef().getFormRefList()) {
				FormDataDescr fdd = new FormDataDescr();
				fdd.setFormName(fr.getFormDef().getName());
				fdd.setFormDefId(fr.getFormDef().getId());
				fdd.setOrder(fr.getOrderNumber());
				if (se.getStudyEventData() != null && se.getStudyEventData().getFormDataList() !=null ) {
					for (FormData fd : se.getStudyEventData().getFormDataList()) {
						if (fd.getFormDef().getId().equals(fr.getFormDef().getId()))
							fdd.setStatus(fd.getStatus());
					}
				}
				formDescrList.add(fdd);
			}
			Collections.sort(formDescrList, new Comparator<FormDataDescr>() {
				public int compare(FormDataDescr f1, FormDataDescr f2) {
					if (f1.order == f2.order) return 0;
					return f1.order< f2.order ? -1 : 1;
				}
			});
		}
		public String getStudyEventName() {
			return studyEventName;
		}

		public void setStudyEventName(String studyEventName) {
			this.studyEventName = studyEventName;
		}

	

		public String getEffectiveDate() {
			return effectiveDate;
		}

		public void setEffectiveDate(String effectiveDate) {
			this.effectiveDate = effectiveDate;
		}

		public String getScheduledDate() {
			return scheduledDate;
		}

		public void setScheduledDate(String scheduledDate) {
			this.scheduledDate = scheduledDate;
		}

		public List<FormDataDescr> getFormDescrList() {
			return formDescrList;
		}

		public void setFormDescrList(List<FormDataDescr> formDescrList) {
			this.formDescrList = formDescrList;
		}

		public Long getStudyEventDefId() {
			return studyEventDefId;
		}

		public void setStudyEventDefId(Long studyEventDefId) {
			this.studyEventDefId = studyEventDefId;
		}

		public String getMinTolerance() {
			return minTolerance;
		}

		public void setMinTolerance(String minTolerance) {
			this.minTolerance = minTolerance;
		}

		public String getMaxTolerance() {
			return maxTolerance;
		}

		public void setMaxTolerance(String maxTolerance) {
			this.maxTolerance = maxTolerance;
		}

		public String getStudyEventDescr() {
			return studyEventDescr;
		}

		public void setStudyEventDescr(String studyEventDescr) {
			this.studyEventDescr = studyEventDescr;
		}

	}

	@GetMapping("/")
	public String home(Model model) {
		Set<Study> studyList =  studyDesignSvc.getStudyList();
		model.addAttribute("studyList", studyList);
		return "studyList";
	}
	private void recurStudyEvents(ScheduledEvent curSe,List<SubjectEventDataDisplay> responseObject, Locale locale) {
		for (ScheduledEvent se : curSe.getNextEvents() )
			if (se.isVisited() == false) {
				se.setVisited(true);
				SubjectEventDataDisplay sedd = new SubjectEventDataDisplay(se, locale);
				responseObject.add(sedd);				
				recurStudyEvents(se, responseObject, locale);
			}
	}
	
	@GetMapping("/studyEventList")
	public String studyEventList(@RequestParam(value="subjectDataId", required=true) Long subjectDataId, 
			@RequestParam(value="studyId", required=true) Long studyId, Locale locale, Model model) {
		
		MetadataVersion mv = studyDesignSvc.findCurrentMetadataVersion(studyId);
		SubjectData sd = edcSvc.findSubject(subjectDataId);
		
		List<StudyEventData> studyEventDataList = edcSvc.findStudyEventDataBySubject(sd.getId());
		Set<StudyEventData> studyEventDataSet = new HashSet<StudyEventData>(0);
		for (StudyEventData sed : studyEventDataList)
			studyEventDataSet.add(sed);
		ScheduledEventBuilder scheduledEventBuilder = new ScheduledEventBuilder();
		//TODO: add a check on the Status Enrolled otherwise refuse to compute the visit Plan.
		Set<ScheduledEvent> scheduledEvents = scheduledEventBuilder.build(mv.getProtocol(), sd.getStatusDate(), studyEventDataSet);
		
		// Prepare responseObject
		
		List<SubjectEventDataDisplay> responseObject = new ArrayList<SubjectEventDataDisplay>();
		for (ScheduledEvent se: scheduledEvents) {
			SubjectEventDataDisplay sedd = new SubjectEventDataDisplay(se, locale);
			responseObject.add(sedd);
			se.setVisited(true);
			recurStudyEvents(se, responseObject, locale);
		}
		Collections.sort(responseObject, new Comparator<SubjectEventDataDisplay>() {
			public int compare(SubjectEventDataDisplay s1, SubjectEventDataDisplay s2) {
				if (s1.getOrderDate() == s2.getOrderDate()) return 0;
				return s1.getOrderDate().before(s2.getOrderDate()) ? -1 : 1;
			}
		});
		model.addAttribute("studyEventList", responseObject);
		model.addAttribute("subjectData", sd);
		return "studyEventList";
	}
	@GetMapping("/subjectDataList") 
	public String subjectDataList(@RequestParam(value="studyId", required=false) Long studyId, Model model, HttpServletRequest request) {
		//Study study = studyDesignSvc.getStudy(studyId);
		MetadataVersion mv = studyDesignSvc.findCurrentMetadataVersion(studyId);
		request.getSession().setAttribute("studyId", studyId);
		List<SubjectData> subjectDataList = edcSvc.findSubjectDataList(mv.getClinicalData());
		model.addAttribute("subjectDataList", subjectDataList);
		model.addAttribute("studyId", studyId);
		
		return "subjectDataList";
	}
	@GetMapping("/exportCDISC")
	public void exportCDISC(@RequestParam(value="studyId", required=true) Long studyId, HttpServletResponse response) {
		Study study = studyDesignSvc.findStudy(studyId);
		MetadataVersion mv = studyDesignSvc.findCurrentMetadataVersion(study);
		Locale locale = new Locale("en");
		List<SubjectData> subjectDataList = edcSvc.findSubjectDataList(mv.getClinicalData());
		UUID uuid = UUID.randomUUID();
		Document cdiscOdm = cdiscSvc.createODM("Snapshot", "file-"+uuid.toString(), new Date(), study, mv, subjectDataList, locale);
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment; filename='file-"+ uuid.toString()  + "';");
	
        response.setHeader("Cache-Control", "no-cache");         
		//response.setHeader("Content-Disposition", "attachment;filename=thisIsTheFileName.xml");
		try {
			ServletOutputStream outStream = response.getOutputStream();
			XMLOutputter outXml = new XMLOutputter();
			//System.out.println(outXml.outputString(cdiscOdm));
			
			outStream.println(outXml.outputString(cdiscOdm));
			outStream.flush();
			outStream.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	//localhost:8080/createSubject?studyId=1&metadataVersionId=2&studyEventDefId=48&formDefId=21&itemGroupDefId=40&itemDefId=25
	@GetMapping("/createSubject")
	public String createSubject(@RequestParam(value="studyId", required=false) Long studyId, 
			@RequestParam(value="metadataVersionId", required=false) Long metadataVersionId,
			@RequestParam(value="studyEventDefId", required=false) Long studyEventDefId,
			@RequestParam(value="formDefId", required=false) Long formDefId,
			@RequestParam(value="itemGroupDefId", required=false) Long itemGroupDefId,
			@RequestParam(value="itemDefId", required=false) Long itemDefId) {
		
		Study study = studyDesignSvc.findStudy(studyId);
		ClinicalData clinicalData=new ClinicalData();
		MetadataVersion metadatav = studyDesignSvc.findByIdFromStudy(study, metadataVersionId);
		
				
		clinicalData.setMetadataVersion(metadatav);
		metadatav.setClinicalData(clinicalData);
		//clinicalData.setStudy(study);
		//study.setClinicalData(clinicalData);
		
		
		SubjectData subjectData = new SubjectData();
		subjectData.setSubjectKey("Luik04");
		subjectData.setClinicalData(clinicalData);
		subjectData.setStatus(new Long(SubjectData.SUBJECT_ENROLLED));
		subjectData.setStatusDate(new Date());
		clinicalData.getElectronicRecordList().add(subjectData);
		
		StudyEventData studyEventData = new StudyEventData();
		studyEventData.setEffectiveDate(new Date());
		studyEventData.setSubjectData(subjectData);
		StudyEventDef sd = studyDesignSvc.findByIdStudyEventDef(metadatav, studyEventDefId);
		studyEventData.setStudyEventDef(sd);

		sd.getStudyEventDataList().add(studyEventData);
		subjectData.getStudyEventDataList().add(studyEventData);
		//	FormData 
		FormDef fd = studyDesignSvc.findByIdFormDef(metadatav, formDefId);

		FormData formData = new FormData();
		formData.setFormDef(fd);
		formData.setStudyEventData(studyEventData);
		fd.getFormDataList().add(formData);
		studyEventData.getFormDataList().add(formData);
		
		ItemGroupDef itgd = studyDesignSvc.findByIdItemGroupDef(metadatav, itemGroupDefId);
		
		ItemGroupData itgdata = new ItemGroupData();
		itgdata.setFormData(formData);
		itgdata.setItemGroupDef(itgd);
		itgdata.setItemGroupRepeatKey("1");
		itgd.getItemGroupDataList().add(itgdata);
		
		formData.getItemGroupDataList().add(itgdata);
		
		ItemDef idf = studyDesignSvc.findByIdItemDef(metadatav, itemDefId);
		ItemData itemData = new ItemData();
		itemData.setItemGroupData(itgdata);
		idf.getItemDataList().add(itemData);
		itemData.setItemDef(idf);
		itgdata.getItemDataList().add(itemData);
		
		studyDesignSvc.saveClinicalData(clinicalData);
		
		return "redirect:/";
	}
	@GetMapping("/createStudy")
	public String createStudy() {
/*		log.debug("Create a new study");
		Study study = new Study();
		study.setProtocolName("ICARUS - protocole v01");
		study.setStudyName("ICARUS - study");
		study.setStudyDescription("blablabla");
		study.setStudyStartDate(new Date());
		
		AdminData ad = new AdminData();
		study.setAdminData(ad);
		ad.setStudy(study);
		
		Protocol protocol = new Protocol();
		
		MetadataVersion metadatav = new MetadataVersion();
		metadatav.setProtocol(protocol);
		metadatav.setName("Metadata v01");
		metadatav.setStudy(study);
		metadatav.setVersion(new Long(1));
		
		protocol.setMetadataVersion(metadatav);
		
		StudyEventDef sd = new StudyEventDef();
		sd.setName("Screening Visit");
		sd.setMetadataVersion(metadatav);
		Description descr = studyDesignSvc.createDescription("Screening Visit", new Locale("en"));
		sd.setDescription(descr);
		descr.setClinicalDef(sd);

		FormDef fd = new FormDef();
		fd.setName("DEMOG");
		fd.setMetadataVersion(metadatav);
		metadatav.getFormDefList().add(fd);
		descr = studyDesignSvc.createDescription("Demographic form", new Locale("en"));
		fd.setDescription(descr);
		descr.setClinicalDef(fd);
		
		ItemGroupDef igd = new ItemGroupDef();
		igd.setDomain("DM");
		igd.setSasDatasetName("DM");
		igd.setName("Demog Item");
		igd.setMetadataVersion(metadatav);
		descr = studyDesignSvc.createDescription("Demographic Item1", new Locale("en"));
		igd.setDescription(descr);
		descr.setClinicalDef(igd);
		
		ItemDef id1 = new ItemDef();
		id1.setName("identification number in hospital");
		id1.setSasFieldName("USUBJID");
		id1.setDataType(ItemDef.DATATYPE_TEXT);
		id1.setMetadataVersion(metadatav);
		Question question1 = studyDesignSvc.createQuestion("identification number in hospital", new Locale("en"));
		id1.setQuestion(question1);
		question1.setItemDef(id1);
		
		ItemDef id2 = new ItemDef();
		id2.setName("hospital number where patient was treated");
		id2.setSasFieldName("HOSP");
		id2.setDataType(ItemDef.DATATYPE_TEXT);
		id2.setMetadataVersion(metadatav);
		Question question2 = studyDesignSvc.createQuestion("hospital number whre patient was treated", new Locale("en"));
		id2.setQuestion(question2);
		question2.setItemDef(id2);
		
		metadatav.getStudyEventDefList().add(sd);
		metadatav.getFormDefList().add(fd);
		metadatav.getItemGroupDefList().add(igd);
		metadatav.getItemDefList().add(id1);
		metadatav.getItemDefList().add(id2);

		study.getMetadataVersionList().add(metadatav);
		
		StudyEventRef studyEventRef = new StudyEventRef();
		studyEventRef.setOrderNumber(new Long(1));
		studyEventRef.setProtocol(protocol);
		studyEventRef.setStudyEventDef(sd);
		protocol.getStudyEventRefList().add(studyEventRef);
		
		StudyEventPred studyEventPred = new StudyEventPred();
		studyEventPred.setPredecessorId(null);
		studyEventPred.setStudyEventRef(studyEventRef);
		studyEventRef.getPredecessorList().add(studyEventPred);
		
		FormRef formRef = new FormRef();
		formRef.setFormDef(fd);
		formRef.setOrderNumber(new Long(1));
		formRef.setStudyEventDef(sd);
		sd.getFormRefList().add(formRef);
		
		ItemGroupRef itemGroupRef = new ItemGroupRef();
		itemGroupRef.setOrderNumber(new Long(1));
		itemGroupRef.setFormDef(fd);
		itemGroupRef.setItemGroupDef(igd);
		fd.getItemGroupRefList().add(itemGroupRef);
		
		ItemRef itemRef1 = new ItemRef();
		itemRef1.setItemDef(id1);
		itemRef1.setItemGroupDef(igd);
		itemRef1.setMandatory(new Long(ClinicalRef.MANDATORY_NO));
		itemRef1.setOrderNumber(new Long(1));
		igd.getItemRefList().add(itemRef1);

		ItemRef itemRef2 = new ItemRef();
		itemRef2.setItemDef(id2);
		itemRef2.setItemGroupDef(igd);
		itemRef2.setMandatory(new Long(ClinicalRef.MANDATORY_NO));
		itemRef2.setOrderNumber(new Long(2));

		igd.getItemRefList().add(itemRef2);
		
	
		
	
		*/
		Study study= studyDesignSvc.createStudy("ICARUS - protocole v01", "ICARUS - protocole v01 - blablalbal", "CARUS - protocole v01", "Version 01", new Long(1), new Date());
		MetadataVersion mv = study.getMetadataVersionList().iterator().next();
		
		
	//	MetadataVersion mv = studyDesignSvc.findCurrentMetadataVersion(study.getId());
		StudyEventDef sd = studyDesignSvc.createStudyEventDef("Screening Visit", "Screening Visit", mv, new Long(1), null, null);
		FormDef fd = studyDesignSvc.createFormDef("DEMOG","DEMOG", mv, new Long(1), sd, null);
		ItemGroupDef igd = studyDesignSvc.createItemGroupDef("DM", "DEMOG_GRP", "DM", "description", mv, new Long(1), fd, null);
		ItemDef id1 = studyDesignSvc.createItemDef("USUBJID", "USUBJID", "identification number in hospital", ItemDef.DATATYPE_TEXT, null, new Long(ItemRef.MANDATORY_NO), new Long(255), mv , new Long(1), igd, null);
		ItemDef id2 = studyDesignSvc.createItemDef("UHOSPID", "UHOSPID", "hospital number whre patient was treated", ItemDef.DATATYPE_TEXT, null, new Long(ItemRef.MANDATORY_NO), new Long(255),  mv, new Long(2), igd, null);
		ItemDef id3 = studyDesignSvc.createItemDef("DOB","DOB", "date of birth", new Long(ItemDef.DATATYPE_DATE),null, new Long(ItemRef.MANDATORY_NO), null, mv, new Long(3), igd, null);
		ItemDef id4 = studyDesignSvc.createItemDef("SUPRESP", "SUPRESP", "Smoker", new Long(ItemDef.DATATYPE_BOOLEAN),null, new Long(ItemRef.MANDATORY_NO), null, mv, new Long(4), igd, null);
		CodeList codeList = studyDesignSvc.createCodeList("CL.ETHNIC", "CL.ETHNIC", "Ethnicity format", mv, new Long(ItemDef.DATATYPE_INT), null);
		studyDesignSvc.addCodeListItem(codeList,"1", "Affrican", new Float(1), null);
		studyDesignSvc.addCodeListItem(codeList,"2", "Asiatic", new Float(2), null);
		studyDesignSvc.addCodeListItem(codeList,"3", "Caucasian", new Float(3), null);
		studyDesignSvc.addCodeListItem(codeList,"4", "Hispanic", new Float(4), null);

		ItemDef id5 = studyDesignSvc.createItemDef("ETHNIC", "ETHNIC", "Ethnicity", new Long(ItemDef.DATATYPE_INT),codeList, new Long(ItemRef.MANDATORY_NO), null, mv, new Long(5), igd, null);

		studyDesignSvc.saveStudy(study);
	
		SignatureDef signatureDef = new SignatureDef();
		signatureDef.setAdminData(study.getAdminData());
		signatureDef.setLegalReason("The signer accepts responsibility for the accuracy of this data as the best as his knowledge.");
		signatureDef.setMeaning("Autorship");
		signatureDef.setMethodology(new Long(SignatureDef.Electronic));
		study.getAdminData().getSignatureDefList().add(signatureDef);

//		studyDesignSvc.saveSignatureDef(signatureDef);
		
		return "redirect:/";
	}
}
