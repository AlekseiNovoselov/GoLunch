package golunch.mail.ru.golunch.screens.dishes_list;

public class Dish {

    public String indexOrgCat;
    public String icon;
    public String name;
    public String weight;
    public String price;

    public Dish() {

    }

    public Dish(String icon, String name, String weight, String price, String indexOrgCat) {
        this.icon = icon;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.indexOrgCat = indexOrgCat;
    }

}
