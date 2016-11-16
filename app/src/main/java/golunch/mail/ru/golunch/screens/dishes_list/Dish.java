package golunch.mail.ru.golunch.screens.dishes_list;

public class Dish {

    private String indexOrgCat;
    private String icon;
    private String name;
    private String weight;
    private String price;

    public Dish() {

    }

    public Dish(String icon, String name, String weight, String price, String indexOrgCat) {
        this.icon = icon;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.indexOrgCat = indexOrgCat;
    }

    public String getIndexOrgCat() {
        return indexOrgCat;
    }

    public void setIndexOrgCat(String indexOrgCat) {
        this.indexOrgCat = indexOrgCat;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
