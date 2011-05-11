package br.unisinos.swe.shared.serialization;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonConverter {
	// TODO recode this class so we do not need to extend it to correctly work 
	
	public JsonConverter() {
		
	}
	/*
	
	public final String toJSON(Object unserialized) {
		JSONObject object = new JSONObject();
		try{
			
			Class classe = unserialized.getClass();         
			while(classe != null) {
				if(ISerializable.class.isAssignableFrom(classe)) {
					putFieldsIntoJson(classe, object, unserialized);
					classe = classe.getSuperclass();
				} else if (List.class.isAssignableFrom(classe)) {  
					List components = (List)unserialized;
					return getListIntoJson(components);
				} else {
					classe = null;
				}
			}
			
		}catch(Exception e){ }
		return object.toString();
	}
	
	private void putFieldsIntoJson(Class classe, JSONObject object, Object unserialized) throws Exception {
		Field[] fields = classe.getDeclaredFields();
		
		for (Field field : fields) {             
			if (field.isAnnotationPresent(TransferParameter.class)) {
				field.setAccessible(true);
				
				if(ISerializable.class.isAssignableFrom(field.getType())) {
					object.put(field.getAnnotation(TransferParameter.class).value(), (new MobiSerialization().encode(field.get(unserialized))) );
				} else if(List.class.isAssignableFrom(field.getType())) {
					List components = (List)field.get(unserialized);
					object.put(field.getAnnotation(TransferParameter.class).value(), getListIntoJson(components));
				} else {
					object.put(field.getAnnotation(TransferParameter.class).value(), field.get(unserialized));
				}
			}
		}
		
	}

	private String getListIntoJson(List components) {
		JSONArray array = new JSONArray();
		for(Object component : components) {
			if(ISerializable.class.isAssignableFrom(component.getClass()))
				array.put((new MobiSerialization().encode(component)));
		}
		
		return array.toString();
		
	}
	
	public final <T> Object fromJSON(Class<T> finalClass, String jsonobj) {
		return fromJSON(finalClass, jsonobj, finalClass);
	}

	public final <T> Object fromJSON(Class<T> finalClass, String jsonobj, Class objectClass) {
		T newObject = null;
		try {
			newObject = finalClass.newInstance();
			Class classe = newObject.getClass();
			JSONObject object = new JSONObject();
			
			if(ISerializable.class.isAssignableFrom(classe))
				object = new JSONObject(jsonobj);
			
			while(classe != null) {
				if(ISerializable.class.isAssignableFrom(classe)) {
					
					getFieldsFromJson(classe, object, newObject);
					classe = classe.getSuperclass();
				} else if (List.class.isAssignableFrom(classe)) {  
					JSONArray array = new JSONArray(jsonobj);
					List components = getListFromJson(array, newObject.getClass());
					return components;
				} else {
					classe = null;
				}
			}
			
		} catch(Exception ex) {
			
		}
		
		return newObject;
	}

	private List getListFromJson(JSONArray array, Class listType) {
		
		List list = new ArrayList(); // TODO decode lists... objects are fine..
		
		try {
			
			// TODO code a way to find collection item class

			for(int idx = 0; idx < array.length(); idx++) {
				list.add(new MobiSerialization().decode((listType), array.getString(idx)));
			}
		
		} catch(Exception e) {
			
		}
		return list;
	}

	private <T> void getFieldsFromJson(Class classe, JSONObject object, T newObject) throws Exception {
		Field[] fields = classe.getDeclaredFields();    
		for (Field field : fields) {
			if (field.isAnnotationPresent(TransferParameter.class)) {
				field.setAccessible(true);
				if(ISerializable.class.isAssignableFrom(field.getType())) {
					field.set(newObject, new MobiSerialization().decode(field.getType(), object.getString(field.getAnnotation(TransferParameter.class).value())));
					
				} else if(List.class.isAssignableFrom(field.getType())) {
					JSONArray array = new JSONArray(object.getString(field.getAnnotation(TransferParameter.class).value()));

					Type[] genericTypes = ((ParameterizedType)field.getGenericType()).getActualTypeArguments();
					if(genericTypes.length > 0) {
						field.set(newObject, getListFromJson(array, ((Class)genericTypes[0])));//field.getGenericType()));
					}
				} else {
					field.set(newObject, object.get(field.getAnnotation(TransferParameter.class).value()));
				}
			}
		}
		
	}
	*/
}
