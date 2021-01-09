package com.pgbezerra.datamigration.annotations.processor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.pgbezerra.datamigration.annotations.Header;

public class HeaderAnnotationProcessor {

	private HeaderAnnotationProcessor() {}
	
	public static <T> String[] extractHeaderNames(Class<T> clazz) {
		
		Field[] fields = clazz.getDeclaredFields();
		List<String> names = new ArrayList<>();
		for(Field field: fields)
			if(field.isAnnotationPresent(Header.class)) {
				String name = field.getAnnotation(Header.class).name();
				if(StringUtils.hasLength(name))
					names.add(name);
				else names.add(field.getName());
			}
		return names.toArray(new String[names.size()]);
	}
	
}
