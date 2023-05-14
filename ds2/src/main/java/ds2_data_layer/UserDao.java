package ds2_data_layer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ds2.User;
import ds2.User.Type;
import ds2.User.UserBuilder;

public class UserDao {
	
	private static String insertUser = 
			"insert into Clients (login, firstName, "
			+ "lastName, phoneNumber, address,"
			+ " email, type, partnerDate, discount, active)"
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static String getUsers =
			"SELECT ID, login, firstName, lastName, phoneNumber,"
			+ " address, email, type, partnerDate, discount, active FROM clients";
	
	private static String getUserById =
			" WHERE ID = ?";
	
	private static String updateUser =
			"UPDATE Clients "
			+ "SET login = ?, firstName = ?, lastName = ?, phoneNumber = ?,"
			+ "address = ?, email = ?, type = ?, partnerDate = ?, discount = ?,"
			+ "active = ?"
			+ "WHERE ID = ?";
	
	private static String setPartnerDate =
			"UPDATE Clients "
			+ "SET partnerDate = ?, type = ? "
			+ "WHERE ID = ?";

	public static void insertUser(User user) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(insertUser);
			
			Date date;		
			if (user.getPartnerDate() != null) {
				date = new java.sql.Date(Date.from(user.getPartnerDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
			} else date = null;
					
			pstm.setString(1, user.getLogin());
	        pstm.setString(2, user.getFirstName());
	        pstm.setString(3, user.getLastName());
	 		if (user.getPhoneNumber() == 0) {
				pstm.setNull(4, Types.NULL);;
			} else {
				pstm.setInt(4, user.getPhoneNumber());
			}		
	        pstm.setString(5, user.getAddress());
	        pstm.setString(6, user.getEmail());
	        pstm.setInt(7, user.getType().ordinal());
	        pstm.setDate(8, (java.sql.Date) date);
	        if (user.getDiscount() == 0) {
				pstm.setNull(9, Types.NULL);;
			} else {
				pstm.setDouble(9, user.getDiscount());
			}       
	        pstm.setBoolean(10, user.getActive());
	        pstm.execute();
	        
	        con.close();
		
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
	public static List<User> getUsers() {
		List<User> users = new LinkedList<>();
        try(Connection con = ConnectionProvider.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(getUsers);
            while(rs.next()) {
               int id = rs.getInt(1);
               String login = rs.getString(2);
               String firstName = rs.getString(3);
               String lastName = rs.getString(4);
               int phoneNumber = rs.getInt(5);
               String address = rs.getString(6);
               String email = rs.getString(7);
               int type = rs.getInt(8);
               java.sql.Date partnerDate = rs.getDate(9);
               double discount = rs.getDouble(10);
               boolean active = rs.getBoolean(11);   
               
               LocalDate convertedDate;
               if (partnerDate != null) {
            	   convertedDate = partnerDate.toLocalDate();	   
               } else convertedDate = null;
                             
               users.add(
            		new UserBuilder(login, firstName, lastName, email, Type.byInt(type), active)
            		.id(id)
            		.discount(discount)
            		.partnerDate(convertedDate)
            		.address(address)
            		.phoneNumber(phoneNumber)
            		.build()
            	);
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return users;
	}
	
	public static User getUserById(int pID) {
		User user = new User();
        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(getUsers + getUserById);
            stmt.setInt(1, pID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
               int id = rs.getInt(1);
               String login = rs.getString(2);
               String firstName = rs.getString(3);
               String lastName = rs.getString(4);
               int phoneNumber = rs.getInt(5);
               String address = rs.getString(6);
               String email = rs.getString(7);
               int type = rs.getInt(8);
               java.sql.Date partnerDate = rs.getDate(9);
               double discount = rs.getDouble(10);
               boolean active = rs.getBoolean(11);   
               
               LocalDate convertedDate;
               if (partnerDate != null) {
            	   convertedDate = partnerDate.toLocalDate();	   
               } else convertedDate = null;
                             
               user = new UserBuilder(login, firstName, lastName, email, Type.byInt(type), active)
            		.id(id)
            		.discount(discount)
            		.partnerDate(convertedDate)
            		.address(address)
            		.phoneNumber(phoneNumber)
            		.build();
            }
            
            con.close();
        } catch (SQLException e) {
        	System.out.println(e);
        }
        
        return user;
	}
	
	public static void updateUser(User user) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(updateUser);
			
			Date date;		
			if (user.getPartnerDate() != null) {
				date = new java.sql.Date(Date.from(user.getPartnerDate().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
			} else date = null;
					
			pstm.setString(1, user.getLogin());
	        pstm.setString(2, user.getFirstName());
	        pstm.setString(3, user.getLastName());
	 		if (user.getPhoneNumber() == 0) {
				pstm.setNull(4, Types.NULL);;
			} else {
				pstm.setInt(4, user.getPhoneNumber());
			}		
	        pstm.setString(5, user.getAddress());
	        pstm.setString(6, user.getEmail());
	        pstm.setInt(7, user.getType().ordinal());
	        pstm.setDate(8, (java.sql.Date) date);
	        if (user.getDiscount() == 0) {
				pstm.setNull(9, Types.NULL);;
			} else {
				pstm.setDouble(9, user.getDiscount());
			}       
	        pstm.setBoolean(10, user.getActive());
	        pstm.setInt(11, user.getId());
	        pstm.execute();
			
	        con.close();
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
	public static void setPartnerDate(int id, LocalDate date) {
		try( Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(setPartnerDate);
					
			pstm.setDate(1, new java.sql.Date(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
			pstm.setInt(2, Type.partner.ordinal());
	        pstm.setInt(3, id);
	        pstm.execute();
			System.out.println("1.6 Nastavení partnerského uživatele");
	        con.close();
		} catch (SQLException e) {
        	System.out.println(e);
        }  
	}
	
	public static void deleteUser(User user) {
		try( Connection con = ConnectionProvider.getConnection()) {
			CallableStatement cstmt = con.prepareCall("call deleteUser(?)");

	        cstmt.setInt(1, user.getId());
	        cstmt.execute(); // test pro počet uživatelů
	        System.out.println("1.5 Smazání uživatele");
	        con.close();
		} catch (SQLException e) {
        	System.out.println(e);
        }  
        
	}
}
