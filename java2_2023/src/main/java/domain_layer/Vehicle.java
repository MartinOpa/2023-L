package domain_layer;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;

@PersistenceUnit(unitName="domain_layer")
@Entity
@Table(name="Vehicle")
@NoArgsConstructor
public class Vehicle implements Serializable {	
	@Transient
	private static final long serialVersionUID = -3164816681785901496L;
	
	@Column(name = "login", nullable = false)
    private String login;
	@Column(name = "make", nullable = false)
    private String make;
	@Column(name = "model", nullable = false)
    private String model;
	@Column(name = "year", nullable = false)
    private String year;
	@Id
	@Column(name = "vin", nullable = false)
    private String vin;
	@Column(name = "plate", nullable = false)
    private String plate;
	
    public Vehicle(String login, String make, String model, String year, String vin, String plate) {
        this.login = login;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.plate = plate;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public String getMake() {
        return this.make;
    }
    
    public String getModel() {
        return this.model;
    }
    
    public String getYear() {
        return this.year;
    }
    
    public String getVin() {
        return this.vin;
    }
    
    public String getPlate() {
        return this.plate;
    }
}
