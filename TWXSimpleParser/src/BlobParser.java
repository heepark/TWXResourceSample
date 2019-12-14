import com.thingworx.data.util.InfoTableInstanceFactory;
import com.thingworx.logging.LogUtilities;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.resources.Resource;
import com.thingworx.types.BaseTypes;
import com.thingworx.types.InfoTable;
import com.thingworx.types.collections.ValueCollection;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;

public class BlobParser extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger _logger = LogUtilities.getInstance().getApplicationLogger(BlobParser.class);

	public BlobParser() {
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	@ThingworxServiceDefinition(name = "BlobToFloat", description = "", category = "", isAllowOverride = false, aspects = {
			"isAsync:false" })
	@ThingworxServiceResult(name = "Result", description = "", baseType = "JSON", aspects = {})
	public JSONObject BlobToFloat(
			@ThingworxServiceParameter(name = "blob", description = "", baseType = "BLOB", aspects = {
					"isRequired:true" }) byte[] blob) {
		_logger.trace("Entering Service: BlobToFloat");
		JSONObject rtnJson = new JSONObject();
		int delta = 390 ;
		int sequance = 0;
		try {
			float[] floats = this.ConvertByteArrayToFloat(blob);
			for (int i = 0; i < floats.length; i++) {
				float x =  floats[i];
				sequance = i*delta;
				rtnJson.put(sequance + "", String.format ("%.2f", x) ); 
			}	
		} catch (Exception e) {
			e.printStackTrace();
			_logger.trace("error :" + e.getMessage());
		}
		_logger.trace("Exiting Service: BlobToFloat");
		return rtnJson;
	}

	@ThingworxServiceDefinition(name = "Hello", description = "", category = "", isAllowOverride = false, aspects = {
			"isAsync:false" })
	@ThingworxServiceResult(name = "Result", description = "", baseType = "STRING", aspects = {})
	public String Hello(
			@ThingworxServiceParameter(name = "inStr", description = "", baseType = "STRING") String inStr) {
		_logger.trace("Entering Service: Hello");
		 
		_logger.trace("Exiting Service: Hello");
		return inStr + " v4";
	}

	public float[] BlobToFloatArray(Blob blob) throws IllegalArgumentException, SQLException
	{
		return 	ConvertByteArrayToFloat(blob.getBytes(1l, (int) blob.length()));	
	}

	private float[] ConvertByteArrayToFloat(byte[] bytes) throws IllegalArgumentException
	{
		_logger.info("#####BlobToFloatInfo 2221");
	   // if(bytes.length % 4 != 0) throw new IllegalArgumentException ();
	    _logger.info("#####BlobToFloatInfo 2222");
		byte[] data = new byte[4];
		_logger.info("#####BlobToFloatInfo 2223");
	   	ByteBuffer buf = ByteBuffer.wrap(bytes);
	   	_logger.info("#####BlobToFloatInfo 2224");
	    float[] floats = new float[bytes.length/4];
	    _logger.info("#####BlobToFloatInfo 2225");
	    for(int i = 0; i < floats.length; i++)
	    {
	    	_logger.info("#####BlobToFloatInfo 2226");
			buf.get(data);
			_logger.info("#####BlobToFloatInfo 2227");
			floats[i] = byteToFloat(data);
			_logger.info("#####BlobToFloatInfo 2228");
 	    }
	    _logger.info("#####BlobToFloatInfo 2229");
	    return floats;
	}

	// 4 bytes only
	private  byte[] floatToByte(float value) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
	}
		
	// 4 bytes only
	private  float byteToFloat(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}


//
//
//
//	@ThingworxServiceDefinition(name = "BlobToFloatInfo", description = "", category = "", isAllowOverride = false, aspects = {
//			"isAsync:false" })
//	@ThingworxServiceResult(name = "Result", description = "", baseType = "INFOTABLE", aspects = {
//			"isEntityDataShape:true", "dataShape:WaveFormSequenceDS" })
//	public InfoTable BlobToFloatInfo(
//			@ThingworxServiceParameter(name = "blob", description = "", baseType = "BLOB") byte[] blob) {
//		_logger.info("info Entering Service: BlobToFloatInfo");
//
//		InfoTable it = null;
//		
//		int delta = 390 ;
//		int sequance = 0;
//		try {
//			_logger.info("#####BlobToFloatInfo 111 ");
//			it = InfoTableInstanceFactory.createInfoTableFromDataShape("WaveFormSequenceDS");
//			_logger.info("#####BlobToFloatInfo 112 ");
//			float[] floats = this.ConvertByteArrayToFloat(blob);
//			_logger.info("#####BlobToFloatInfo 113 ");
//			ValueCollection values;
//			_logger.info("#####BlobToFloatInfo 1114 ");
//			for (int i = 0; i < floats.length; i++) {
//				float value =  floats[i];
//				sequance = i*delta;
//				_logger.info("#####BlobToFloatInfo 111 sequance  " + sequance) ;
//				_logger.info("#####BlobToFloatInfo 111 value  " + value) ;
//				values = new ValueCollection();
//	        	values.put("seq", BaseTypes.ConvertToPrimitive(sequance, BaseTypes.INTEGER) );
//	        	values.put("value", BaseTypes.ConvertToPrimitive(value, BaseTypes.NUMBER) );
//				it.addRow(values);
//				//rtnJson.put(sequance + "", String.format ("%.2f", x) ); 
//			}	
//			_logger.info("#####BlobToFloatInfo 1115 ");
//		} catch (Exception e) {
//			e.printStackTrace();
//			_logger.error("error :" + e.getMessage());
//		}
// 
//		_logger.info("Exiting Service: BlobToFloatInfo");
//		return it;
//	} 
//	 
//	

