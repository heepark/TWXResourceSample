
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataDAO {
	String key;
	Date date;
	Blob blob;

	public static DataDAO newDataDAO(String key, Date date, Blob blob) {
		DataDAO dao = new DataDAO();
		dao.key = key;
		dao.date = date;
		dao.blob = blob;
		return dao;
	}
	 
	public static String getWaveJson(List<DataDAO> waveData,String key) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"target\":"+ key +",\"datapoints\":[");
		//float delta = (float) 0.390619 ;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		long delta = 390L ;
		Date date = new Date();
		long nowdate = date.getTime();
		//nowdate = nowdate - 30000000;
		System.out.println("####");
		System.out.println("##last## "+dateFormat.format(new Date(nowdate )));
		long temp = 0L;
		for (DataDAO wave : waveData ) {
			float[] floats =ConvertUtil.BlobToFloatArray(wave.blob);
			for (int i = 0; i < floats.length; i++) {
				float x =  floats[i];
				temp = i * delta;
				sb.append("[" + String.format ("%.2f", x) + "," + (nowdate + temp) + "],");
			}	
		}
		
		System.out.println("####");
		System.out.println("##last## "+dateFormat.format(new Date(nowdate + temp)));
		if (sb.charAt(sb.length()-1) == ',') sb.deleteCharAt(sb.length()-1);
		 
		sb.append("]}");
		return sb.toString();
	}

}
