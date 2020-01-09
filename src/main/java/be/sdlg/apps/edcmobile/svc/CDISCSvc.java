package be.sdlg.apps.edcmobile.svc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.springframework.stereotype.Service;

import be.sdlg.apps.edcmobile.controller.HomeController;
import be.sdlg.apps.edcmobile.model.ElectronicRecord;
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
import be.sdlg.apps.edcmobile.model.Study;
import be.sdlg.apps.edcmobile.model.StudyEventData;
import be.sdlg.apps.edcmobile.model.StudyEventDef;
import be.sdlg.apps.edcmobile.model.StudyEventRef;
import be.sdlg.apps.edcmobile.model.SubjectData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CDISCSvc {
	private static final org.slf4j.Logger log =
		    org.slf4j.LoggerFactory.getLogger(CDISCSvc.class);
	public Document createODM(String strFileType, String strFileOID, Date creationDateTime, Study study, MetadataVersion mv, List<SubjectData> subjectDataList,  Locale locale) {
		  String pattern = "yyyy-MM-DDZHH:mm:ss";
		  if (locale ==null)
			  locale = new Locale("en");
		  SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, locale);
		  String creationDate = simpleDateFormat.format(creationDateTime);
		  
		  if (strFileType ==null)
			strFileType = new String("Snapshot");
		  Document cdiscDoc = new Document();
		  Element odm = new Element("ODM");
		  cdiscDoc.setRootElement(odm);
		 
		  odm.setAttribute(new Attribute("ODMVersion", "1.2"));
		  odm.setAttribute(new Attribute("FileType", strFileType));
		  odm.setAttribute(new Attribute("FileOID", strFileOID));
		  odm.setAttribute(new Attribute("SourceSystem", "PLX-EDC-MOBILE"));
		  odm.setAttribute(new Attribute("SourceSystemVersion", "V01"));
		  odm.setAttribute(new Attribute("CreationDateTime", creationDate ));
		  
		  Element studyEl = createStudy(study, mv, subjectDataList);
		  odm.addContent(studyEl);
			//TODO: complete admindata
		  Element adminData = new Element("AdminData");
		  odm.addContent(adminData);
			
		  Element clinical = createClinicalData(study, mv, subjectDataList);
		  odm.addContent(clinical);
		  return cdiscDoc;
	}
	public Element createStudy(Study study, MetadataVersion mv, List<SubjectData> subjectDataList) {
		Element odmStudy = new Element("Study");
		odmStudy.setAttribute(new Attribute("OID", "STUDY."+study.getId()));
		Element globalVariables = new Element("GlobalVariables");
		Element studyName = new Element("StudyName");
		studyName.setText(study.getStudyName());
		Element studyDescr = new Element("StudyDescription");
		studyDescr.setText(study.getStudyDescription());
		Element protocolName = new Element("ProtocolName");
		protocolName.setText(study.getProtocolName());
		globalVariables.addContent(studyName);
		globalVariables.addContent(studyDescr);
		globalVariables.addContent(protocolName);
		odmStudy.addContent(globalVariables);
		
		//TODO: complete basic definitions
		Element basicDefinitions = new Element("BasicDefinitions");
		odmStudy.addContent(basicDefinitions);
		
		Element mvEl = createMetadataVersion(mv);
		odmStudy.addContent(mvEl);
		
		return odmStudy;
		
	}
	// http://support.sas.com/documentation/cdl/en/cdisc/60755/HTML/default/viewer.htm#a003070386.htm
	// https://www.javaguides.net/2018/10/how-to-create-xml-file-in-java-jdom-parser.html
	
	public Element createClinicalData(Study study, MetadataVersion mv, List<SubjectData> subjectDataList) {
		Element cdEl = new Element("ClinicalData");
		cdEl.setAttribute(new Attribute("StudyOID", "STUDY."+study.getId()));
		cdEl.setAttribute(new Attribute("MetaDataVersionOID", "MV."+mv.getId()));
		
		
		for (SubjectData sd : subjectDataList) {
			Element sdEl = createSubjectData(mv, sd);
			cdEl.addContent(sdEl);
		}
		return cdEl;
	}
	public Element createSubjectData(MetadataVersion mv, SubjectData sd) {
		Element sdEl = new Element("SubjectData");
		sdEl.setAttribute(new Attribute("SubjectKey", sd.getSubjectKey()));
		for (StudyEventData sed : sd.getStudyEventDataList()) {
			Element sedEl = createStudyEventData(sed);
			sdEl.addContent(sedEl);
		}
		return sdEl;
		
	}
	public Element createStudyEventData(StudyEventData sed) {
		Element sedEl = new Element("StudyEventData");
		sedEl.setAttribute(new Attribute("StudyEventOID", "SE." + sed.getStudyEventDef().getId()));
		if (sed.getStudyEventRepeatKey() !=null)
			sedEl.setAttribute(new Attribute("StudyEventRepeatKey", sed.getStudyEventRepeatKey().toString()));
		else 
			sedEl.setAttribute(new Attribute("StudyEventRepeatKey", "1"));

		for (FormData fd : sed.getFormDataList()) {
			Element fdEl = createFormData(fd);
			sedEl.addContent(fdEl);
		}
		return sedEl;
	}
	public Element createFormData(FormData fd) {
		Element fdEl = new Element("FormData");
		fdEl.setAttribute(new Attribute("FormOID", "FORM."+fd.getFormDef().getId()));
		if (fd.getFormRepeatKey() != null)
			fdEl.setAttribute(new Attribute("FormRepeatKey", fd.getFormRepeatKey().toString()));
		else
			fdEl.setAttribute(new Attribute("FormRepeatKey", "1"));
			
		for (ItemGroupData igd : fd.getItemGroupDataList()) {
			Element igdEl = createItemGroupData(igd) ;
			fdEl.addContent(igdEl);
		}
		return fdEl;
	}
	public Element createItemGroupData(ItemGroupData igd) {
		Element igdEl = new Element("ItemGroupData");
		igdEl.setAttribute(new Attribute("ItemGroupOID", "IG."+igd.getItemGroupDef().getId()));
		if (igd.getItemGroupRepeatKey() != null)
			igdEl.setAttribute(new Attribute("ItemGroupRepeatKey", igd.getItemGroupRepeatKey()));
		else
			igdEl.setAttribute(new Attribute("ItemGroupRepeatKey", "1"));
		
		for (ItemData id : igd.getItemDataList()) {
			Element idEl = createItemData(id);
			igdEl.addContent(idEl);
		}
		return igdEl;
	}
	public Element createItemData(ItemData id) {
		Element idEl = new Element("ItemData");
		idEl.setAttribute(new Attribute("ItemOID", "ID."+id.getItemDef().getId()));
		idEl.setAttribute(new Attribute("Value", id.getValue()!=null ? id.getValue() : ""));
		return idEl;
	}
 	public Element createMetadataVersion(MetadataVersion mv) {
		Element mvEl = new Element("MetaDataVersion");
		mvEl.setAttribute(new Attribute("OID", "MV."+mv.getId()));
		mvEl.setAttribute(new Attribute("Name", mv.getName()));
		
		Element protocolEl = new Element("Protocol");
		for (StudyEventRef sr : mv.getProtocol().getStudyEventRefList()) {
			Element srEl = new Element("StudyEventRef");
			srEl.setAttribute(new Attribute("StudyEventOID", "SE."+sr.getId()));
			srEl.setAttribute(new Attribute("OrderNumber", sr.getOrderNumber().toString()));
			if (sr.getMandatory() == null)
				sr.setMandatory(new Long(StudyEventRef.MANDATORY_NO));
			srEl.setAttribute(new Attribute("Mandatory", sr.getMandatory() == StudyEventRef.MANDATORY_YES ? "Yes" : "No" ));
			protocolEl.addContent(srEl);
		}
		mvEl.addContent(protocolEl);

		for (StudyEventDef sd : mv.getStudyEventDefList()) {
			Element sdEl = new Element("StudyEventDef");
			sdEl.setAttribute(new Attribute("OID", "SE."+sd.getId()));
			sdEl.setAttribute(new Attribute("Name", sd.getName()));
			if (sd.getRepeating() == null)
				sd.setRepeating(new Long(StudyEventDef.REPEATING_NO));
			sdEl.setAttribute(new Attribute("Repeating", sd.getRepeating() == StudyEventDef.REPEATING_YES ? "Yes" : "No"));
			String strType = "Common";
			if (sd.getType() == null)
				strType="Unscheduled";
			else {
				if (sd.getType() == StudyEventDef.TYPE_SCHEDULED) strType="Scheduled";
				else if (sd.getType()  == StudyEventDef.TYPE_UNSCHEDULED) strType ="Unscheduled";
			}
			sdEl.setAttribute(new Attribute("Type", strType ));
			for (FormRef fr : sd.getFormRefList() ) {
				Element frEl = new Element("FormRef");
				frEl.setAttribute(new Attribute("FormOID", "FORM."+fr.getFormDef().getId()));
				frEl.setAttribute(new Attribute("OrderNumber", fr.getOrderNumber().toString()));
				if (fr.getMandatory() == null)
					fr.setMandatory(new Long(FormRef.MANDATORY_NO));
				frEl.setAttribute(new Attribute("Mandatory", fr.getMandatory() == FormRef.MANDATORY_YES ? "Yes" : "No"));
				sdEl.addContent(frEl);
			}
			mvEl.addContent(sdEl);
		}
		for (FormDef fd : mv.getFormDefList()) {
				Element fdEl = new Element("FormDef");
				fdEl.setAttribute(new Attribute("OID", "FORM."+fd.getId() ));
				fdEl.setAttribute(new Attribute("Name", fd.getName()));
				if (fd.getRepeating() == null)
					fd.setRepeating(new Long(FormDef.REPEATING_NO));
				fdEl.setAttribute(new Attribute("Repeating", fd.getRepeating() == FormDef.REPEATING_YES ? "Yes" : "No"));
				for (ItemGroupRef igr : fd.getItemGroupRefList()) {
					Element igrEl = new Element("ItemGroupRef");
					igrEl.setAttribute(new Attribute("ItemGroupOID", "IG."+ igr.getItemGroupDef().getId()));
					if (igr.getMandatory() == null)
						igr.setMandatory(new Long(ItemGroupRef.MANDATORY_NO));
					igrEl.setAttribute(new Attribute("Mandatory", igr.getMandatory() == ItemGroupRef.MANDATORY_YES ? "Yes" : "No"));
					fdEl.addContent(igrEl);
				}
				mvEl.addContent(fdEl);
		}
		for (ItemGroupDef igd : mv.getItemGroupDefList()) {
			Element igdEl = new Element("ItemGroupDef");
			igdEl.setAttribute(new Attribute("OID", "IG." + igd.getId()));
			if (igd.getRepeating() == null)
				 igd.setRepeating(new Long(ItemGroupDef.REFERENCE_DATA_NO));
			igdEl.setAttribute(new Attribute("Repeating", igd.getRepeating() == ItemGroupDef.REPEATING_YES ? "Yes" : "No"));
			igdEl.setAttribute(new Attribute("SASDatasetName", igd.getSasDatasetName()));
			igdEl.setAttribute(new Attribute("Name", igd.getName()));
			igdEl.setAttribute(new Attribute("Domain", igd.getDomain()));
			if (igd.getComment() !=null)
				igdEl.setAttribute(new Attribute("Comment", igd.getComment()));
			for (ItemRef ir : igd.getItemRefList()) {
				Element irEl = new Element("ItemRef");
				irEl.setAttribute(new Attribute("ItemOID", "ID."+ ir.getItemDef().getId()));
				irEl.setAttribute(new Attribute("OrderNumber", ir.getOrderNumber().toString()));
				if (ir.getMandatory() == null)
					ir.setMandatory(new Long(ItemRef.MANDATORY_NO));
				irEl.setAttribute(new Attribute("Mandatory", ir.getMandatory() == ItemRef.MANDATORY_YES ? "Yes" : "No" ));
				
				igdEl.addContent(irEl);
			}
			mvEl.addContent(igdEl);
		}
		String strDataType = "";
		for (ItemDef id : mv.getItemDefList()) {
			Element idEl = new Element("ItemDef");
			idEl.setAttribute(new Attribute("OID", "ID."+ id.getId()));
			idEl.setAttribute(new Attribute("Name", id.getName()));
			idEl.setAttribute(new Attribute("SasFieldName", id.getSasFieldName()));
			if (id.getDataType().equals(ItemDef.DATATYPE_BOOLEAN))
				strDataType = "boolean";
			else if (id.getDataType().equals(ItemDef.DATATYPE_DATE))
				strDataType ="date";
			else if (id.getDataType().equals(ItemDef.DATATYPE_FLOAT))
				strDataType="float";
			else if (id.getDataType().equals(ItemDef.DATATYPE_INT))
				strDataType ="integer";
			else if (id.getDataType().equals(ItemDef.DATATYPE_TEXT))
				strDataType ="text";
			idEl.setAttribute(new Attribute("DataType", strDataType));
			if (id.getLength() !=null)
				idEl.setAttribute(new Attribute("Length", id.getLength().toString()));
			mvEl.addContent(idEl);
			// TODO:Add Question
			// TODO: Add codelist
		}		
		return mvEl;
		
	}
	
}
