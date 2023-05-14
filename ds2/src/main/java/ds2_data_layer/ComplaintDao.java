package ds2_data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ds2.Complaint;

public class ComplaintDao {

	private static String insertComplaint = 
			"insert into Complaint (Reservation_id, complaint,"
			+ "resolved, complaintdate)"
			+ "values (?, ?, ?, ?)";
	
	private static String getComplaint =
			"SELECT Reservation_id, complaint, resolved, complaintdate "
			+ "FROM Complaint WHERE Reservation_id = ?";
	
	private static String updateComplaint =
			"UPDATE Complaint "
			+ "SET complaint = ?, resolved = ? "
			+ "WHERE Reservation_id = ?";
	
	private static String deleteComplaint = 
			"DELETE FROM Complaint WHERE Reservation_id = ?";

	public static void insertComplaint(Complaint complaint, int res_id) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(insertComplaint);
			
			java.sql.Date date = java.sql.Date.valueOf(complaint.getComplaintDate());
			
			pstm.setInt(1, res_id);
			pstm.setString(2, complaint.getComplaint());
			pstm.setBoolean(3, complaint.getResolved());
			pstm.setDate(4, date);		
			
	        pstm.execute();
	        
	        con.close();
		
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
	public static Complaint getComplaint(int res_id) {
		Complaint complaint = new Complaint();
        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(getComplaint);
            stmt.setInt(1, res_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	complaint = new Complaint(
            		rs.getInt(1),
            		rs.getString(2),
            		rs.getBoolean(3),
            		rs.getDate(4).toLocalDate()
            	);
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return complaint;
	}
	
	public static void updateVehicle(Complaint complaint, int res_id) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(updateComplaint);
			
			pstm.setString(1, complaint.getComplaint());
			pstm.setBoolean(2, complaint.getResolved());
			pstm.setInt(3, res_id);

	        pstm.execute();
			
	        con.close();
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
	public static void deleteComplaint(int res_id) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(deleteComplaint);
			
			pstm.setInt(1, res_id);		
			
	        pstm.execute();
	        
	        con.close();
		
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
}
