package golunch.mail.ru.golunch.screens.orders;

public class OrderItem {

    private String orderId;
    private String organizationName;

    public OrderItem(String orderId, String organizationName) {
        this.orderId = orderId;
        this.organizationName = organizationName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

}
