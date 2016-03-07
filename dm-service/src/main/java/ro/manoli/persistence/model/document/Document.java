package ro.manoli.persistence.model.document;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ro.manoli.persistence.model.PersistableEntity;
import ro.manoli.persistence.model.abe.Attribute;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "DOCUMENT")
public class Document extends PersistableEntity {
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private Calendar uploadDate;
	private List<Attribute> attributes;
	private Dossier dossier;
	
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
	
	@Column(name = "UPLOAD_DATE")
	public Calendar getUploadDate() {
		return uploadDate;
	}
	
	public void setUploadDate(Calendar uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	@OneToMany(mappedBy = "document")
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DOSSIER")
	public Dossier getDossier() {
		return dossier;
	}
	
	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
	}
}
