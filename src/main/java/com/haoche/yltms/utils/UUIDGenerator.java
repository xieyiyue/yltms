package com.haoche.yltms.utils;

import java.util.UUID;

public class UUIDGenerator {
	public static synchronized String getUUID(){
		UUID uuid=UUID.randomUUID();
		String str = uuid.toString(); 
		String uuidStr=str.replace("-", "");
		return uuidStr;
	}
}
