package domain_layer;

import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import data_layer.ClientMapper;
import data_layer.ReservationsMapper;
import data_layer.VehicleMapper;
import lombok.extern.log4j.Log4j2;
import presentation_layer.App;


@Log4j2
@PersistenceUnit(unitName="domain_layer")
@Entity
@Table(name="Client")
public class Client extends User {	
	@Transient
	static Logger log = LogManager.getLogger(Client.class);	
	
	@Transient
	@PersistenceContext(unitName="domain_layer")
    EntityManager em;
	
	@Transient
	private static final long serialVersionUID = -2387152527670832303L;
	@Transient
	private ClientMapper ClientDB;
	@Transient
    private ReservationsMapper ReservationDB;
	@Transient
    private VehicleMapper VehicleDB;
	@Transient
    private List<Vehicle> VehicleList;
	@Transient
    private List<Reservation> ReservationList;
	@Transient
    private LoginValidation LoginValidation;
    
    public Client() {
        this.LoginValidation = new LoginValidation();
        log.log(Level.INFO, "Client logging in");
    }
    
    public Client(int ID, String login, String firstName, String lastName, Address address, int phone) {
        this.ID = ID;
        this.accountType = 0;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.ClientDB = new ClientMapper();
        this.ReservationDB = new ReservationsMapper();
        this.VehicleDB = new VehicleMapper();
        this.LoginValidation = new LoginValidation();
        log.log(Level.INFO, "Client logged in");
    }
    
    public ClientMapper getClientDB() {
        return this.ClientDB;
    }
    
    public ReservationsMapper getReservationDB() {
        return this.ReservationDB;
    }
    
    public VehicleMapper getVehicleDB() {
        return this.VehicleDB;
    }
    
    public void setVehicleList(Client client) {
        this.VehicleList = client.getVehicleDB().load(client);
    }
    
    public List<Vehicle> getVehicleList() {
        return this.VehicleList;
    }
    
    public void setReservationList(Client client) {
        this.ReservationList = client.getReservationDB().load(client);
    }
    
    public List<Reservation> getReservationList() {
        return this.ReservationList;
    }
    
    public boolean checkIfAvailable(String dateTime) {
        return this.ReservationDB.checkIfAvailable(dateTime);
    }
    
    public void save(Client client) {       
        this.ClientDB.save(client);
        
        em = App.ENTITY_MANAGER_FACTORY.createEntityManager();                
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            em.persist(client);
            et.commit();
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            log.error("Error when persisting client", ex);
        } finally {
            em.close();
            log.log(Level.INFO, "Client info updated");
        }
    }
    
    public void saveRes(Reservation reservation) {
        this.ReservationDB.save(reservation);
        
        em = App.ENTITY_MANAGER_FACTORY.createEntityManager();                
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            em.persist(reservation);
            et.commit();
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            log.error("Error when persisting reservation", ex);
        } finally {
            em.close();
            log.log(Level.INFO, "Reservation saved");
        }
    }
    
    public void saveVeh(Vehicle vehicle) {
        this.VehicleDB.save(vehicle);
        
        em = App.ENTITY_MANAGER_FACTORY.createEntityManager();                
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            em.persist(vehicle);
            et.commit();
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            log.error("Error when persisting vehicle", ex);
        } finally {
            em.close();
            log.log(Level.INFO, "Vehicle saved");
        }
    }

    public List<Vehicle> filter(Client client, String parameter) {
        return this.VehicleDB.filter(client, parameter);
    }
    
    public List<Reservation> filter(String parameter, Client client) {
        return this.ReservationDB.filter(parameter, client);
    }
    
    public boolean checkCredentials(String loginInput, String passwordInput) {
        return this.LoginValidation.checkCredentials(loginInput, passwordInput);
    }
    
    public int checkDuplicateLogin(String loginInput) {
        return this.LoginValidation.checkDuplicateLogin(loginInput);
    }
    
    public void storeUser(int ID, String login, String password) {
        this.LoginValidation.storeUser(ID, login, password);
    }
    
    public boolean tryPasswordReset(String loginInput, String newpassword) {
        return this.LoginValidation.tryPasswordReset(loginInput, newpassword);
    }
}
