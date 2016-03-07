package ro.manoli.persistence.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ro.manoli.persistence.model.PersistableEntity;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "DEPARTMENT")
public class Department extends PersistableEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
