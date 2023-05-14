package ds2_data_layer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ds2.Reservation;
import ds2.Reservation.Payment;
import ds2.Reservation.ReservationBuilder;
import ds2.Vehicle;

public class ReservationDao {
	
	private static String getReservations =
			"SELECT id, RT_datetime, User_id, Vehicle_VIN, task, taskdescription, "
			+ "apxduration, discount, bill, payment, paid "
			+ "FROM Reservation";
	
	private static String getReservationById = " WHERE id = ? ";
	
	public static void insertReservation(Reservation res) {
		try( Connection con = ConnectionProvider.getConnection()) {
			CallableStatement cstmt = con.prepareCall("call insertReservation(?, ?, ?, ?, ?, ?)");

			java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(res.getRT_Datetime());
			cstmt.setTimestamp(1, timestamp);
			cstmt.setInt(2, res.getUserId());
			cstmt.setString(3, res.getVehicle().getVIN());
			cstmt.setString(4, res.getTask());
			cstmt.setString(5, res.getTaskDescription());
			cstmt.setBoolean(6, false);
	        cstmt.execute();
			
	        con.close();
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	} 
	
	/*
	public static void insertReservation(Reservation res) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(insertReservation);
			
			java.sql.Date date = java.sql.Date.valueOf(res.getRT_Datetime().toLocalDate());
					
			pstm.setDate(1, date);
	        pstm.setInt(2, res.getUserId());
	        pstm.setString(3, res.getVehicle().getVIN());
	        pstm.setString(4, res.getTask());
	        pstm.setString(5, res.getTaskDescription());
			pstm.setNull(6, Types.NULL);		
	        if (res.getDiscount() == 0) {
				pstm.setNull(7, Types.NULL);;
			} else {
				pstm.setDouble(7, res.getDiscount());
			}   
	        if (res.getBill() == 0) {
				pstm.setNull(8, Types.NULL);;
			} else {
				pstm.setDouble(8, res.getBill());
			} 		
	        pstm.setInt(9, res.getPayment().ordinal());
	        pstm.setBoolean(10, res.getPaid());
	        pstm.execute();
	        
	        con.close();
		
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}*/
	
	public static List<Reservation> getReservations() {
		List<Reservation> res = new LinkedList<>();
        try(Connection con = ConnectionProvider.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getReservations);
            while(rs.next()) {
               int id = rs.getInt(1);
               LocalDateTime convertedDate = rs.getTimestamp(2).toLocalDateTime();
               int User_id = rs.getInt(3);
               Vehicle vehicle = VehicleDao.getVehicleByVin(rs.getString(4));
               String task = rs.getString(5);
               String taskdescription = rs.getString(6);
               if (rs.getTimestamp(7) != null) {
            	   LocalDateTime apxduration = rs.getTimestamp(7).toLocalDateTime();
               }
               double discount = rs.getDouble(8);
               double bill = rs.getDouble(9);
               int payment = rs.getInt(10);
               boolean paid = rs.getBoolean(11);
    
               res.add(											
            		new ReservationBuilder(id, User_id, convertedDate, vehicle, task, paid)
            		.payment(Payment.byInt(payment))
            		.bill(bill)
            		.discount(discount)
            		.taskDescription(taskdescription)
            		.build()
            	);
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return res;
	}
	
	/*public static List<Reservation> getReservationsFiltered(String keyword, LocalDateTime dt, boolean current) {
		List<Reservation> res = new LinkedList<>();
		int cur;
		if (current) {
			cur = 1;
		} else cur = 0;
		
        try(Connection con = ConnectionProvider.getConnection()) {
        	CallableStatement cstmt = con.prepareCall("{? = call getReservationList(?, ?, ?)}");
        	cstmt.registerOutParameter(1, Types.REF_CURSOR);
        	if (keyword != null) {
        		cstmt.setString(2, keyword);
        	} else cstmt.setNull(2, Types.NULL);
        	
        	java.sql.Date date;
        	if (dt != null) {
        		date = java.sql.Date.valueOf(dt.toLocalDate());
        		cstmt.setDate(3, date);
        	} else cstmt.setNull(3, Types.NULL);
        	
	        cstmt.setInt(4, cur);
	        
            cstmt.executeQuery();
            ResultSet rs = (ResultSet) cstmt.getObject(1);
            while(rs.next()) {
               int id = rs.getInt(1);
               LocalDateTime convertedDate = rs.getTimestamp(2).toLocalDateTime();
               int User_id = rs.getInt(3);
               Vehicle vehicle = VehicleDao.getVehicleByVin(rs.getString(4));
               String task = rs.getString(5);
               String taskdescription = rs.getString(6);
               LocalDateTime apxduration = rs.getTimestamp(7).toLocalDateTime();
               double discount = rs.getDouble(8);
               double bill = rs.getDouble(9);
               int payment = rs.getInt(10);
               boolean paid = rs.getBoolean(11);
    
               res.add(											
            		new ReservationBuilder(id, User_id, convertedDate, vehicle, task, paid)
            		.payment(Payment.byInt(payment))
            		.bill(bill)
            		.discount(discount)
            		.taskDescription(taskdescription)
            		.build()
            	);
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return res;
	}*/
	
	public static Reservation getReservationById(int pID) {
		Reservation res = new Reservation();
        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(getReservations + getReservationById);
            stmt.setInt(1, pID);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
               int id = rs.getInt(1);
               LocalDateTime convertedDate = rs.getTimestamp(2).toLocalDateTime();
               int User_id = rs.getInt(3);
               Vehicle vehicle = VehicleDao.getVehicleByVin(rs.getString(4));
               String task = rs.getString(5);
               String taskdescription = rs.getString(6);
               if (rs.getTimestamp(7) != null) {
            	   LocalDateTime apxduration = rs.getTimestamp(7).toLocalDateTime();
               }               
               double discount = rs.getDouble(8);
               double bill = rs.getDouble(9);
               int payment = rs.getInt(10);
               boolean paid = rs.getBoolean(11);
               res =												
            		new ReservationBuilder(id, User_id, convertedDate, vehicle, task, paid)
            		.payment(Payment.byInt(payment))
            		.bill(bill)
            		.discount(discount)
            		.taskDescription(taskdescription)
            		.build();
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return res;
	}

}
