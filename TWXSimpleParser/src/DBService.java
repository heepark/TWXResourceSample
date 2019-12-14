 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBService {
	private static Logger logger = LoggerFactory.getLogger(DBService.class);
	private static String url = "jdbc:mysql://localhost/movedb";

	public static ArrayList<DataDAO> getWaveValue(String fromDate, String toDate) throws SQLException {
		Connection connection = null;
		Statement st = null;
		ArrayList<DataDAO> waveData = new ArrayList<DataDAO>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, "root", "root");

			st = connection.createStatement();
			String sql;

			sql =  " Select EquipmentKey, DCSID, WaveformKey, RawData , datetime";
			sql += " from vibwaveform ";
//			sql += " where datetime > '"+fromDate+"' ";
//			sql += " AND datetime < '"+toDate+"' ";
			sql += " where EquipmentKey = '-276' ";
			sql += " AND DCSID = '-283' ";
			sql += " AND sampleID = '-316' ";
			sql += " order by DateTime ";
			
			System.out.println("sql ="+ sql);
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				waveData.add(DataDAO.newDataDAO(rs.getString("EquipmentKey"), rs.getDate("datetime"), rs.getBlob("RawData")));
			}
			rs.close();
			st.close();
			connection.close();
		} catch (SQLException se1) {
			se1.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return waveData;
	}
	
	public static ArrayList<DataDAO> getFFTValue(String fromDate, String toDate) throws SQLException {
		Connection connection = null;
		Statement st = null;
		ArrayList<DataDAO> waveData = new ArrayList<DataDAO>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, "twadmin", "twadmin");

			st = connection.createStatement();
			String sql;

			sql =  " Select SpectraKey, DCSID,  RawData , datetime";
			sql += " from vibspectra "; 
//			sql += " where datetime > '"+fromDate+"' ";
//			sql += " AND datetime < '"+toDate+"' ";
			sql += " where  DCSID = '-283' ";
			sql += " AND sampleID = '-316' ";
			sql += " order by DateTime ";
	 
			
			System.out.println("sql="+sql);
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				waveData.add(DataDAO.newDataDAO(rs.getString("SpectraKey"), rs.getDate("datetime"), rs.getBlob("RawData")));
			}
			rs.close();
			st.close();
			connection.close();
		} catch (SQLException se1) {
			se1.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return waveData;
	}
	 

}