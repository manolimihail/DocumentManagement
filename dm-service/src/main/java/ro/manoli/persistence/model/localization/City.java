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
@Table(name = "CITY")
public class City extends PersistableEntity {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private County county;
}
