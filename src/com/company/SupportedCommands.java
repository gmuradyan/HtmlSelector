package com.company;

import java.util.HashMap;
import java.util.Map;

public class SupportedCommands {

    public static final Map<String,Boolean> SupportedFlags= new HashMap<String, Boolean>();

    static{
        SupportedFlags.put("-f",true);
        SupportedFlags.put("-s",true);
        SupportedFlags.put("-h",false);
    }

    public static  Boolean isFlag(String flag){
        try {
            if (SupportedFlags.containsKey(flag)) {
                return true;
            }
        }
        catch (ClassCastException e){
            return false;
        }
        return false;
    }

    public static Boolean isHaveOpt(String flag){
        try {
            return SupportedFlags.get(flag);
        }catch (ClassCastException e){
            return false;
        }
    }
}
