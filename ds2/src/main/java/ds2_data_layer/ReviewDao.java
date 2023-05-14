package ds2_data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ds2.Review;
import ds2.Review.ReviewBuilder;

public class ReviewDao {

	private static String insertReview = 
			"INSERT INTO Review (Reservation_id, User_id, "
			+ "userfirstname, reviewdate, lastedit,"
			+ " reviewpts, reviewtext)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	private static String getReview =
			"SELECT Reservation_id, complaint, resolved, complaintdate "
			+ "FROM Complaint WHERE Reservation_id = ?";
	
	private static String deleteReview =
			"DELETE FROM Review WHERE Reservation_id = ?";

	public static void insertReview(Review review, int res_id, int user_id) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(insertReview);
			
			java.sql.Date date = java.sql.Date.valueOf(review.getReviewDate());
			java.sql.Date lastEdit = java.sql.Date.valueOf(review.getLastEdit());
			
			pstm.setInt(1, res_id);
			pstm.setInt(2, user_id);
			pstm.setString(3, review.getUserFirstName());
			pstm.setDate(4, date);		
			pstm.setDate(5, lastEdit);
			pstm.setInt(6, review.getReviewPts());
			pstm.setString(7, review.getReviewText());
			
	        pstm.execute();
	        
	        con.close();
		
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
	public static Review getReview(int res_id) {
		Review review = new Review();
        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(getReview);
            stmt.setInt(1, res_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	review = new ReviewBuilder(
            		rs.getInt(1),
            		rs.getInt(2),
            		rs.getString(3),
            		rs.getDate(4).toLocalDate(),
            		rs.getInt(6))
            	.lastEdit(rs.getDate(5).toLocalDate())
            	.reviewText(rs.getString(7))
            	.build();
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return review;
	}
	
	public static void deleteReview(int res_id) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(deleteReview);

			pstm.setInt(1, res_id);

	        pstm.execute();
			
	        con.close();
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
}
