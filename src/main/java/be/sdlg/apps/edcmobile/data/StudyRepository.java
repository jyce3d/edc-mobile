package be.sdlg.apps.edcmobile.data;

import org.springframework.data.repository.CrudRepository;

import be.sdlg.apps.edcmobile.model.Study;

public interface StudyRepository extends CrudRepository<Study, Long> {

}
