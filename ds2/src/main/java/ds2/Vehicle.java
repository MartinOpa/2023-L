package ds2;

public class Vehicle {
	
	private String VIN;
	private int User_id;
	private String licensePlate;
	private String manufacturer;
	private String model;
	private int year;
	private String chassis;
	private boolean valid;
	
	public Vehicle() {}

	public String getVIN() {
		return this.VIN;
	}
	
	public int getUser_id() {
		return this.User_id;
	}
	
	public String getLicensePlate() {
		return this.licensePlate;
	}
	
	public String getManufacturer() {
		return this.manufacturer;
	}
	
	public String getModel() {
		return this.model;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public String getChassis() {
		return this.chassis;
	}
	
	public boolean getValid() {
		return this.valid;
	}
	
	private Vehicle(VehicleBuilder builder) {
		this.VIN = builder.VIN;
		this.User_id = builder.User_id;
		this.licensePlate = builder.licensePlate;
		this.manufacturer = builder.manufacturer;
		this.model = builder.model;
		this.year = builder.year;
		this.chassis = builder.chassis;
		this.valid = builder.valid;
	}
	
	public static class VehicleBuilder {
		private String VIN;
		private int User_id;
		private String licensePlate;
		private String manufacturer;
		private String model;
		private int year;
		private String chassis;
		private boolean valid;
		
		public VehicleBuilder(String VIN, int User_id, String licensePlate, String manufacturer,
							  String model, int year, boolean valid) {
			
			this.VIN = VIN;
			this.User_id = User_id;
			this.licensePlate = licensePlate;
			this.manufacturer = manufacturer;
			this.model = model;
			this.year = year;
			this.valid = valid;
		}
		
		public VehicleBuilder chassis(String chassis) {
			this.chassis = chassis;
			return this;
		}
		
		public Vehicle build() {
			Vehicle vehicle = new Vehicle(this);
			return vehicle;
		}
	}
}
