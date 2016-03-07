package ro.manoli.persistence.model.company;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ro.manoli.persistence.model.PersistableEntity;
import ro.manoli.persistence.model.identity.Address;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "COMPANY")
public class Company extends PersistableEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private Address address;
	private List<Department> departments;
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "DESCRIPTION")
	@Lob
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@OneToMany(mappedBy = "company")
	public List<Department> getDepartments() {
		return departments;
	}
	
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
}