//
//	@ThingworxServiceDefinition(name = "BlobToFloatInfo", description = "", category = "", isAllowOverride = false, aspects = {
//			"isAsync:false" })
//	@ThingworxServiceResult(name = "Result", description = "", baseType = "INFOTABLE", aspects = {
//			"isEntityDataShape:true", "dataShape:WaveFormSequenceDS" })
//	public InfoTable BlobToFloatInfo(
//			@ThingworxServiceParameter(name = "blob", description = "", baseType = "BLOB") byte[] blob) {
//		_logger.info("info Entering Service: BlobToFloatInfo");
//
//		InfoTable it = null;
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		//int delta = 390 ;
//		//int sequance = 0;
//		try {
//			_logger.info("#####BlobToFloatInfo 111 ");
//			it = InfoTableInstanceFactory.createInfoTableFromDataShape("WaveFormSequenceDS");
//			_logger.info("#####BlobToFloatInfo 112 ");
//			
//			ArrayList<DataDAO>  waveData =DBService.getWaveValue(null, null);
//			
//			long delta = 390L ;
//			Date date = new Date();
//			long nowdate = date.getTime();
//			long temp = 0L;
//			System.out.println("##last## "+dateFormat.format(new Date(nowdate )));
//			ValueCollection values;
//			for (DataDAO wave : waveData ) {
//				float[] floats =ConvertUtil.BlobToFloatArray(wave.blob);
//				for (int i = 0; i < floats.length; i++) {
//					float value =  floats[i];
//					temp = i * delta;
//					values = new ValueCollection();
//		        	values.put("seq", BaseTypes.ConvertToPrimitive((nowdate + temp), BaseTypes.INTEGER) );
//		        	values.put("value", BaseTypes.ConvertToPrimitive(value, BaseTypes.NUMBER) );
//					it.addRow(values);
//				}	
//			}
//			_logger.info("#####BlobToFloatInfo 1115 ");
//		} catch (Exception e) {
//			e.printStackTrace();
//			_logger.error("error :" + e.getMessage());
//		}
// 
//		_logger.info("Exiting Service: BlobToFloatInfo");
//		return it;
//	} 

	@ThingworxServiceDefinition(name = "BlobToFloatInfo", description = "", category = "", isAllowOverride = false, aspects = {
			"isAsync:false" })
	@ThingworxServiceResult(name = "Result", description = "", baseType = "INFOTABLE", aspects = {
			"isEntityDataShape:true", "dataShape:WaveFormSequenceDS" })
	public InfoTable BlobToFloatInfo(
			@ThingworxServiceParameter(name = "blob", description = "", baseType = "BLOB") byte[] blob) {
		_logger.info("info Entering Service: BlobToFloatInfo");

		InfoTable it = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//int delta = 390 ;
		//int sequance = 0;
		try {
			_logger.info("#####BlobToFloatInfo 111 ");
			it = InfoTableInstanceFactory.createInfoTableFromDataShape("WaveFormSequenceDS");
			_logger.info("#####BlobToFloatInfo 112 ");
			
			ArrayList<DataDAO>  waveData =DBService.getWaveValue(null, null);
			_logger.info("#####BlobToFloatInfo 113 ");
			int delta = 390 ;
			Date date = new Date();
			//long nowdate = date.getTime();
			int temp = 0;
			//System.out.println("##last## "+dateFormat.format(new Date(nowdate )));
			ValueCollection values;
			for (DataDAO wave : waveData ) {
				_logger.info("#####BlobToFloatInfo 14 ");
				float[] floats =ConvertUtil.BlobToFloatArray(wave.blob);
				_logger.info("#####BlobToFloatInfo 1115 ");
				for (int i = 0; i < floats.length; i++) {
					float value =  floats[i];
					temp = i * delta;
					values = new ValueCollection();
					_logger.info("#####BlobToFloatInfo 1116 ");
		        	values.put("seq", BaseTypes.ConvertToPrimitive(  temp , BaseTypes.INTEGER) );
		        	values.put("value", BaseTypes.ConvertToPrimitive(value, BaseTypes.NUMBER) );
					it.addRow(values);
				}	
			}
			_logger.info("#####BlobToFloatInfo 1117 ");
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("error :" + e.getMessage());
		}
 
		_logger.info("Exiting Service: BlobToFloatInfo");
		return it;
	} 
 
	
}
