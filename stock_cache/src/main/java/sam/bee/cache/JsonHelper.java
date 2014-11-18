package sam.bee.cache;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class JsonHelper {
	/**
	 * 将Javabean转换为Map
	 * 
	 * @param javaBean
	 *            javaBean
	 * @return Map对象
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Map toMap(Object javaBean)  {

		Map result = new HashMap();
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {

			try {

				if (method.getName().startsWith("get")) {

					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					result.put(field, null == value ? "" : value.toString());

				}
				



			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		Field[] fields = javaBean.getClass().getFields();
		for(Field field : fields){
			try {
			result.put(field.getName(),  field.get(javaBean));
			}
			catch (Exception e) {
			e.printStackTrace();
			}
		}

		return result;

	}

	/**
	 * 将Json对象转换成Map
	 * 
	 * @param jsonObject
	 *            json对象
	 * @return Map对象
	 * @throws JSONException
	 */
	public static Map toMap(String jsonString) throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		return toMap(json);
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap();
		Iterator keys = object.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			map.put(key, fromJson(object.get(key)));
		}
		return map;
	}

	public static Object toJSON(Object object) throws JSONException {
		if (object instanceof Map) {
			JSONObject json = new JSONObject();
			Map map = (Map) object;
			for (Object key : map.keySet()) {
				json.put(key.toString(), toJSON(map.get(key)));
			}
			return json;
		} else if (object instanceof Iterable) {
			JSONArray json = new JSONArray();
			for (Object value : ((Iterable) object)) {
				json.put(value);
			}
			return json;
		} else {
			return object;
		}
	}

	public static boolean isEmptyObject(JSONObject object) {
		return object.names() == null;
	}

	public static Map<String, Object> getMap(JSONObject object, String key)
			throws JSONException {
		return toMap(object.getJSONObject(key));
	}


	public static List toList(JSONArray array) throws JSONException {
		List list = new ArrayList();
		for (int i = 0; i < array.length(); i++) {
			list.add(fromJson(array.get(i)));
		}
		return list;
	}

	private static Object fromJson(Object json) throws JSONException {
		if (json == JSONObject.NULL) {
			return null;
		} else if (json instanceof JSONObject) {
			return toMap((JSONObject) json);
		} else if (json instanceof JSONArray) {
			return toList((JSONArray) json);
		} else {
			return json;
		}
	}

	/**
	 * 将JavaBean转换成JSONObject（通过Map中转）
	 * 
	 * @param bean
	 *            javaBean
	 * @return json对象
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static JSONObject toJSONObjectt(Object bean) throws IllegalArgumentException, IllegalAccessException {

		return new JSONObject(toMap(bean));

	}

	/**
	 * 将Map转换成Javabean
	 * 
	 * @param javabean
	 *            javaBean
	 * @param data
	 *            Map数据
	 */
	public static Object toJavaBean(Object javabean, Map data) {

		Method[] methods = javabean.getClass().getDeclaredMethods();
		for (Method method : methods) {

			try {
				if (method.getName().startsWith("set")) {

					String field = method.getName();
					field = field.substring(field.indexOf("set") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					method.invoke(javabean, new Object[] {

					data.get(field)

					});

				}
			} catch (Exception e) {
			}

		}

		return javabean;

	}

	/**
	 * JSONObject到JavaBean
	 * 
	 * @param bean
	 *            javaBean
	 * @return json对象
	 * @throws ParseException
	 *             json解析异常
	 * @throws JSONException
	 */
	public static void toJavaBean(Object javabean, String jsonString)
			throws ParseException, JSONException {

		JSONObject jsonObject = new JSONObject(jsonString);

		Map map = toMap(jsonObject.toString());

		toJavaBean(javabean, map);

	}
}
