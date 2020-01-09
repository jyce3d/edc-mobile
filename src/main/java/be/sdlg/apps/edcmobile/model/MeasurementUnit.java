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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="MEASUREMENT_UNITS")
public class MeasurementUnit {
	protected Long id;
	protected String name;
	protected Set<Symbol> symbolList;
	protected ItemDef itemDef;
	protected Set<RangeCheck> rangeCheckList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="measurementUnit", fetch=FetchType.LAZY)
	public Set<RangeCheck> getRangeCheckList() {
		return rangeCheckList;
	}	
	public void setRangeCheckList(Set<RangeCheck> rangeCheckList) {
		this.rangeCheckList = rangeCheckList;
	}
	@ManyToOne
	public ItemDef getItemDef() {
		return itemDef;
	}
	public void setItemDef(ItemDef itemDef) {
		this.itemDef = itemDef;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MEASUREMENT_UNIT_ID")
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
	@OneToMany(cascade = CascadeType.ALL, mappedBy="measurementUnit", fetch=FetchType.LAZY)
	public Set<Symbol> getSymbolList() {
		if (symbolList == null) symbolList = new HashSet<Symbol>(0);
		return symbolList;
	}
	public void setSymbolList(Set<Symbol> symbolList) {
		this.symbolList = symbolList;
	}
	
}