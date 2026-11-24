package com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.text.Words;

/**
 * The words of Google's sign-in pages, in English, one constant each. A button's are its whole text, whatever its case
 * ({@code Next} on the web page, {@code NEXT} on LDPlayer's native one); a sentence's are the part that tells it.
 */
@SdkValue(SdkValue.Id.TEXTS)
public final class Texts {

    public static final Words FORGOT_EMAIL = Words.of("Forgot email");
    public static final Words COULDNT_FIND = Words.of("couldn't find");
    public static final Words VALID_EMAIL = Words.of("enter a valid email");
    public static final Words WRONG_PASSWORD = Words.of("wrong password");
    public static final Words ITS_YOU = Words.of("it's you");
    public static final Words CODE = Words.of("code");
    public static final Words BACKUP_CODES = Words.of("8-digit backup code");
    public static final Words TEXT_MESSAGE = Words.of("text message");
    public static final Words WRONG_CODE = Words.of("wrong code");
    public static final Words CHECK_YOUR_PHONE = Words.of("check your phone");
    public static final Words CHOOSE_HOW = Words.of("choose how you want to sign in");
    public static final Words ANOTHER_WAY_LIST = Words.of("verify it's you another way");
    public static final Words PASSKEY = Words.of("use your passkey to");
    public static final Words TRY_ANOTHER_WAY = Words.of("Try another way");
    public static final Words ENTER_PASSWORD = Words.of("Enter your password");
    public static final Words PASSKEY_SCREEN_LOCK = Words.of("set up screen lock to use passkeys");
    public static final Words CAPTCHA = Words.of("type the text you hear or see");
    public static final Words REFUSED = Words.of("couldn't sign you in");
    public static final Words I_AGREE = Words.of("I agree");
    public static final Words GOOGLE_SERVICES = Words.of("google services");
    public static final Words MORE = Words.of("More");
    public static final Words ACCEPT = Words.of("Accept");
    public static final Words PHONE_NUMBER = Words.of("phone number");
    public static final Words SKIP = Words.of("Skip");
    public static final Words NOT_NOW = Words.of("Not now");
    public static final Words TAP_YES = Words.of("tap yes");
    public static final Words AUTHENTICATOR = Words.of("google authenticator");
    public static final Words VERIFICATION_CODE = Words.of("verification code");
    public static final Words FORGOT_EMAIL_2 = Words.of("Forgot email?");

    private Texts() {}
}
