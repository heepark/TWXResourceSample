


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Blob;
import java.sql.SQLException;

public class ConvertUtil {

	 
	public static float[] BlobToFloatArray(Blob blob) throws IllegalArgumentException, SQLException
	{
		return 	ConvertByteArrayToFloat(blob.getBytes(1l, (int) blob.length()));	
	}

	private static float[] ConvertByteArrayToFloat(byte[] bytes) throws IllegalArgumentException
	{
	    if(bytes.length % 4 != 0) throw new IllegalArgumentException ();
		byte[] data = new byte[4];

	   	ByteBuffer buf = ByteBuffer.wrap(bytes);
	    float[] floats = new float[bytes.length/4];
	    
	    for(int i = 0; i < floats.length; i++)
	    {
			buf.get(data);
			floats[i] = byteToFloat(data);
 	    }
	    return floats;
	}

	// 4 bytes only
	private static  byte[] floatToByte(float value) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
	}
		
	// 4 bytes only
	private static  float byteToFloat(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	} 
	
// test

	public static void main(String[] args) {
		test2();
	}
	
	public static void test2() {
		ByteBuffer buf = ByteBuffer.allocate(30);
 
		buf.put(floatToByte(4444f));
		buf.put(floatToByte(44434f));
		buf.put(floatToByte(44444f));
		buf.clear();
		log(buf);
		log(buf);
		log(buf);

	}
 
	public static void log(ByteBuffer buf) {
		byte[] data = new byte[4];
		buf.get(data);
		float f = byteToFloat(data);
		
		System.out.println(buf.position() + " ~ " + buf.limit() + " [" +  String.format ("%.2f", f) + "]");
	} 
 
}
