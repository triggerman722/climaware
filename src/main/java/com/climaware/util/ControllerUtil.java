package com.climaware.util;

/**
 * Created by greg on 3/9/19.
 */
public class ControllerUtil {
    public static int getParameterAsInt(String parameter, int defaultvalue, int minimumvalue, int maximumvalue) {
        int returnvalue = defaultvalue;
        if (parameter == null | parameter == "") {
            return returnvalue;
        }
        try {
            returnvalue = Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            returnvalue = defaultvalue;
        }
        if (returnvalue > maximumvalue)
            returnvalue = maximumvalue;
        if (returnvalue < minimumvalue)
            returnvalue = minimumvalue;
        return returnvalue;
    }

    public static float getParameterAsFloat(String parameter, float defaultvalue, int minimumvalue, int maximumvalue) {
        float returnvalue = defaultvalue;
        if (parameter == null | parameter == "") {
            return returnvalue;
        }
        try {
            returnvalue = Float.parseFloat(parameter);
        } catch (NumberFormatException e) {
            returnvalue = defaultvalue;
        }
        if (returnvalue > maximumvalue)
            returnvalue = maximumvalue;
        if (returnvalue < minimumvalue)
            returnvalue = minimumvalue;
        return returnvalue;
    }
}
