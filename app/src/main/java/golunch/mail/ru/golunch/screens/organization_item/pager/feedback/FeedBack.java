package golunch.mail.ru.golunch.screens.organization_item.pager.feedback;

public class FeedBack {
    public String user;
    public String date;
    public String text;
    public boolean positive;

    public FeedBack(String user, String date, String text, boolean positive) {
        this.user = user;
        this.date = date;
        this.text = text;
        this.positive = positive;
    }
}
