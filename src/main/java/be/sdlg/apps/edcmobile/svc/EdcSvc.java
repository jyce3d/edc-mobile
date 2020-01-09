package be.sdlg.apps.edcmobile.svc;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import be.sdlg.apps.edcmobile.model.Description;
import be.sdlg.apps.edcmobile.model.ElectronicRecord;
import be.sdlg.apps.edcmobile.model.FormData;
import be.sdlg.apps.edcmobile.model.FormDataAudit;
import be.sdlg.apps.edcmobile.model.FormDef;
import be.sdlg.apps.edcmobile.model.ItemData;
import be.sdlg.apps.edcmobile.model.ItemDataAudit;
import be.sdlg.apps.edcmobile.model.ItemDef;
import be.sdlg.apps.edcmobile.model.ItemGroupData;
import be.sdlg.apps.edcmobile.model.ItemGroupDataAudit;
import be.sdlg.apps.edcmobile.model.ItemGroupDef;
import be.sdlg.apps.edcmobile.model.ItemGroupRef;
import be.sdlg.apps.edcmobile.model.ItemRef;
import be.sdlg.apps.edcmobile.model.Location;
import be.sdlg.apps.edcmobile.model.MetadataVersion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.sdlg.apps.edcmobile.data.ElectronicRecordRepository;
import be.sdlg.apps.edcmobile.data.FormDataRepository;
import be.sdlg.apps.edcmobile.data.FormDefRepository;
import be.sdlg.apps.edcmobile.data.StudyEventDataRepository;
import be.sdlg.apps.edcmobile.data.SubjectDataRepository;
import be.sdlg.apps.edcmobile.model.AuditRecord;
import be.sdlg.apps.edcmobile.model.ClinicalData;
import be.sdlg.apps.edcmobile.model.DBUser;
import be.sdlg.apps.edcmobile.model.Decode;
import be.sdlg.apps.edcmobile.model.Question;
import be.sdlg.apps.edcmobile.model.Signature;
import be.sdlg.apps.edcmobile.model.SignatureDef;
import be.sdlg.apps.edcmobile.model.Study;
import be.sdlg.apps.edcmobile.model.StudyEventData;
import be.sdlg.apps.edcmobile.model.StudyEventDataAudit;
import be.sdlg.apps.edcmobile.model.StudyEventDef;
import be.sdlg.apps.edcmobile.model.SubjectData;
import be.sdlg.apps.edcmobile.model.TranslatedText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class EdcSvc implements EdcSvcIntf {
	protected static final String AUDIT_SUBJECT_CREATION = "SUBJECT_CREATION";
	protected static final String AUDIT_SUBJECT_UPDATE = "SUBJECT_UPDATE";
	protected static final String AUDIT_STUDYEVENT_CREATION ="STUDY_EVENT_DATA_CREATION";
	protected static final String AUDIT_FORM_CREATION = "FORM_DATA_CREATION";
	protected static final String AUDIT_ITEMGROUP_CREATION = "ITEM_GROUP_DATA_CREATION";
	protected static final String AUDIT_ITEM_CREATION = "ITEM_DATA_CREATION";
	
	@Autowired
	private StudyEventDataRepository studyEventDataDao;


	@Autowired 
	private SubjectDataRepository subjectDataDao;
	@Autowired
	private FormDataRepository formDataDao;
	@Autowired
	private FormDefRepository formDefDao;
	@Autowired
	private ElectronicRecordRepository electronicRecordDao;

	
	public SubjectData findSubject(Long subjectId) {
		Optional<SubjectData> sd=subjectDataDao.findById(subjectId);
		if (sd.isPresent())
			return sd.get();
		else 
			return null;
	}
	public List<StudyEventData> findStudyEventDataBySubject(Long subjectId) {
		List<StudyEventData> studyEventDataList = new ArrayList<StudyEventData>();
		studyEventDataDao.findStudyEventDataBySubjectId(subjectId).forEach(i -> studyEventDataList.add(i));
		return studyEventDataList;
	}

	public StudyEventData findStudyEventDataById(Long subjectEventDataId) {
		StudyEventData s2;
		Optional<StudyEventData> s = studyEventDataDao.findById(subjectEventDataId);
		if (s.isPresent()) s2=s.get(); else s2=null;
		return s2;
	}
	public List<StudyEventData> findStudyEventDataBySubjectAndStudyEventDef(Long subjectId, Long studyEventDefId) {
		List<StudyEventData> studyEventDataList = new ArrayList<StudyEventData>();
		studyEventDataDao.findStudyEventDataBySubjectIdAndStudyEventId(subjectId, studyEventDefId).forEach(i -> studyEventDataList.add(i));
		return studyEventDataList;
	}
	public List<FormData> findFormDataBySubjectIdByFormDefId(Long studyEventDefId, Long formDefId, Long subjectId) {
		List<FormData> formDataList = new ArrayList<FormData>();
		formDataDao.findFormDataByStudyEventDataByStudyEventDefBySubject(studyEventDefId, formDefId, subjectId).forEach(i->formDataList.add(i));
		return formDataList;
	}
	public FormData findFormDataById(Long id) {
		Optional<FormData> fd = formDataDao.findById(id);
		if (fd.isPresent())
			return fd.get();
		else 
			return null;
	}
	protected void populateAuditChecks( String fullName,
			String reasonForUpdate) throws Exception {
		if (reasonForUpdate ==null  ) throw new Exception("Reason cannot be null");
		if (reasonForUpdate.length()<1 ) throw new Exception("Reason cannot be null");
		if (fullName !=null && fullName.length()<1) throw new Exception("login cannot be null");
		else if (fullName==null) throw new Exception("login cannot be null");
	}
	protected void populateAudit(MetadataVersion mv, AuditRecord auditRecord, ElectronicRecord target,
			String fullName, String reason,
			String sourceId, int operation) {
		
		auditRecord.setElectronicRecord(target);
		target.getAuditRecordList().add(auditRecord);
		
		auditRecord.setDateTimeStamp(new Date());
		auditRecord.setEditPoint(new Long(AuditRecord.DBAudit));
		//TODO:Complete : auditRecord.setLocationName(Cont);
		auditRecord.setUserName(fullName);
		auditRecord.setUsedImputationMethod(new Long(AuditRecord.No));
		auditRecord.setReasonForChange(reason);
		auditRecord.setSourceId(sourceId);
		auditRecord.setOperation(new Long(operation));
		auditRecord.setClinicalData(mv.getClinicalData());
		mv.getClinicalData().getAuditRecordlist().add(auditRecord);
		
	}
	protected void populateStudyEventData(StudyEventData studyEventData, MetadataVersion mv, Long subjectDataId, StudyEventDef studyEventDef, String studyEventRepeatKey, Date effectiveDate, Long active,
			String fullName,
			String reasonForUpdate, int operationType) throws Exception {
		
		//DBUser user = null;
		//Location location = null;
		SubjectData subjectData= null;
		
		try {
			populateAuditChecks( fullName, 
					reasonForUpdate);
	//		user = this.getUserById(userId);
	//		location = this.getLocation(locationId);
		
		} catch (Exception e) {
			throw e;
		}
		// connect parent links
		if (subjectDataId == null && studyEventData.getSubjectData() !=null)
			subjectData = studyEventData.getSubjectData();
		else {
			if (subjectDataId != null)
				subjectData = this.findSubject(subjectDataId); else
					throw new Exception("SubjectDataId cannot be null");
		}
		subjectData.setClinicalData(mv.getClinicalData());
		mv.getClinicalData().getElectronicRecordList().add(subjectData);
		//mv.getClinicalData().getSubjectDataList().add(subjectData);
		
		if (studyEventData.getId() == null) {
			studyEventData.setSubjectData(subjectData);
			subjectData.getStudyEventDataList().add(studyEventData);
		}
		if (mv == null)
				throw new Exception("MetadataVersion must exist");
		
		
		
		studyEventData.setClinicalData(mv.getClinicalData());
		mv.getClinicalData().getElectronicRecordList().add(studyEventData);
		//-----
		// connect to the Def links
		
		if (studyEventDef == null)
				throw new Exception("StudyEventDef cannot be null");
		
		studyEventData.setStudyEventDef(studyEventDef);
		studyEventDef.getStudyEventDataList().add(studyEventData);
		// getting the previous information
		String oldStudyEventRepeatKey = studyEventData.getStudyEventRepeatKey();
		Date oldEffectiveDate = studyEventData.getEffectiveDate();
		Long oldActive = studyEventData.getActive();
		
		if (studyEventRepeatKey !=null)
			studyEventData.setStudyEventRepeatKey(studyEventRepeatKey);
		if (effectiveDate !=null)
			studyEventData.setEffectiveDate(effectiveDate);
		if (active != null) 
			studyEventData.setActive(active);
		
		// TODO: maybe add those one. Access the location and user's subject
		// data without those one, if it works let like otherwise add them and
		// test
		// keep in mind that in such a situation we probably have to save, the
		// location and the user
		// user.getSubjectDataList().add(subjectData);
		// location.getSubjectDataList().add(subjectData);

		// add audit trail
		StudyEventDataAudit sda = new StudyEventDataAudit();
		
		sda.setNewStudyEventRepeatKey(studyEventData.getStudyEventRepeatKey());
		sda.setOldStudyEventRepeatKey(oldStudyEventRepeatKey);
		
		sda.setNewEffectiveDate(studyEventData.getEffectiveDate());
		sda.setOldEffectiveDate(oldEffectiveDate);
				
		sda.setOldActive(oldActive);
		sda.setNewActive(studyEventData.getActive());
		
		
		populateAudit(mv, sda, studyEventData, fullName, reasonForUpdate, "",
				operationType);

		
			
	}
	public StudyEventData createStudyEventData(MetadataVersion mv, Long subjectDataId, StudyEventDef studyEventDef, String studyEventRepeatKey, Date effectiveDate, Long active, 
			 String fullName) throws Exception {
		
		StudyEventData sed = new StudyEventData();
		try {
			populateStudyEventData(sed, mv, subjectDataId, studyEventDef, studyEventRepeatKey, effectiveDate, active,
					fullName, AUDIT_STUDYEVENT_CREATION, AuditRecord.CREATE_STUDYEVENT);
			studyEventDataDao.save(sed);
		} catch (Exception e) {
			throw e;
		}
		return sed;
	}
	
	protected void populateFormData(FormData formData, MetadataVersion mv, StudyEventData studyEventData, FormDef formDef,
			String formRepeatKey, Long status, Long active, String reasonForUpdate, String fullName, int operationType, FormDataAudit fa) throws Exception {
		
		try {
			populateAuditChecks( fullName, 
					reasonForUpdate);
		} catch (Exception e) {
			throw new Exception("poplulateFormData: wrong audit checks");
		}
		// connect with parent link
		if ( formData.getStudyEventData() !=null && formData.getStudyEventData().getId()==studyEventData.getId() )
			;
		else {
		 formData.setStudyEventData(studyEventData);
		 studyEventData.getFormDataList().add(formData);
		}

		formData.setClinicalData(mv.getClinicalData());
	
		//----
		// Connect to def link

		formData.setFormDef(formDef);
		formDef.getFormDataList().add(formData);
		//------------------------
		String oldFormRepeatKey = formData.getFormRepeatKey();
		Long oldActive = formData.getActive();
		Long oldStatus = formData.getStatus();
		
		if (formRepeatKey !=null)
			formData.setFormRepeatKey(formRepeatKey);
		if (active != null) 
			formData.setActive(active);
		
		if (status !=null) {
			formData.setStatus(status);
			formData.setStatusDate(new Date());
		}
		// add audit trail
		fa = new FormDataAudit();
		
		fa.setNewFormRepeatKey(formData.getFormRepeatKey());
		fa.setOldFormRepeatKey(oldFormRepeatKey);
						
		fa.setOldActive(oldActive);
		fa.setNewActive(formData.getActive());
		
		fa.setOldStatus(oldStatus);
		fa.setNewStatus(formData.getStatus());
	
		populateAudit(mv,fa, formData, fullName, reasonForUpdate, "",
				operationType);

		
			
	}
	
	public FormData createFormData(MetadataVersion mv, StudyEventData studyEventData, FormDef formDef,
			String formRepeatKey, Long status, Long active, String fullName, FormDataAudit formDataAudit)
			throws Exception {
		FormData formData = new FormData();
		try {
			populateFormData(formData, mv, studyEventData, formDef, formRepeatKey,
					status, active, AUDIT_FORM_CREATION, fullName, AuditRecord.CREATE_FORM, formDataAudit);
			//createFormItemGroupData(formData, studyId, status, active, locationId, userId);
			formDataDao.save(formData);
		} catch(Exception e) {
			throw new Exception("createFormData:Unable to create FormData");
		}
		return formData;
	}
	protected ItemData getExistingItemData(ItemGroupData itgdata, ItemDef idef) {
		for (ItemData id : itgdata.getItemDataList() ) {
			if (id.getItemDef().getId()==idef.getId()) 
				return id;
		}
		return null;
		
	}
	public void updateFormData(MetadataVersion mv, FormData formData, StudyEventData studyEventData,
			FormDef formDef, String formRepeatKey, Long status, Long active,
			String fullName, String reasonForUpdate,
			int operationType, FormDataAudit formDataAudit)
			throws Exception {
			
	//	FormData formData = formDataDao.get(formDataId);
		try {
	
			populateFormData(formData, mv, studyEventData, formDef, formRepeatKey,
					status, active, reasonForUpdate, fullName, AuditRecord.UPDATE_FORM, formDataAudit );
		//	updateFormItemGroupData(formData, studyId, status, active, reasonForUpdate, locationId, userId, operationType);
			
		} catch (Exception e) {
			throw new Exception("updateFormData:error in populate\n"+e.getCause());
		}

	}

	public void saveFormData(MetadataVersion mv,
			Long subjectDataId, StudyEventDef studyEventDef, Long studyEventDataId, FormDef formDef, Long formDataId,
			Long status, String reasonForUpdate,  Map<String, String> itemsDef, Map<String, String> itemsDisp, Map<String, String> itemsDirty,  String fullName, SignatureDef signatureDef, DBUser user, Location location) throws Exception {
		StudyEventData studyEventData = null;
		FormData formData= null;
		ItemData itemData = null;
		FormDataAudit formDataAudit = new FormDataAudit();

		if (formDataId != null)
			formData = findFormDataById(formDataId);


		try {
			if (studyEventDataId == null) {
				studyEventData = createStudyEventData(mv, subjectDataId, studyEventDef, "1", new Date(),  new Long(ElectronicRecord.ACTIVE_YES), fullName);
				studyEventDataId = studyEventData.getId();	
			} else if (studyEventDataId !=null)
				 studyEventData = this.findStudyEventDataById(studyEventDataId);
			if (formData == null ) {
				formData = createFormData(mv, studyEventData, formDef, "1", status, new Long(ElectronicRecord.ACTIVE_YES), fullName, formDataAudit);
				Long itemGroupRepeatKeyInt = new Long(1);
				// create item group
				for (ItemGroupRef itgr : formDef.getItemGroupRefList()) {
					ItemGroupDef itg = itgr.getItemGroupDef();
					ItemGroupDataAudit itemGroupDataAudit = new ItemGroupDataAudit();
					// Even if there is no field defined for the ItemGroupData, as we are in the form creation, we already create the itemgroupdata
					// In the other hand, if we change the status 
					ItemGroupData itgdata = addItemGroupData(mv, formData, formDataAudit, String.valueOf(itemGroupRepeatKeyInt), itg, status, fullName, itemGroupDataAudit);
					itemGroupRepeatKeyInt++;
					for (ItemRef ir : itg.getItemRefList()) {
						ItemDef id = ir.getItemDef();
						itemData = getExistingItemData( itgdata, id);		
						String value="";
						String codedValue= "";
						if (itemsDirty.get("item-dirty-"+itg.getId()+"-"+id.getId()).equals("t")) {			
							if (id.getCodeList() !=null) {
								codedValue =itemsDef.get("item-def-"+itg.getId()+"-"+id.getId());
								value = itemsDisp.get("item-display-"+itg.getId()+"-"+id.getId()+"-"+codedValue);

							} else
								value = itemsDef.get("item-def-"+itg.getId()+"-"+id.getId());
							addItemData( mv, itemGroupDataAudit,itgdata, id, new Long(ItemData.ISNULL_NO),codedValue, value, status, fullName);

						} else // new case
							addItemData( mv, itemGroupDataAudit, itgdata, id, new Long(ItemData.ISNULL_YES),"", "",  status,fullName);

					} // for

				} // end for
			} else { // form data is not null
				// Study event is not null as form data exists

				boolean statusChanged = formData.getStatus()==status ? false : true;
				boolean itemGroupDirty=false;
				updateFormData(mv, formData, studyEventData, formDef, null, status, null, fullName, reasonForUpdate,AuditRecord.UPDATE_FORM, formDataAudit);

				for (ItemGroupRef itgr : formDef.getItemGroupRefList()) {
					ItemGroupDef itg = itgr.getItemGroupDef();
					if (! statusChanged) {
						itemGroupDirty = false;
						for (ItemRef ir : itg.getItemRefList()) {
							if (itemsDirty.get("item-dirty-"+itg.getId()+"-"+ir.getItemDef().getId()).equals("t")) {
								itemGroupDirty =true;
								break;
							}
						} 
					} else itemGroupDirty = true;

					if (itemGroupDirty) {
						ItemGroupData curItg = null;
						for (ItemGroupData itgdata : formData.getItemGroupDataList()) {
							if (itgdata.getItemGroupDef().getId()==itg.getId()) {
								curItg = itgdata;
								break;
							}
						}
						ItemGroupDataAudit itemGroupDataAudit = new ItemGroupDataAudit();
						//itemGroupDataAudit.setFormDataAudit(formDataAudit);
						//formDataAudit.getItemGroupDataAuditList().add(itemGroupDataAudit);
						updateItemGroupData(mv, formDataAudit, curItg, curItg.getItemGroupRepeatKey(), status, fullName, reasonForUpdate, AuditRecord.UPDATE_ITEMGROUP, itemGroupDataAudit);
						for (ItemRef ir : itg.getItemRefList()) {
							ItemDef id = ir.getItemDef();
							itemData = getExistingItemData( curItg, id);		
							String value="";
							String codedValue= "";
							if (itemsDirty.get("item-dirty-"+itg.getId()+"-"+id.getId()).equals("t") ) {			
								if (id.getCodeList() !=null) {
									codedValue =itemsDef.get("item-def-"+itg.getId()+"-"+id.getId());
									value = itemsDisp.get("item-display-"+itg.getId()+"-"+id.getId()+"-"+codedValue);

								} else
									value = getItemDataValue(itg, id, itemsDef);//itemsDef.get("item-def-"+itg.getId()+"-"+id.getId());
								if (itemData == null ) 
									addItemData(mv, itemGroupDataAudit, curItg, id, new Long(ItemData.ISNULL_NO),codedValue, value, status, fullName);
								else
									updateItemData(mv, itemGroupDataAudit, itemData, new Long(ItemData.ISNULL_NO), codedValue, value,status, fullName, reasonForUpdate, AuditRecord.UPDATE_ITEM);
							} 

						} // for
					}	

				}

			}	
			if (status==FormData.STATUS_SIGNED) {
				// sign the form
				this.signFormData(formData, signatureDef, fullName, formDataAudit, user, location);
			}
		} catch (Exception e) {
			throw e;
		}


	}
	protected String getItemDataValue(ItemGroupDef itg, ItemDef id, Map<String, String> itemsDef  ) {
		if (id.getDataType().equals(ItemDef.DATATYPE_BOOLEAN)) {
			if (itemsDef.get("item-def-"+itg.getId()+"-"+id.getId()+"-yes").equals("checked")) return "t";
			if (itemsDef.get("item-def-"+itg.getId()+"-"+id.getId()+"-no").equals("checked")) return "f";		
			return "";
		} else {
			return itemsDef.get("item-def-"+itg.getId()+"-"+id.getId());
		}
	}
	public void backupItemGroupData(ItemGroupData igd) {
		igd.setOldActive(igd.getActive());
		igd.setOldStatus(igd.getStatus());
		igd.setOldStatusDate(igd.getStatusDate());
		igd.setOldItemGroupRepeatKey(igd.getItemGroupRepeatKey());
		
	}

	public void populateItemGroupData(MetadataVersion mv, FormDataAudit formDataAudit, ItemGroupData igd, String itemGroupRepeatKey, Long status, String fullName, String reasonForUpdate, int operationType, ItemGroupDataAudit igda) throws Exception {

		try {
			populateAuditChecks( fullName, 
					reasonForUpdate);
		} catch (Exception e) {
			throw new Exception("populateItemGroupData: AuditCheck failure");
		}
		
		if (igd.getItemGroupDef() == null) throw new Exception("populateItemGroupData:ItemGroupDef cannot be null");
		if (igd.getItemGroupRepeatKey() == null) throw new Exception("populateItemGroupData:ItemGroupRepeatKey cannot be null");
		this.backupItemGroupData(igd);
		igd.setActive(new Long(ElectronicRecord.ACTIVE_YES));
		igd.setStatusDate(new Date());
		igd.setItemGroupRepeatKey(itemGroupRepeatKey);
		// if it does not exist in the form => add it
			
		//igd.getItemGroupDef().getItemGroupDataList().add(igd);

		// TODO: maybe add those one. Access the location and user's subject
		// data without those one, if it works let like otherwise add them and
		// test
		// keep in mind that in such a situation we probably have to save, the
		// location and the user
	
		
		//igda.setFormDataAudit(formDataAudit);
		//formDataAudit.getItemGroupDataAuditList().add(igda);
		
		igda.setNewItemGroupRepeatKey(igd.getItemGroupRepeatKey());

		igda.setOldItemGroupRepeatKey(igd.getOldItemGroupRepeatKey());
		igda.setOldActive(igd.getOldActive());
		igda.setOldStatus(igd.getOldStatus());
		
		igda.setNewActive(igd.getActive());
		igda.setNewStatus(status);
		
		populateAudit(mv, igda, igd, fullName, reasonForUpdate, "", operationType);
		
	}
	protected void updateItemGroupData(MetadataVersion mv, FormDataAudit formDataAudit, ItemGroupData igd, String itemGroupRepeatKey, Long status, String fullName, String reasonForUpdate, int operationType, ItemGroupDataAudit itemGroupDataAudit ) throws Exception {
		try {
			igd.setStatusDate(new Date());
			populateItemGroupData(mv, formDataAudit, igd, itemGroupRepeatKey, status, fullName, reasonForUpdate,operationType, itemGroupDataAudit);
		}catch (Exception e) {
			throw new Exception("updateitemGroupData:Error in populating ItemGroup\n"+e.getCause());
		}
		
	}
	protected ItemGroupData addItemGroupData(MetadataVersion mv, FormData formData, FormDataAudit formDataAudit, String itemGroupRepeatKey, ItemGroupDef itemGroupDef, Long status, String fullName, ItemGroupDataAudit itemGroupDataAudit ) throws Exception {
		ItemGroupData igd = new ItemGroupData();
		igd.setItemGroupRepeatKey(itemGroupRepeatKey);
		igd.setItemGroupDef(itemGroupDef);
		itemGroupDef.getItemGroupDataList().add(igd);
		igd.setFormData(formData);
		formData.getItemGroupDataList().add(igd);
		igd.setFormData(formData);
		igd.setClinicalData(mv.getClinicalData());
		mv.getClinicalData().getElectronicRecordList().add(igd);
		try {
			populateItemGroupData(mv, formDataAudit, igd, itemGroupRepeatKey, status, fullName, AUDIT_ITEMGROUP_CREATION, AuditRecord.CREATE_ITEMGROUP, itemGroupDataAudit);
		}catch (Exception e) {
			throw new Exception("addItemGroupData:Error in link and populate");
		}
		
		return igd;
	}
	
	
	
	public void backupItemData(ItemData id) {
		id.setOldActive(id.getActive());
		id.setOldCodeListValue(id.getCodeListValue());
		id.setOldStatus(id.getStatus());
		id.setOldStatusDate(id.getStatusDate());
		id.setOldIsNull(id.getIsNull());
		id.setOldValue(id.getValue());
	}

	protected void populateItemData( MetadataVersion mv, ItemGroupDataAudit itemGroupDataAudit, ItemData id, Long isNull, String codeListValue, String value, Long status, String fullName, String reasonForUpdate, int operationType  ) throws Exception {
		// Store the old value
		try {
			populateAuditChecks( fullName, 
					reasonForUpdate);
		} catch (Exception e) {
			throw new Exception("populateItemData: fail the audit checks");
		}
		backupItemData(id);
		id.setActive(new Long(ElectronicRecord.ACTIVE_YES));
		id.setStatusDate(new Date());

		id.setDirty(true);
		
		id.setIsNull(isNull);
		id.setCodeListValue(codeListValue);
		id.setValue(value);
		
		ItemDataAudit ida = new ItemDataAudit();
		//ida.setItemGroupDataAudit(itemGroupDataAudit);
		//itemGroupDataAudit.getItemDataAuditList().add(ida);
		
		ida.setOldActive(id.getOldActive());
		ida.setOldCodeListValue(id.getOldCodeListValue());
		ida.setOldIsNull(id.getOldIsNull());
		ida.setOldStatus(id.getOldStatus());
		ida.setOldValue(id.getOldValue());
		
		ida.setNewActive(id.getActive());
		ida.setNewCodeListValue(id.getCodeListValue());
		ida.setNewIsnull(id.getIsNull());
		ida.setNewStatus(status);
		ida.setNewValue(id.getValue());
		populateAudit(mv, ida, id, fullName, reasonForUpdate, "", operationType);
		

		
	}

	protected void addItemData(MetadataVersion mv, ItemGroupDataAudit itemGroupDataAudit, ItemGroupData itemGroupData, ItemDef itemDef,  Long isNull, String codeListValue, String value, Long status, String fullName) throws Exception {
		ItemData id = new ItemData();
		id.setItemDef(itemDef);
		itemDef.getItemDataList().add(id);
		id.setItemGroupData(itemGroupData);	
		itemGroupData.getItemDataList().add(id);
		
		try {
			populateItemData(mv,itemGroupDataAudit, id, isNull, codeListValue, value, status, fullName, AUDIT_ITEM_CREATION, AuditRecord.CREATE_ITEM);
		} catch (Exception e) {
			throw new Exception("addItemData:Failed in populating item data");
		}
	}
	protected void updateItemData( MetadataVersion mv, ItemGroupDataAudit itemGroupDataAudit, ItemData itemData,  Long isNull, String codeListValue, String value, Long status, String fullName, String reasonForUpdate, int operationType) throws Exception {
		try {
			itemData.setStatusDate(new Date());
			populateItemData( mv, itemGroupDataAudit, itemData, isNull, codeListValue, value, status, fullName, reasonForUpdate, operationType);
		} catch (Exception e) {
			throw new Exception("updateItemData:Error in populating ItemData");
		}
		
	}
	
	// Signature Management
	public void signClinicalData(ElectronicRecord electronicRecord, SignatureDef signatureDef,
			String fullName, AuditRecord auditRecord, DBUser user, Location location) throws Exception {
		try {
	
			Signature signature = new Signature();
			signature.setElectronicRecord(electronicRecord);
			signature.setDateTimeStamp(new Date());
			signature.setSignatureDef(signatureDef);
			signature.setStatus(new Long(Signature.SIGNED));
			signature.setLocationRef(location);
			signature.setUserRef(user);

			electronicRecord.getSignatureList().add(signature);

			// TODO: maybe add those one. Access the location and user's subject
			// data without those one, if it works let like otherwise add them
			// and test
			// keep in mind that in such a situation we probably have to save,
			// the location and the user
			// user.getSubjectDataList().add(subjectData);
			// location.getSubjectDataList().add(subjectData);
			if (auditRecord != null) {
				auditRecord.setSignator(fullName);
				//auditRecord.setSignLocation(ControllerAdvice.getFormattedLocation(location.getShortName(), location.getName()));
				auditRecord.setSignStatus(new Long(Signature.SIGNED));
			}
				
			electronicRecordDao.save(electronicRecord);
			
		} catch (Exception e) {
			throw new Exception("signClinicalData : error in signing");
		}

	}
	public void signFormData(FormData formData, SignatureDef signatureDef, String fullName, AuditRecord auditRecord, DBUser user, Location location) throws Exception{

		signClinicalData(formData, signatureDef, fullName, auditRecord, user, location);
	}
	
	public List<SubjectData> findSubjectDataList(ClinicalData clinicalData) {
		List<SubjectData> subjectDataList = new ArrayList<SubjectData>();
		for (ElectronicRecord er : clinicalData.getElectronicRecordList() )
			if ( er instanceof SubjectData ) subjectDataList.add((SubjectData) er);
		return subjectDataList;
	}

	
}
