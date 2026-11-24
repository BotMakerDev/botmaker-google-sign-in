package com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.screens.Element;

/**
 * What the bot finds on Google's pages, by what every emulator's tree shows: the field Android states, and words.
 * Google's HTML ids reach the tree on some emulators only (MuMu 15, not LDPlayer 14), so none is a clue here.
 */
@SdkValue(SdkValue.Id.ELEMENTS)
public final class Elements {

    /** The page's text field: the address's, or a code's. */
    public static final Element FIELD = Element.textField();
    public static final Element PASSWORD = Element.passwordField();
    public static final Element FORGOT_EMAIL = Element.byText(Texts.FORGOT_EMAIL);
    public static final Element COULDNT_FIND = Element.byContaining(Texts.COULDNT_FIND);
    public static final Element VALID_EMAIL = Element.byContaining(Texts.VALID_EMAIL);
    public static final Element WRONG_PASSWORD = Element.byContaining(Texts.WRONG_PASSWORD);
    public static final Element ITS_YOU = Element.byContaining(Texts.ITS_YOU);
    public static final Element CODE = Element.byContaining(Texts.CODE);
    public static final Element BACKUP_CODES = Element.byContaining(Texts.BACKUP_CODES);
    /** What a code Google texted says of itself. */
    public static final Element TEXT_MESSAGE = Element.byContaining(Texts.TEXT_MESSAGE);
    public static final Element WRONG_CODE = Element.byContaining(Texts.WRONG_CODE);
    public static final Element CHECK_YOUR_PHONE = Element.byContaining(Texts.CHECK_YOUR_PHONE);
    public static final Element CHOOSE_HOW = Element.byContaining(Texts.CHOOSE_HOW);
    public static final Element ANOTHER_WAY_LIST = Element.byContaining(Texts.ANOTHER_WAY_LIST);
    public static final Element PASSKEY = Element.byContaining(Texts.PASSKEY);
    public static final Element TRY_ANOTHER_WAY = Element.byText(Texts.TRY_ANOTHER_WAY);
    /** The list of ways' row for the password, offered first when the account also has a passkey. */
    public static final Element PASSWORD_ROW = Element.byText(Texts.ENTER_PASSWORD);
    public static final Element PASSKEY_SCREEN_LOCK = Element.byContaining(Texts.PASSKEY_SCREEN_LOCK);
    /** The screen above a bottom sheet: a tap there closes the sheet. */
    public static final Element ABOVE_SHEET = Element.byId("touch_outside");
    public static final Element CAPTCHA = Element.byContaining(Texts.CAPTCHA);
    public static final Element REFUSED = Element.byContaining(Texts.REFUSED);
    public static final Element I_AGREE = Element.byText(Texts.I_AGREE);
    public static final Element GOOGLE_SERVICES = Element.byContaining(Texts.GOOGLE_SERVICES);
    public static final Element MORE = Element.byText(Texts.MORE);
    public static final Element ACCEPT = Element.byText(Texts.ACCEPT);
    public static final Element PHONE_NUMBER = Element.byContaining(Texts.PHONE_NUMBER);
    public static final Element SKIP = Element.byText(Texts.SKIP);
    public static final Element NOT_NOW = Element.byText(Texts.NOT_NOW);
    /** A row of the list of ways, each the tappable row around its words. */
    public static final Element PHONE_ROW = Element.byContaining(Texts.TAP_YES);
    public static final Element AUTHENTICATOR_ROW = Element.byContaining(Texts.AUTHENTICATOR);
    /** The first code's row: a texted code's, or the authenticator's when no other is offered. */
    public static final Element CODE_ROW = Element.byContaining(Texts.VERIFICATION_CODE).index(0);
    public static final Element FORGOT_EMAIL_2 = Element.byText(Texts.FORGOT_EMAIL_2)
            .shot("elements/forgot_email_2.png");
    public static final Element NEXT = Element.byText(Texts.NEXT).shot("elements/next.png");
    public static final Element PRIVATE_DNS = Element.byHolding(Texts.PRIVATE_DNS).shot("elements/private_dns.png");
    public static final Element PASSWORDS_ACCOUNTS = Element.byHolding(Texts.PASSWORDS_ACCOUNTS)
            .shot("elements/passwords_accounts.png");
    public static final Element SEARCH_ACTION_BAR_TWO_PANE = Element.byId("search_action_bar_two_pane");
    public static final Element LAYOUT_RESULTS = Element.byId("layout_results");
    public static final Element NAVIGATE_UP = Element.byLabel(Texts.NAVIGATE_UP);
    public static final Element SEARCH_SRC_TEXT = Element.byId("search_src_text");
    public static final Element MORE_OPTIONS = Element.byLabel(Texts.MORE_OPTIONS);

    private Elements() {}
}
