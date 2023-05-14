package ds2;

import java.time.LocalDate;
import java.util.List;

public class User {
	public enum Type {
		admin, staff, partner, client;
		
		public static Type byInt(int x) {
	        switch(x) {
	        case 0:
	            return admin;
	        case 1:
	            return staff;
	        case 2:
	            return partner;
	        case 3:
	            return client;
	        }
	        return null;
	    }
	}
	
	private int id;
	private String login;
	private String firstName;
	private String lastName;
	private int phoneNumber;
	private String address;
	private String email;
	private Type type;
	private LocalDate partnerDate;
	private double discount;
	private boolean active;
	
	private List<Vehicle> vehicles;
	private List<Reservation> reservations;
	
	public User() {}

	public int getId() {
		return this.id;
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

	public int getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public String getEmail() {
		return this.email;
	}

	public Type getType() {
		return this.type;
	}

	public LocalDate getPartnerDate() {
		return this.partnerDate;
	}

	public double getDiscount() {
		return this.discount;
	}

	public boolean getActive() {
		return this.active;
	}

	public List<Vehicle> getVehicles() {
		return this.vehicles;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	private User(UserBuilder builder) {
		this.id = builder.id;
		this.login = builder.login;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
		this.type = builder.type;
		this.active = builder.active;
		
		this.phoneNumber = builder.phoneNumber;
		this.address = builder.address;
		this.partnerDate = builder.partnerDate;
		this.discount = builder.discount;
	}
	
	public static class UserBuilder {
		
		private int id;
		private String login;
		private String firstName;
		private String lastName;
		private int phoneNumber;
		private String address;
		private String email;
		private Type type;
		private LocalDate partnerDate;
		private double discount;
		private boolean active;
		
		public UserBuilder phoneNumber(int phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}
		
		public UserBuilder address(String address) {
			this.address = address;
			return this;
		}
		
		public UserBuilder partnerDate(LocalDate partnerDate) {
			this.partnerDate = partnerDate;
			return this;
		}
		
		public UserBuilder discount(double discount) {
			this.discount = discount;
			return this;
		}
		
		public UserBuilder id(int id) {
			this.id = id;
			return this;
		}
		
		public UserBuilder (String login, String firstName, String lastName,
								String email, Type type, boolean active) {
			this.login = login;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.type = type;
			this.active = active;
		}
		
		public User build() {
			User user =  new User(this);
			return user;
		}
		
	}

}
