package be.sdlg.apps.edcmobile.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/***
 * Non CDISC base class with the purpose to replace : description, question and decode CDISC
 * entities
 * @author Jean-Yves
 * @version 1.0 July 2013
 */
@Entity
@Table(name="TRANSLATABLE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Translatable {
	protected Long id;
	protected Set<TranslatedText> translatedTextList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="decodable", fetch=FetchType.LAZY)
	public Set<TranslatedText> getTranslatedTextList() {
		if (translatedTextList == null) translatedTextList= new HashSet<TranslatedText>(0);
		return translatedTextList;
	}

	public void setTranslatedTextList(Set<TranslatedText> translatedTextList) {
		this.translatedTextList = translatedTextList;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TRANSLATABLE_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}