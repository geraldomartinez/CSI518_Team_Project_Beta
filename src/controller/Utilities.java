package controller;

import java.util.HashMap;
import java.util.Map;
/* 
 * The purpose of this class is to include any helper functions that 
 * will aid the development of our system.
 * 
 * */
public class Utilities {
	
	//Given a Hashmap and a given value, return the key associated with that value.
	public static int getMapKeyValue(HashMap<Integer, String> input_list, String inputValue){
		int key = 0;
		
		for(Map.Entry<Integer, String> entry : input_list.entrySet()) {
            if((inputValue == null && entry.getValue() == null) || (inputValue != null && inputValue.equals(entry.getValue()))) {
                key = entry.getKey();
                break;
            }
        }
		
		return key;
		
	}

}
