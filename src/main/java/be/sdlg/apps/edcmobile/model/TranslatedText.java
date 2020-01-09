package be.sdlg.apps.edcmobile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TRANSLATED_TEXT")
public class TranslatedText {
	protected Long id;
	protected Translatable decodable;
	protected String translatedText;
	protected String languageTag;
	@ManyToOne
	public Translatable getDecodable() {
		return decodable;
	}
	public void setDecodable(Translatable decodable) {
		this.decodable = decodable;
	}
	public String getTranslatedText() {
		return translatedText;
	}
	public void setTranslatedText(String translatedText) {
		this.translatedText = translatedText;
	}
	public String getLanguageTag() {
		return languageTag;
	}
	public void setLanguageTag(String languageTag) {
		this.languageTag = languageTag;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TRANSLATEED_TEXT_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}