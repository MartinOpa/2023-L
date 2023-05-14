package ds2_data_layer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionProvider {
	
	public ConnectionProvider () {}

	public static Connection getConnection() throws SQLException {
			
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
        try {
            return DriverManager.getConnection(
    				"jdbc:oracle:thin:OPA0023/xxGc9L8Zfve99028@dbsys.cs.vsb.cz:1521:oracle"
    				);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
				
	}
}

