package android.example.xinxi.monkeytest;


/**
 * @author xinxi
 * 随机数类
 */


public class MathRandom {

    /**
     * 0出现的概率为%40
     */
    public static double  EVENT_TYPE_TAP = 0.35;
    /**
     * 1出现的概率为%30
     */
    public static double EVENT_TYPE_SWIPE = 0.15;
    /**
     * 2出现的概率为%10
     */
    public static double EVENT_TYPE_BACK = 0.10;

    public static double EVENT_TYPE_SUBMIT = 0.05;

    public static double EVENT_TYPE_CONTENT = 0.30;
    
    public static double EVENT_TYPE_SPECIAL_POINT = 0.04;
    
    public static double  EVENT_TYPE_HOMEKEY = 0.01;




    public int PercentageRandom() {
        double randomNumber;
        randomNumber = Math.random();
        if (randomNumber >= 0 && randomNumber <= EVENT_TYPE_TAP) {
            return 0;
        } else if (randomNumber >= EVENT_TYPE_TAP / 100 && randomNumber <= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE) {
            return 1;
        } else if (randomNumber >= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE
                && randomNumber <= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE + EVENT_TYPE_BACK) {
            return 2;
        } else if (randomNumber >= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE + EVENT_TYPE_BACK
                && randomNumber <= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE + EVENT_TYPE_BACK + EVENT_TYPE_SUBMIT) {
            return 3;
        } else if (randomNumber >= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE + EVENT_TYPE_BACK + EVENT_TYPE_SUBMIT
                && randomNumber <= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE + EVENT_TYPE_BACK + EVENT_TYPE_SUBMIT
                + EVENT_TYPE_CONTENT) {
            return 4;
        }else if (randomNumber >= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE + EVENT_TYPE_BACK + EVENT_TYPE_SUBMIT + EVENT_TYPE_CONTENT
                && randomNumber <= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE + EVENT_TYPE_BACK + EVENT_TYPE_SUBMIT
                + EVENT_TYPE_CONTENT + EVENT_TYPE_SPECIAL_POINT) {
            return 5;
        }else if (randomNumber >= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE + EVENT_TYPE_BACK + EVENT_TYPE_SUBMIT + EVENT_TYPE_CONTENT
        		+ EVENT_TYPE_SPECIAL_POINT
                && randomNumber <= EVENT_TYPE_TAP + EVENT_TYPE_SWIPE + EVENT_TYPE_BACK + EVENT_TYPE_SUBMIT
                + EVENT_TYPE_CONTENT + EVENT_TYPE_SPECIAL_POINT + EVENT_TYPE_HOMEKEY) {
            return 6;
        }
        return -1;
    }

}
