package domain_layer;

import java.io.Serializable;

import org.hibernate.annotations.Columns;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import lombok.NoArgsConstructor;



@NoArgsConstructor
@PersistenceUnit(unitName="domain_layer")
@Entity
@Table(name="User")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Serializable {
	
	private static final long serialVersionUID = -2962707267933344391L;
	//@Id
	//@GeneratedValue (strategy = GenerationType.IDENTITY)
	//@Column(name = "Id", unique = true)
	@Transient
    int ID;
	@Column(name = "accountType", nullable = false)
    int accountType;
	@Id
	@Column(name = "login", nullable = false)
    String login;
	@Column(name = "firstName", nullable = false)
    String firstName;
	@Column(name = "lastName", nullable = false)
    String lastName;
	@Embedded
	@Columns(columns = { @Column(name = "street"), @Column(name = "city"), @Column(name = "postalCode") })
    Address address;
	@Column(name = "phone", nullable = false)
    int phone;

    int getAccountType() {
        return this.accountType;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public String getLogin() {
        return this.login;
    }

    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public Address getAddress() {
        return this.address;
    }
    
    public int getPhone() {
        return this.phone;
    }
    
    public void setData(String firstName, String lastName, Address address, int phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }
}