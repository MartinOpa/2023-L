package ds2_data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ds2.Reply;

public class ReplyDao {
	
	private static String insertReply = 
			"INSERT INTO Reply (reply, replydate, Review_Reservation_id) "
			+ "VALUES (?, ?, ?)";
	
	private static String getReply =
			"SELECT reply, replydate, Review_Reservation_id "
			+ "FROM Reply WHERE Review_Reservation_id = ?";
	
	private static String deleteReply =
			"DELETE FROM Reply WHERE Review_Reservation_id = ?";

	public static void insertReply(Reply reply, int res_id) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(insertReply);
			
			java.sql.Date date = java.sql.Date.valueOf(reply.getReplyDate());
			
			pstm.setInt(1, res_id);
			pstm.setString(2, reply.getReply());
			pstm.setDate(3, date);		
			
	        pstm.execute();
	        
	        con.close();
		
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
	public static Reply getReply(int res_id) {
		Reply reply = new Reply();
        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(getReply);
            stmt.setInt(1, res_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	reply = new Reply(
            		rs.getString(1),
            		rs.getDate(2).toLocalDate(),
            		rs.getInt(3));
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return reply;
	}
	
	public static void deleteReply(int res_id) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(deleteReply);

			pstm.setInt(1, res_id);

	        pstm.execute();
			
	        con.close();
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}

}
