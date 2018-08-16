package com.yc.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class RequestUtils {
		/**
		 * @throws IllegalAccessException 
		 * @throws InstantiationException 
		 * @throws InvocationTargetException 
		 * @throws IllegalArgumentException 
		 * 
		 * */
		 public static <T> T parseRequest(HttpServletRequest request,Class<T> clz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			
			 //将这个参数值存成单数值
			 Map<String,String[]> maps=request.getParameterMap();
			 Map<String,String> m=new HashMap<String,String>();
			 for(Map.Entry<String,String[]> entry:maps.entrySet()){
				 m.put(entry.getKey(), entry.getValue()[0]);
			 }
		
			 
			return  parseRequest(m,clz);
		 }
		 
		 public static <T> T parseRequest(Map<String,String> m,Class<T> clz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			 //1.创建clz的对象   反射实例
			 T obj=clz.newInstance();  //Product p=new Product();  newInstance调用的是无参构造函数
			 
			 //取set方法
			 List<Method> allSetMethod=getAllSetMethod(clz);
			//循环  m,取出每一个参数
			 for(Map.Entry<String,String> entry:m.entrySet()){
				String parameterName=entry.getKey();
				String parameterValue=entry.getValue();
				
				//取allSetMethod中所有的方法的方法名
				for(Method method:allSetMethod){
					String methodName=method.getName();
					if(methodName.equalsIgnoreCase("set"+parameterName)){
						
						//取出当前method中参数的类型，判断是哪一种，在进行类型的转换
						String parameterTypeName=method.getParameterTypes()[0].getName();
						if("double".equals(parameterTypeName)  || "java.lang.Double".equals(parameterTypeName)){
							double v=Double.parseDouble(parameterValue);
							method.invoke(obj, v);
						}else if("int".equals(parameterTypeName)  || "java.lang.Integer".equals(parameterTypeName)){
							int v=Integer.parseInt(parameterValue);
							method.invoke(obj, v);
						}else if("float".equals(parameterTypeName)  || "java.lang.Float".equals(parameterTypeName)){
							float v=Float.parseFloat(parameterValue);
							method.invoke(obj, v);
						}else{
							//激活方法  ：argument  type  mismatch
							method.invoke(obj, parameterValue);//price 在Product类中double型，但在map中是String，所以类型转换
						}
						
					}
				}
			 }
			 
			return obj;
		 }
		 
		 
		 private static List<Method> getAllSetMethod(Class clz){
			List<Method> allSetMethod=new ArrayList<Method>();
			Method[] ms=	clz.getMethods();    //clz中所有的方法
			for(Method m:ms){
				if(m.getName().startsWith("set")){
					allSetMethod.add(m);
				}
			}
			  return allSetMethod;
		 }
		 
		 public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			 Map<String,String>map=new HashMap<String,String>();
			 map.put("pid", 100+"");
			 map.put("pname", "apple");
			 map.put("price", 20+"");
		 }
		 
}
