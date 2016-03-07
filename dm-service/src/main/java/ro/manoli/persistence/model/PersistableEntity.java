package ro.manoli.persistence.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base class for entities.
 * 
 * @author Mihail
 *
 */
@MappedSuperclass
public class PersistableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long id;
	private Long version;
	private String systemIdentifier;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "VERSION")
	@Version
	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}
	
	@Column(name = "SYSTEM_IDENTIFIER", unique = true)
	public String getSystemIdentifier() {
		return systemIdentifier;
	}
	
	public void setSystemIdentifier(String systemIdentifier) {
		this.systemIdentifier = systemIdentifier;
	}
}
