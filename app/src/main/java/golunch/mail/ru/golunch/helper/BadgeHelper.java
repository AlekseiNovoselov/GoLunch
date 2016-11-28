package golunch.mail.ru.golunch.helper;

import android.graphics.Color;

import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.utils.BadgeStyle;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;

import golunch.mail.ru.golunch.MainActivity;
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.buy.BuyHelper;

public class BadgeHelper {

    private MainActivity mActivity;
    private BuyHelper buyHelper;

    public enum BADGE {
        SHOP, BASKET
    }

    public BadgeHelper(MainActivity activity) {
        mActivity = activity;
        buyHelper = new BuyHelper(mActivity);
    }

    public void updateBadge(BADGE badge) {
        int badgeCount = buyHelper.getDishList().size();
        if (badgeCount > 0) {
            ActionItemBadge.update(mActivity, mActivity.getMenu().findItem(R.id.item_samplebadge),
                    getBadgeResource(badge),
                    new BadgeStyle(BadgeStyle.Style.DEFAULT,
                            com.mikepenz.actionitembadge.library.R.layout.menu_action_item_badge,
                            Color.LTGRAY,
                            Color.WHITE,
                            Color.BLACK),
                    badgeCount);
        } else {
            ActionItemBadge.hide(mActivity.getMenu().findItem(R.id.item_samplebadge));
        }
    }

    private FontAwesome.Icon getBadgeResource(BADGE badge) {
        FontAwesome.Icon icon = FontAwesome.Icon.faw_shopping_cart;
        switch (badge) {
            case BASKET:
                icon = FontAwesome.Icon.faw_archive;
                break;
            case SHOP:
                icon = FontAwesome.Icon.faw_shopping_cart;
                break;
        }
        return icon;
    }
}
