package ro.manoli.persistence.model.company;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ro.manoli.persistence.model.security.User;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "APP_USER")
@DiscriminatorValue("1")
public class ApplicationUser extends User {
	private static final long serialVersionUID = 1L;
	
	private Department department;

	@ManyToOne
	@JoinColumn(name = "FK_DEPARTMENT")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
