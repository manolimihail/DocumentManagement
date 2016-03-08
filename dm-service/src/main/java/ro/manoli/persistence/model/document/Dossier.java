package ro.manoli.persistence.model.document;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ro.manoli.persistence.model.PersistableEntity;

/**
 * 
 * @author Mihail
 *
 */
public class Dossier extends PersistableEntity {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private Calendar creationDate;
	private Calendar modifiedDate;
	private List<Document> documents;
	private DossierState state;
	
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
	
	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@OneToMany(mappedBy = "dossier")
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public DossierState getState() {
		return state;
	}
	
	public void setState(DossierState state) {
		this.state = state;
	}
}
