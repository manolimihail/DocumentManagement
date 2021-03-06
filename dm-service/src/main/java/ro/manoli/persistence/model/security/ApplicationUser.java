package ro.manoli.persistence.model.security;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import ro.manoli.persistence.model.company.Department;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "APP_USER")
public class ApplicationUser extends User {
	private static final long serialVersionUID = 1L;

	public ApplicationUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	private Long id;
	private Department department;

	@Id
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "FK_DEPARTMENT")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
