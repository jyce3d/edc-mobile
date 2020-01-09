package be.sdlg.apps.edcmobile.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.sdlg.apps.edcmobile.model.SignatureDef;

public interface SignatureDefRepository extends CrudRepository<SignatureDef, Long> {
	@Query("SELECT sd from be.sdlg.apps.edcmobile.model.SignatureDef sd join sd.adminData ad where ad.study.id=:studyId and sd.signatureUsage = :signatureUsage")
	public List<SignatureDef> findSignatureDefByStudyId(@Param("studyId") Long studyId, @Param("signatureUsage") Long signatureUsage);

}
