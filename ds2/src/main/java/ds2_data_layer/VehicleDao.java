package ds2_data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ds2.Vehicle;
import ds2.Vehicle.VehicleBuilder;

public class VehicleDao {

	private static String insertVehicle = 
			"insert into Vehicle (VIN, User_id, "
			+ "licenseplate, manufacturer, model,"
			+ " year, chassis, valid)"
			+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static String getVehicles =
			"SELECT VIN, User_id, licenseplate, manufacturer, model,"
			+ " year, chassis, valid FROM Vehicle";
	
	private static String getVehicleByVIN =
			" WHERE VIN = ?";
	
	private static String updateVehicle =
			"UPDATE Vehicle "
			+ "SET User_id = ?, licenseplate = ?, manufacturer = ?, "
			+ "model = ?, year = ?, chassis = ?, valid = ? "
			+ "WHERE VIN = ?";
	
	private static String setInvalidVeh =
			"UPDATE Vehicle "
			+ "SET valid = ? "
			+ "WHERE VIN = ?";

	public static void insertVehicle(Vehicle vehicle) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(insertVehicle);
			
			pstm.setString(1, vehicle.getVIN());
			pstm.setInt(2, vehicle.getUser_id());
			pstm.setString(3, vehicle.getLicensePlate());
			pstm.setString(4, vehicle.getManufacturer());
			pstm.setString(5, vehicle.getModel());
			pstm.setInt(6, vehicle.getYear());
			pstm.setString(7, vehicle.getChassis());
			pstm.setBoolean(8, vehicle.getValid());
			
	        pstm.execute();
	        
	        con.close();
		
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
	public static List<Vehicle> getUsers() {
		List<Vehicle> vehicles = new LinkedList<>();
        try(Connection con = ConnectionProvider.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getVehicles);
            while(rs.next()) {
	        	 String VIN = rs.getString(1);
	             int User_id = rs.getInt(2);
	             String licensePlate = rs.getString(3);
	             String manufacturer = rs.getString(4);
	             String model = rs.getString(5);
	             int year = rs.getInt(6);
	             String chassis = rs.getString(7);
	             boolean valid = rs.getBoolean(8);
                             
	             vehicles.add(
            		   new VehicleBuilder(VIN, User_id, licensePlate, manufacturer, model, year, valid)
               		.chassis(chassis)
               		.build()
            	);
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return vehicles;
	}
	
	public static Vehicle getVehicleByVin(String pVIN) {
		Vehicle vehicle = new Vehicle();
        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(getVehicles + getVehicleByVIN);
            stmt.setString(1, pVIN);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               String VIN = rs.getString(1);
               int User_id = rs.getInt(2);
               String licensePlate = rs.getString(3);
               String manufacturer = rs.getString(4);
               String model = rs.getString(5);
               int year = rs.getInt(6);
               String chassis = rs.getString(7);
               boolean valid = rs.getBoolean(8);
                             
               vehicle = new VehicleBuilder(VIN, User_id, licensePlate, manufacturer, model, year, valid)
            		.chassis(chassis)
            		.build();
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return vehicle;
	}
	
	public static void updateVehicle(Vehicle vehicle) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(updateVehicle);
			
			pstm.setInt(1, vehicle.getUser_id());
			pstm.setString(2, vehicle.getLicensePlate());
			pstm.setString(3, vehicle.getManufacturer());
			pstm.setString(4, vehicle.getModel());
			pstm.setInt(5, vehicle.getYear());
			pstm.setString(6, vehicle.getChassis());
			pstm.setBoolean(7, vehicle.getValid());
			pstm.setString(8, vehicle.getVIN());
	        pstm.execute();
			
	        con.close();
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
	public static void setInvalid(Vehicle vehicle) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(setInvalidVeh);
					
			pstm.setBoolean(1, false);
			pstm.setString(2, vehicle.getVIN());
	        pstm.execute();
			
	        con.close();
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
}
