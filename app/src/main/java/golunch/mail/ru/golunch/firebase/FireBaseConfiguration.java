package golunch.mail.ru.golunch.firebase;

public class FireBaseConfiguration {

    public static final String ORDER_CAFE_NAME = "ORDER_CAFE_ID";
    public static final String ORDER_DISHES = "ORDER_DISHES";
    public static final String ORDER_STATE = "ORDER_STATE";

    public enum ORDER_STATE_ENUM {
        NEW, PAID, CANCELLED
    }
}
