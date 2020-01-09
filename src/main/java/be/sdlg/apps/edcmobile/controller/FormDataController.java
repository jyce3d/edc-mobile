package be.sdlg.apps.edcmobile.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import be.sdlg.apps.edcmobile.model.ClinicalRef;
import be.sdlg.apps.edcmobile.model.CodeList;
import be.sdlg.apps.edcmobile.model.CodeListItem;
import be.sdlg.apps.edcmobile.model.DBUser;
import be.sdlg.apps.edcmobile.model.FormData;
import be.sdlg.apps.edcmobile.model.FormDef;
import be.sdlg.apps.edcmobile.model.ItemData;
import be.sdlg.apps.edcmobile.model.ItemDef;
import be.sdlg.apps.edcmobile.model.ItemGroupData;
import be.sdlg.apps.edcmobile.model.ItemGroupDef;
import be.sdlg.apps.edcmobile.model.ItemGroupRef;
import be.sdlg.apps.edcmobile.model.ItemRef;
import be.sdlg.apps.edcmobile.model.Location;
import be.sdlg.apps.edcmobile.model.MetadataVersion;
import be.sdlg.apps.edcmobile.model.SignatureDef;
import be.sdlg.apps.edcmobile.model.Study;
import be.sdlg.apps.edcmobile.model.StudyEventData;
import be.sdlg.apps.edcmobile.model.StudyEventDef;
import be.sdlg.apps.edcmobile.model.SubjectData;
import be.sdlg.apps.edcmobile.svc.EdcSvc;
import be.sdlg.apps.edcmobile.svc.StudyDesignSvc;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/formData")
public class FormDataController {
	private static final org.slf4j.Logger log =
		    org.slf4j.LoggerFactory.getLogger(FormDataController.class);
	
	@Autowired
	private EdcSvc edcSvc;
	@Autowired
	private StudyDesignSvc studyDesignSvc;
	public class CodeListItemMVC {
		protected Long id;
		protected String codedValue;
		protected String decode;
		
