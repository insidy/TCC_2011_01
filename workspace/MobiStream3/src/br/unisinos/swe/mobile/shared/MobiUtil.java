package br.unisinos.swe.mobile.shared;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.resource.ClientResource;

import android.util.Log;
import br.unisinos.swe.shared.serialization.ISerializable;
import br.unisinos.swe.shared.serialization.MobiSerialization;

public class MobiUtil {

	public static final String TAG = "MobiStream";
	
	public static String convertStreamToString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			
		} catch (Exception ex) {
			Log.e(TAG, "Convert stream to string failed: " + ex.getMessage());
		}
		return sb.toString();
	}
	
	public static <E> E getObjectFromServer(Class<E> _class, String path, ISerializable message ) {
		E object = null;

		try {
			object = (E)new MobiSerialization().<E>decode(getStringFromServer(path, message));
		} catch (Exception ex) {
			Log.e(TAG, "Get Object from server failed: " + ex.getMessage());
		}
		
		return object;
	}
	
	public static <E> List<E> getListFromServer(Class<E> _class, String path, ISerializable message ) {
		
		// Example service list pattern: http://SERVER/channels/CHANNEL ID/services
		List<E> responseList = new ArrayList<E>();
		
		try {
			responseList = new MobiSerialization().<List<E>>decode(getStringFromServer(path, message));
		} catch (Exception ex) {
			Log.e(TAG, "Get List from server failed: " + ex.getMessage());
		}
		
		return responseList;
	}
	
	public static String getStringFromServer(String path, ISerializable message) {
		ClientResource resource = new ClientResource(path);
		String response = "";

		try {
			resource.post(new MobiSerialization().encode(message), MediaType.TEXT_PLAIN);
			if (resource.getStatus().isSuccess()
					&& resource.getResponseEntity().isAvailable()) {
				response = MobiUtil.convertStreamToString(resource.getResponseEntity().getStream());
			}
			resource.release();
		} catch (Exception ex) {
			if(resource != null)
				resource.release();
			Log.e(TAG, "Get List from server failed: " + ex.getMessage());
		}
		
		return response;
	}
}
