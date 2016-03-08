package ro.manoli.persistence.model.localization;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ro.manoli.persistence.model.PersistableEntity;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "COUNTRY")
public class Country extends PersistableEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	private List<County> counties;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "country")
	public List<County> getCounties() {
		return counties;
	}
	
	public void setCounties(List<County> counties) {
		this.counties = counties;
	}
}
