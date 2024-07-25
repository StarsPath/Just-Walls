package com.starspath.justwalls;

public class Utils {
    public static void debug(Object ... objects){
        StringBuilder s = new StringBuilder("DEBUG: ");
        for(Object obj : objects){
            if(obj == null)
                s.append("EMPTY ");
            else
                s.append(obj.toString()).append(" ");
        }
        System.out.println(s);
//        IndustrialRenewal.LOGGER.info(String.valueOf(s));
    }
}
