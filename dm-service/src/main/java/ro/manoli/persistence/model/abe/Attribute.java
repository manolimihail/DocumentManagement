package ro.manoli.persistence.model.abe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ro.manoli.persistence.model.PersistableEntity;
import ro.manoli.persistence.model.document.Document;

/**
 * 
 * @author Mihail
 *
 */
@Entity
@Table(name = "ATTRIBUTE")
public class Attribute extends PersistableEntity {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Document document;

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DOCUMENT")
	public Document getDocument() {
		return document;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
}
