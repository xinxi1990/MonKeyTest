package android.example.xinxi.monkeytest;

/**
 * abstract class for monkey event
 */
public abstract class MonkeyEvent {

    protected int eventType;

    public static final int EVENT_TYPE_TAP = 0;
    public static final int EVENT_TYPE_SWIPE = 1;
    public static final int EVENT_TYPE_LAUNCH = 2;
    public static final int EVENT_TYPE_BACK = 3;
    public static final int EVENT_TYPE_SUBMIT = 4;
    public static final int EVENT_TYPE_CONTENT = 5;
    public static final int EVENT_TYPE_HOMEKEY = 6;

    public static final int INJECT_SUCCESS = 1;
    public static final int INJECT_FAIL = 0;


    public MonkeyEvent(int type) {
        eventType = type;
    }

    /**
     * @return event type
     */
    public int getEventType() {
        return eventType;
    }

    /**
     * @return true if it is safe to throttle after this event, and false otherwise.
     */
    public boolean isThrottlable() {
        return true;
    }


    /**
     * @return INJECT_SUCCESS if it goes through, and INJECT_FAIL if it fails
     *         in the case of exceptions, return its corresponding error code
     */
    public abstract int injectEvent() throws Exception;
}
