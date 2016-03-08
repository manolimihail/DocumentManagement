package ro.manoli.persistence.model.localization;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ro.manoli.persistence.model.PersistableEntity;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "CITY")
public class City extends PersistableEntity {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private County county;
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_COUNTY")
	public County getCounty() {
		return county;
	}
	
	public void setCounty(County county) {
		this.county = county;
	}
}
