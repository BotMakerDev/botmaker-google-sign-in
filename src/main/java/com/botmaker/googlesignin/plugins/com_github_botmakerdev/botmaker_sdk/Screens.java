package com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.googlesignin.GoogleSignIn;
import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.screens.Button;
import com.botmaker.sdk.api.screens.Custom;
import com.botmaker.sdk.api.screens.Do;
import com.botmaker.sdk.api.screens.Mark;
import com.botmaker.sdk.api.screens.Screen;
import com.botmaker.sdk.api.screens.Setup;

/**
 * Google's sign-in pages, one class and one constant each, told by what every look of the page shows: a field
 * Android states, and words, whatever their case. A message shown over a page (an address not found, a wrong
 * password) makes a page of its own, which the plain page is told apart from by that message's absence. Each page
 * has one {@code Do} serving {@link Goals#SIGNED_IN}: the plan does the one of the page Google shows.
 */
@SdkValue(SdkValue.Id.SCREEN_MAP)
public final class Screens {

    public static final class Email extends Screen {
        public final Mark forgot = Mark.of(Elements.FORGOT_EMAIL);
        public final Mark field = Mark.of(Elements.FIELD);
        public final Mark notFound = Mark.of(Elements.COULDNT_FIND).absent();
        public final Mark invalid = Mark.of(Elements.VALID_EMAIL).absent();
        public final Mark noCaptcha = Mark.of(Elements.CAPTCHA).absent();
        public final Button next = Button.of(Elements.NEXT);
        public final Custom enter = Custom.of(GoogleSignIn::enterEmail);
        public final Do signIn = Do.press(enter).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Email").start();
        }
    }

    public static final class NoSuchAccount extends Screen {
        public final Mark forgot = Mark.of(Elements.FORGOT_EMAIL);
        public final Mark field = Mark.of(Elements.FIELD);
        public final Mark notFound = Mark.of(Elements.COULDNT_FIND);
        public final Custom stop = Custom.of(GoogleSignIn::noSuchAccount);
        public final Do end = Do.press(stop).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("No such account");
        }
    }

    public static final class InvalidEmail extends Screen {
        public final Mark forgot = Mark.of(Elements.FORGOT_EMAIL);
        public final Mark field = Mark.of(Elements.FIELD);
        public final Mark invalid = Mark.of(Elements.VALID_EMAIL);
        public final Custom stop = Custom.of(GoogleSignIn::noSuchAccount);
        public final Do end = Do.press(stop).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Not an address");
        }
    }

    public static final class Password extends Screen {
        public final Mark field = Mark.of(Elements.PASSWORD);
        public final Mark wrong = Mark.of(Elements.WRONG_PASSWORD).absent();
        public final Mark again = Mark.of(Elements.ITS_YOU).absent();
        public final Mark noCaptcha = Mark.of(Elements.CAPTCHA).absent();
        public final Button next = Button.of(Elements.NEXT);
        public final Custom enter = Custom.of(GoogleSignIn::enterPassword);
        public final Do signIn = Do.press(enter).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Password").from(EMAIL.enter);
        }
    }

    public static final class WrongPassword extends Screen {
        public final Mark field = Mark.of(Elements.PASSWORD);
        public final Mark wrong = Mark.of(Elements.WRONG_PASSWORD);
        public final Custom stop = Custom.of(GoogleSignIn::wrongPassword);
        public final Do end = Do.press(stop).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Wrong password");
        }
    }

    /** Google asking the password again to be sure it's the user: BotMaker types it once only. */
    public static final class PasswordAgain extends Screen {
        public final Mark field = Mark.of(Elements.PASSWORD);
        public final Mark again = Mark.of(Elements.ITS_YOU);
        public final Mark wrong = Mark.of(Elements.WRONG_PASSWORD).absent();
        public final Custom ask = Custom.of(GoogleSignIn::look);
        public final Do signIn = Do.press(ask).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Password asked again");
        }
    }

    /** A code's page: an authenticator's, or one Google texted. */
    public static final class Code extends Screen {
        public final Mark field = Mark.of(Elements.FIELD);
        public final Mark code = Mark.of(Elements.CODE);
        public final Mark notEmail = Mark.of(Elements.FORGOT_EMAIL).absent();
        public final Mark notBackup = Mark.of(Elements.BACKUP_CODES).absent();
        public final Mark noPassword = Mark.of(Elements.PASSWORD).absent();
        public final Button next = Button.of(Elements.NEXT).occasional();
        public final Button verify = Button.of(Elements.VERIFY).occasional();
        public final Button anotherWay = Button.of(Elements.TRY_ANOTHER_WAY).occasional();
        public final Custom enter = Custom.of(GoogleSignIn::enterCode);
        public final Do signIn = Do.press(enter).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Code");
        }
    }

    public static final class BackupCode extends Screen {
        public final Mark field = Mark.of(Elements.FIELD);
        public final Mark backup = Mark.of(Elements.BACKUP_CODES);
        public final Mark noPassword = Mark.of(Elements.PASSWORD).absent();
        public final Button next = Button.of(Elements.NEXT).occasional();
        public final Button verify = Button.of(Elements.VERIFY).occasional();
        public final Button anotherWay = Button.of(Elements.TRY_ANOTHER_WAY).occasional();
        public final Custom enter = Custom.of(GoogleSignIn::enterBackupCode);
        public final Do signIn = Do.press(enter).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Backup code");
        }
    }

    /** Google's prompt sent to the account's phone. */
    public static final class PhoneTap extends Screen {
        public final Mark check = Mark.of(Elements.CHECK_YOUR_PHONE);
        public final Mark noField = Mark.of(Elements.FIELD).absent();
        public final Button anotherWay = Button.of(Elements.TRY_ANOTHER_WAY).occasional();
        public final Custom answer = Custom.of(GoogleSignIn::phoneTap);
        public final Do signIn = Do.press(answer).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Approve on the phone");
        }
    }

    /**
     * The list of ways, under its usual heading: the one Try another way opens, or the one Google shows right after
     * the email when the account also has a passkey, whose password row is then the way.
     */
    public static final class Ways extends Screen {
        public final Mark heading = Mark.of(Elements.CHOOSE_HOW);
        public final Button password = Button.of(Elements.PASSWORD_ROW).occasional();
        public final Button phone = Button.of(Elements.PHONE_ROW).occasional();
        public final Button authenticator = Button.of(Elements.AUTHENTICATOR_ROW).occasional();
        public final Button code = Button.of(Elements.CODE_ROW).occasional();
        public final Button backup = Button.of(Elements.BACKUP_ROW).occasional();
        public final Custom pick = Custom.of(GoogleSignIn::pickWay);
        public final Do signIn = Do.press(pick).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Ways to sign in");
        }
    }

    /** The same list under the heading Google gives it after a way that didn't suit. */
    public static final class OtherWays extends Screen {
        public final Mark heading = Mark.of(Elements.ANOTHER_WAY_LIST);
        public final Button phone = Button.of(Elements.PHONE_ROW).occasional();
        public final Button authenticator = Button.of(Elements.AUTHENTICATOR_ROW).occasional();
        public final Button code = Button.of(Elements.CODE_ROW).occasional();
        public final Button backup = Button.of(Elements.BACKUP_ROW).occasional();
        public final Custom pick = Custom.of(GoogleSignIn::pickOtherWay);
        public final Do signIn = Do.press(pick).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Other ways to sign in");
        }
    }

    public static final class Passkey extends Screen {
        public final Mark passkey = Mark.of(Elements.PASSKEY);
        public final Mark noWays = Mark.of(Elements.CHOOSE_HOW).absent();
        public final Button anotherWay = Button.of(Elements.TRY_ANOTHER_WAY).occasional();
        public final Custom away = Custom.of(GoogleSignIn::passkey);
        public final Do signIn = Do.press(away).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Passkey");
        }
    }

    /** Android's sheet offering a screen lock for passkeys, over the sign-in: closed by a tap above it, never set up. */
    public static final class PasskeyScreenLock extends Screen {
        public final Mark words = Mark.of(Elements.PASSKEY_SCREEN_LOCK);
        public final Button close = Button.of(Elements.ABOVE_SHEET).occasional();
        public final Do signIn = Do.press(close).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Screen lock for passkeys");
        }
    }

    /** A second step the bot doesn't read, under any heading, offering another way. */
    public static final class TwoStep extends Screen {
        public final Button anotherWay = Button.of(Elements.TRY_ANOTHER_WAY).marking();
        public final Mark noPasskey = Mark.of(Elements.PASSKEY).absent();
        public final Mark noWays = Mark.of(Elements.CHOOSE_HOW).absent();
        public final Mark noPhone = Mark.of(Elements.CHECK_YOUR_PHONE).absent();
        public final Mark noField = Mark.of(Elements.FIELD).absent();
        public final Mark noPassword = Mark.of(Elements.PASSWORD).absent();
        public final Do signIn = Do.press(anotherWay).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("2-Step Verification");
        }
    }

    public static final class Captcha extends Screen {
        public final Mark words = Mark.of(Elements.CAPTCHA);
        public final Custom ask = Custom.of(GoogleSignIn::captcha);
        public final Do signIn = Do.press(ask).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Captcha");
        }
    }

    public static final class Refused extends Screen {
        public final Mark words = Mark.of(Elements.REFUSED);
        public final Custom stop = Custom.of(GoogleSignIn::refused);
        public final Do end = Do.press(stop).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Sign-in refused");
        }
    }

    public static final class Terms extends Screen {
        public final Button agree = Button.of(Elements.I_AGREE).marking();
        public final Do signIn = Do.press(agree).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Google's terms");
        }
    }

    /** Google's services: More scrolls the page down until it turns into Accept. */
    public static final class Services extends Screen {
        public final Mark heading = Mark.of(Elements.GOOGLE_SERVICES);
        public final Button more = Button.of(Elements.MORE).occasional();
        public final Button accept = Button.of(Elements.ACCEPT).occasional();
        public final Custom next = Custom.of(GoogleSignIn::services);
        public final Do signIn = Do.press(next).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Google services");
        }
    }

    public static final class PhonePrompt extends Screen {
        public final Mark words = Mark.of(Elements.PHONE_NUMBER);
        public final Mark noCode = Mark.of(Elements.CODE).absent();
        public final Mark notEmail = Mark.of(Elements.FORGOT_EMAIL).absent();
        public final Button skip = Button.of(Elements.SKIP).occasional();
        public final Button notNow = Button.of(Elements.NOT_NOW).occasional();
        public final Custom away = Custom.of(GoogleSignIn::skipPhone);
        public final Do signIn = Do.press(away).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Add a phone number");
        }
    }

    public static final Email EMAIL = new Email();
    public static final NoSuchAccount NO_SUCH_ACCOUNT = new NoSuchAccount();
    public static final InvalidEmail INVALID_EMAIL = new InvalidEmail();
    public static final Password PASSWORD = new Password();
    public static final WrongPassword WRONG_PASSWORD = new WrongPassword();
    public static final PasswordAgain PASSWORD_AGAIN = new PasswordAgain();
    public static final Code CODE = new Code();
    public static final BackupCode BACKUP_CODE = new BackupCode();
    public static final PhoneTap PHONE_TAP = new PhoneTap();
    public static final Ways WAYS = new Ways();
    public static final OtherWays OTHER_WAYS = new OtherWays();
    public static final Passkey PASSKEY = new Passkey();
    public static final PasskeyScreenLock PASSKEY_SCREEN_LOCK = new PasskeyScreenLock();
    public static final TwoStep TWO_STEP = new TwoStep();
    public static final Captcha CAPTCHA = new Captcha();
    public static final Refused REFUSED = new Refused();
    public static final Terms TERMS = new Terms();
    public static final Services SERVICES = new Services();
    public static final PhonePrompt PHONE_PROMPT = new PhonePrompt();

    private Screens() {}
}
