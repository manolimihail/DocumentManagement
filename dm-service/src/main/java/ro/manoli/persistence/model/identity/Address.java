package ro.manoli.persistence.model.identity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	private City city;
	private String street;
	private String no;
	private String building;
	private Integer floor;
	private String apartment;
	private String postalCode;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_CITY")
	public City getCity() {
		return city;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	@Column(name = "STREET")
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Column(name = "NUMBER")
	public String getNo() {
		return no;
	}
	
	public void setNo(String no) {
		this.no = no;
	}
	
	@Column(name = "BUILDING")
	public String getBuilding() {
		return building;
	}
	
	public void setBuilding(String building) {
		this.building = building;
	}
	
	@Column(name = "FLOOR")
	public Integer getFloor() {
		return floor;
	}
	
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	
	@Column(name = "APARTMENT")
	public String getApartment() {
		return apartment;
	}
	
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
	
	@Column(name = "POSTAL_CODE")
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
