package ro.manoli.persistence.model.identity;

import javax.persistence.Entity;
import javax.persistence.Table;

import ro.manoli.persistence.model.PersistableEntity;
import ro.manoli.persistence.model.localization.City;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "ADDRESS")
public class Address extends PersistableEntity {
	private static final long serialVersionUID = 1L;
	
	private String street;
	private String no;
	private String building;
	private Integer floor;
	private String apartment;
	private String postalCode;
	private City city;
}
