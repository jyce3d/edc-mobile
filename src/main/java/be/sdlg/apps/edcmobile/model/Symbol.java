package be.sdlg.apps.edcmobile.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="SYMBOLS")
@PrimaryKeyJoinColumn(name="TRANSLATABLE_ID")
public class Symbol extends Translatable {
	protected MeasurementUnit measurementUnit;
	@ManyToOne
	public MeasurementUnit getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(MeasurementUnit measurementUnit) {
		this.measurementUnit = measurementUnit;
	}
	
}