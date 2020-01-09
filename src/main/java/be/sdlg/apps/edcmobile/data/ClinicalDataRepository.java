package be.sdlg.apps.edcmobile.data;
import org.springframework.data.repository.CrudRepository;

import be.sdlg.apps.edcmobile.model.ClinicalData;

public interface ClinicalDataRepository extends CrudRepository<ClinicalData, Long> {

}
