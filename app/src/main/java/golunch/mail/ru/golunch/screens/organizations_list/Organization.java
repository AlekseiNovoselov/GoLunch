package golunch.mail.ru.golunch.screens.organizations_list;

public class Organization {

    public String name;
    public String description;
    public String photoName;
    public String bannerName;

    public Organization() {

    }

    public Organization(String description, String age, String photoId, String bannerName) {
        this.description = description;
        this.name = age;
        this.photoName = photoId;
        this.bannerName = bannerName;
    }
}
