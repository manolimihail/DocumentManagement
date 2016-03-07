package ro.manoli.persistence.model.localization;

import javax.persistence.Entity;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
