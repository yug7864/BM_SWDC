package com.example.yug08.BM_SWDC_yslee.IoTUtil;

/**
 * 그냥 이리저리 사용할 유틸형 class
 * Created by yug08 on 2017-11-09.
 * @author YSLEE
 */

public class IoTUtil {

    private static String IP,ID;
    private static boolean outGoingMode = false;


    public static boolean getoutGoingMode(){
        return outGoingMode;
    }

    public static void setOutGoingMode(boolean OutGoingMode){
        outGoingMode = OutGoingMode;
    }
    public static void setIP(String ip){
        IP = ip;
    }

    public static void setID(String id){
        ID = id;
    }

    public static String getIP(){
        return IP;
    }

    public static String getID(){
        return ID;
    }

    public static int steteToInt(boolean state) {
        if (state)
            return 1;
        else
            return 0;
    }

    public static boolean intToState(int intstate) {
        if (intstate == 1)
            return true;
        else
            return false;
    }

}
