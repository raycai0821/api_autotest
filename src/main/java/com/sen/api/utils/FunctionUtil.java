package com.sen.api.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sen.api.functions.Function;

public class FunctionUtil{

	private static final Map<String, Class<? extends Function>> functionsMap = new HashMap<>();
	static {
		//bodyfile 特殊处理
		functionsMap.put("bodyfile", null);
		List<Class<?>> clazzes = ClassFinder.getAllAssignedClass(Function.class);
		clazzes.forEach((clazz) -> {
			try {
				// function
				Function tempFunc = (Function) clazz.newInstance();
				//获取每个子类的功能名字
				String referenceKey = tempFunc.getReferenceKey();
				if (referenceKey.length() > 0) { // ignore self
				//将子类功能名字，与子类存入map
					functionsMap.put(referenceKey, tempFunc.getClass());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				//TODO 
			}
		});
	}
	
	public static boolean isFunction(String functionName){
		return functionsMap.containsKey(functionName);
	}
	//通过功能名字，调用功能方法
	public static String getValue(String functionName,String[] args){
		try {
			return functionsMap.get(functionName).newInstance().execute(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}