		public CodeListItemMVC(CodeListItem codeListItem, Locale locale) {
			this.id = codeListItem.getId();
			this.codedValue = codeListItem.getCodedValue();
			this.decode = studyDesignSvc.getDecode(codeListItem.getDecode(), locale);
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCodedValue() {
			return codedValue;
		}

		public void setCodedValue(String codedValue) {
			this.codedValue = codedValue;
		}

		public String getDecode() {
			return decode;
		}

		public void setDecode(String decode) {
			this.decode = decode;
		}
		
	}
	public class CodeListMVC {
		protected Long id;
		protected String name;
		protected String description;
		protected List<CodeListItemMVC> codeListItems;
		public CodeListMVC(CodeList codeList, Locale locale) {
			this.id = codeList.getId();
			this.name = codeList.getName();
			this.description = studyDesignSvc.getDescription(codeList.getDescription(), null);
			codeListItems = new ArrayList<CodeListItemMVC>();
			for (CodeListItem codeListItem : codeList.getCodeListItems()) {
				CodeListItemMVC cdimvc = new CodeListItemMVC(codeListItem, locale);
				codeListItems.add(cdimvc);
			}
			
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public List<CodeListItemMVC> getCodeListItems() {
			return codeListItems;
		}
		public void setCodeListItems(List<CodeListItemMVC> codeListItems) {
			this.codeListItems = codeListItems;
		}
		
		
	}
	public class ItemDefMVC {
		protected String name;
		protected String question;
		protected Long id;
		protected Long order;
		protected Long dataType;
		protected CodeListMVC codeList;
		protected String required;
		protected String value;
		public ItemDefMVC(ItemRef itemRef, ItemData itemData, Locale locale) {
			ItemDef itemDef = itemRef.getItemDef();
			this.name = itemDef.getName();
			this.id = itemDef.getId();
			this.question = studyDesignSvc.getQuestion(itemDef.getQuestion(), null);
			if( itemDef.getCodeList() !=null)
				codeList = new CodeListMVC(itemDef.getCodeList(), locale);
			this.dataType = itemDef.getDataType();
			required = itemRef.getMandatory() == ClinicalRef.MANDATORY_YES ? "t" : "f";
			order = itemRef.getOrderNumber();
			value = "";	
			if (itemData != null )
				value = itemData.getItemDef().getCodeList() !=null ? itemData.getCodeListValue() : itemData.getValue();
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getQuestion() {
			return question;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public CodeListMVC getCodeList() {
			return codeList;
		}
		public void setCodeList(CodeListMVC codeList) {
			this.codeList = codeList;
		}
		public Long getDataType() {
			return dataType;
		}
		public void setDataType(Long dataType) {
			this.dataType = dataType;
		}
		public Long getOrder() {
			return order;
		}
		public void setOrder(Long order) {
			this.order = order;
		}
		public String getRequired() {
			return required;
		}
		public void setRequired(String required) {
			this.required = required;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	
	}
	public class ItemGroupDefMVC {
		protected String name;
		protected String description;
		protected Long id;
		protected Long order;
		protected List<ItemDefMVC> itemDefList;
		public ItemGroupDefMVC (ItemGroupDef itemGroupDef, ItemGroupData itemGroupData, Locale locale) {
			this.name = itemGroupDef.getName();
			this.id = itemGroupDef.getId();
			this.description = studyDesignSvc.getDescription(itemGroupDef.getDescription(), null);
			itemDefList = new ArrayList<ItemDefMVC>();
			ItemData itemData = null;			
			for (ItemRef ir : itemGroupDef.getItemRefList()) {
				if (itemGroupData != null) {
					for (ItemData idData : itemGroupData.getItemDataList() ) {
						itemData = null;
						if (idData.getItemDef().getId().equals(ir.getItemDef().getId())) {
							itemData = idData;
							break;
						}
					}
				}
				ItemDefMVC idmvc = new ItemDefMVC(ir, itemData, locale);
				idmvc.setOrder(ir.getOrderNumber());
				itemDefList.add(idmvc);
			}		
			Collections.sort(itemDefList, new Comparator<ItemDefMVC>() {
				public int compare(ItemDefMVC i1, ItemDefMVC i2) {
					if (i1.getOrder() == i2.getOrder()) return 0;
					return i1.order< i2.order ? -1 : 1;
				}
			});
		}
		
		public Long getOrder() {
			return order;
		}

		public void setOrder(Long order) {
			this.order = order;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public List<ItemDefMVC> getItemDefList() {
			return itemDefList;
		}
		public void setItemDefList(List<ItemDefMVC> itemDefList) {
			this.itemDefList = itemDefList;
		}
		
	}
	public class FormDefMVC { 
		public String name;
		public String description;
		public Long id;
		public boolean dirty;
		public List<ItemGroupDefMVC> itemGroupDefList;
		
		public FormDefMVC(FormDef formDef, FormData formData, Locale locale) {
			this.name = formDef.getName();
			this.description = studyDesignSvc.getDescription(formDef.getDescription(), null);
			this.id = formDef.getId();
			this.dirty = (formData == null ) ? false : true;
			itemGroupDefList = new ArrayList<ItemGroupDefMVC>();
			ItemGroupData itemGroupData = null;
	
			for (ItemGroupRef igr : formDef.getItemGroupRefList()) {
				if (formData != null ) {
					for (ItemGroupData itgData : formData.getItemGroupDataList()) {
						itemGroupData=null;
						if (itgData.getItemGroupDef().getId().equals(igr.getItemGroupDef().getId())) {
							itemGroupData = itgData;
		
							break;
						}
					}
				}
				ItemGroupDefMVC itdmvc = new ItemGroupDefMVC(igr.getItemGroupDef(), itemGroupData, locale);
				itdmvc.setOrder(igr.getOrderNumber());
				itemGroupDefList.add(itdmvc);
			}
			Collections.sort(itemGroupDefList, new Comparator<ItemGroupDefMVC>() {
				public int compare(ItemGroupDefMVC ig1, ItemGroupDefMVC ig2) {
					if (ig1.getOrder() == ig2.getOrder()) return 0;
					return ig1.getOrder()< ig2.getOrder() ? -1 : 1;
				}
			});
			
		}

		public boolean isDirty() {
			return dirty;
		}

		public void setDirty(boolean dirty) {
			this.dirty = dirty;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public List<ItemGroupDefMVC> getItemGroupDefList() {
			return itemGroupDefList;
		}

		public void setItemGroupDefList(List<ItemGroupDefMVC> itemGroupDefList) {
			this.itemGroupDefList = itemGroupDefList;
		}
	}

	@GetMapping
	public String showFormData( @RequestParam(value="studyEventDefId", required=true) Long studyEventDefId,
				@RequestParam(value="formDefId", required=true) Long formDefId,@RequestParam(value="subjectId", required=true) Long subjectId, 
				HttpServletRequest request,  Locale locale, Model model, Authentication authentication) {
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		String userName  = authentication.getName();
		Long userId = (Long) request.getSession().getAttribute("userId");

		
		SubjectData subject = edcSvc.findSubject(subjectId);
		MetadataVersion mv = studyDesignSvc.findCurrentMetadataVersion(studyId);
		StudyEventDef studyEvent = studyDesignSvc.findByIdStudyEventDef(mv, studyEventDefId); 
		
		FormDef formDef = studyDesignSvc.findByIdFormDef(mv, formDefId);
		
		List<StudyEventData> studyEventDataList = edcSvc.findStudyEventDataBySubjectAndStudyEventDef(subjectId, studyEventDefId);
		int sizeList = studyEventDataList.size();
		if (sizeList>1) return "technicalError";
		StudyEventData studyEventData = null;
		if (sizeList ==1)
			studyEventData = studyEventDataList.get(0);
		
		List<FormData> formDataList = edcSvc.findFormDataBySubjectIdByFormDefId(studyEventDefId, formDefId, subjectId);
		sizeList = formDataList.size();
		if (sizeList>1) return "technicalError";
		FormData formData = null;
		if (sizeList ==1)
			formData = formDataList.get(0);
	//	String isDEA = administrationService.hasGrant( studyId, locationId, userId, new Long(Grant.DATA_ENTRY) ) ? "t" : "f";
		//SignatureDef signatureDef = studyDesignSvc.findSignatureDefByStudyId(studyId, new Long(SignatureDef.CRF_SIGNATURE));

		FormDefMVC formDefMvc = new FormDefMVC(formDef,formData, locale);
		
		model.addAttribute("subject", subject);
		model.addAttribute("studyEvent", studyEvent);
		model.addAttribute("formDef", formDefMvc);
		model.addAttribute("formDataId", formData != null ? formData.getId() : null);
		model.addAttribute("studyEventDataId", studyEventData != null ? studyEventData.getId() : null);
		//model.addAttribute("isDEA", isDEA);
		model.addAttribute("statusDraft", FormData.STATUS_DRAFT);
		model.addAttribute("statusSign", FormData.STATUS_SIGNED);
		model.addAttribute("statusRTS", FormData.STATUS_READY_TO_SIGN);
//		model.addAttribute("meaning", signatureDef.getMeaning());
	//	model.addAttribute("legalReason", signatureDef.getLegalReason());
	//	model.addAttribute("signatureDefId", signatureDef.getId());
		
		return "formData";	
	}
	
	@PostMapping
	public String processFormData(Locale locale, Model model, HttpServletRequest request, Authentication authentication) {
		
		Long formDefId = new Long(request.getParameter("id"));
		Long studyEventId = new Long(request.getParameter("studyEventId"));
		Long studyId = (Long) request.getSession().getAttribute("studyId");
		Long locationId = (Long) request.getSession().getAttribute("locationId");
		Long userId = (Long) request.getSession().getAttribute("userId");
		Long subjectDataId = new Long(request.getParameter("subjectId"));
		Long status = null;
		if (request.getParameter("form-status") != null && request.getParameter("form-status").length()>0 )
		 status = new Long(request.getParameter("form-status"));
		else status = new Long(FormData.STATUS_DRAFT);
		String login = null;
		String signature = null;
		Long signatureDefId = null;
		//DBUser user = null; 
		String reasonForUpdate= null;
		if (status == FormData.STATUS_DRAFT) 
			reasonForUpdate = request.getParameter("reasonForUpdate");
		else if (status == FormData.STATUS_SIGNED) {
			reasonForUpdate =request.getParameter("reasonForUpdateSign");
			login = request.getParameter("login");
			signature = request.getParameter("signature");
			signatureDefId = new Long(request.getParameter("signatureDefId"));
			//user = administrationService.getUserByLogin(login);
	//		if (!administrationService.isMatchingSignature(studyId, locationId, signature, user) )
		//		return "technicalError";
		}
		else if (status ==FormData.STATUS_READY_TO_SIGN ) {
			reasonForUpdate =request.getParameter("reasonForUpdateRTS");
		}
		
		Long formDataId= null;
		Long studyEventDataId = null;

		if (request.getParameter("formDataId") !=null && request.getParameter("formDataId").length()>0 ) 
			formDataId = new Long(request.getParameter("formDataId"));
			
		
		if (request.getParameter("studyEventDataId") !=null && request.getParameter("studyEventDataId").length()>0 )
			studyEventDataId = new Long(request.getParameter("studyEventDataId"));
		
		Map<String, String> itemsDef = new HashMap<String, String>();
		Map<String, String> itemsDisp = new HashMap<String, String>();
		Map<String, String> itemsDirty = new HashMap<String, String>();
		
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.indexOf("item-def-")>=0)
				itemsDef.put(paramName, request.getParameter(paramName));
			else if (paramName.indexOf("item-display-")>=0)
				itemsDisp.put(paramName, request.getParameter(paramName));
			else if (paramName.indexOf("item-dirty-")>=0)
				itemsDirty.put(paramName, request.getParameter(paramName));
		}
		Study study = studyDesignSvc.findStudy(studyId);
		MetadataVersion mvcur = studyDesignSvc.findCurrentMetadataVersion(studyId);
		MetadataVersion mv =null;
		for ( MetadataVersion mv2 : study.getMetadataVersionList())
			if (mvcur.getId().equals(mv2.getId())) {
				mv = mv2;
				break;
			}
		StudyEventDef studyEventDef = studyDesignSvc.findByIdStudyEventDef(mv, studyEventId);
		FormDef formDef = studyDesignSvc.findByIdFormDef(mv, formDefId);
	//	SignatureDef signatureDef = studyDesignSvc.findSignatureDefByStudyId(studyId, new Long(SignatureDef.CRF_SIGNATURE));
		SignatureDef signatureDef =null;
		DBUser user=null;
		Location location=null;
		try {
			edcSvc.saveFormData(mv, subjectDataId, studyEventDef, studyEventDataId, formDef, formDataId, status, reasonForUpdate, itemsDef, itemsDisp, itemsDirty, authentication.getName(), signatureDef, user, location);
		}
		catch (Exception e) {
			log.debug(e.getMessage());
			model.addAttribute("error", e.getMessage());
			System.out.print(e.getCause());
			return "technicalError";
		}
		return "redirect:/studyEventList?subjectDataId="+subjectDataId+"&studyId="+studyId;
	}

}