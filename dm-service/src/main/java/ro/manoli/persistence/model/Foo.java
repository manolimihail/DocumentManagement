package ro.manoli.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Mihail
 *
 */
@Entity
public class Foo implements Serializable {

    private static final long serialVersionUID = 1L;

    public Foo() {
    }

    public Foo(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
   
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "LASTNAME")
    private String lastName;

    @ManyToOne(targetEntity = Bar.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "BAR_ID")
    private Bar bar;

    public Bar getBar() {
        return bar;
    }

    public void setBar(final Bar bar) {
        this.bar = bar;
    }

    public long getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    
    public String getLastName() {
		return lastName;
	}
    
    public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Foo other = (Foo) obj;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Foo [name=").append(name).append(", ").append(lastName).append("]");
        return builder.toString();
    }
}
