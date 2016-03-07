package ro.manoli.persistence.model.identity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ro.manoli.persistence.model.PersistableEntity;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "PERSON")
public class Person extends PersistableEntity {
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private Address address;
	private Date birthdate;
	private String email;
	
}
