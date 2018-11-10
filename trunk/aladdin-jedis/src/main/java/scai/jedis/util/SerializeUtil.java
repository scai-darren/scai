package scai.jedis.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author liujiqiao
 *
 */
public class SerializeUtil {

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static byte[] serialize(Object object) throws Exception {
		if (object == null) {
			return null;
		}
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		byte[] bytes = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			throw e;
		} finally {
			close(oos);
			close(baos);
		}
		return bytes;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static Object unserialize(byte[] bytes) throws Exception {
		if (bytes == null) {
			return null;
		}
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			throw e;
		} finally {
			close(bais);
			close(ois);
		}
	}

	/**
	 * 序列化 list 集合
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static byte[] serializeList(List<?> list) throws Exception {

		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			for (Object obj : list) {
				oos.writeObject(obj);
			}
			bytes = baos.toByteArray();
		} catch (Exception e) {
			throw e;
		} finally {
			close(oos);
			close(baos);
		}
		return bytes;
	}

	/**
	 * 反序列化 list 集合
	 * 
	 * @param lb
	 * @return
	 * @throws Exception
	 */
	public static List<?> unserializeList(byte[] bytes) throws Exception {
		if (bytes == null) {
			return null;
		}

		List<Object> list = new ArrayList<Object>();
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			while (bais.available() > 0) {
				Object obj = (Object) ois.readObject();
				if (obj == null) {
					break;
				}
				list.add(obj);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(bais);
			close(ois);
		}
		return list;
	}

	/**
	 * 关闭io流对象
	 * 
	 * @param closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
