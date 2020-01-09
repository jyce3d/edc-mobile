package be.sdlg.apps.edcmobile.data;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import be.sdlg.apps.edcmobile.model.MetadataVersion;



public interface MetadataVersionRepository extends CrudRepository<MetadataVersion, Long>  {
	
	 @Query("SELECT mv from be.sdlg.apps.edcmobile.model.MetadataVersion mv join mv.study s where s.id = :studyId order by version desc")
	 public Set<MetadataVersion> find(@Param("studyId") Long studyId);
}
