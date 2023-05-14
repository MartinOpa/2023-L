package domain_layer;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;

@PersistenceUnit(unitName="domain_layer")
@NoArgsConstructor
@Entity
@Table(name="Reservation")
public class Reservation implements Serializable {
	@Transient
	private static final long serialVersionUID = -1420405119626390558L;
	
	@Id
	@Column(name = "Id", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name = "login", nullable = false)
    private String login;
	@Column(name = "dateTime", nullable = false)
    private String dateTime;
	@Transient
    private Vehicle vehicle;
	@Column(name = "vin", nullable = false)
    private String vin;
	@Column(name = "issue", nullable = false)
    private String issue;
    
    public Reservation(String loginInput, String dateTime, Vehicle vehicle, String issueDescription) {
        this.login = loginInput;
        this.dateTime = dateTime;
        this.vehicle = vehicle;
        this.vin = vehicle.getVin();
        this.issue = issueDescription;
    }
    
    public Reservation(String loginInput, String dateTime, String vin, String issueDescription) {
        this.login = loginInput;
        this.dateTime = dateTime;
        this.vin = vin;
        this.issue = issueDescription;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public String getDateTime() {
        return this.dateTime;
    }
    
    public Vehicle getVehicle() {
        return this.vehicle;
    }
    
    public String getVin() {
        return this.vin;
    }
    
    public String getIssue() {
        return this.issue;
    }
}
