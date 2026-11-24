package com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.googlesignin.Login;
import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.params.Params;
import com.botmaker.sdk.api.screens.Button;
import com.botmaker.sdk.api.screens.Do;
import com.botmaker.sdk.api.screens.Mark;
import com.botmaker.sdk.api.screens.Screen;
import com.botmaker.sdk.api.screens.Setup;
import com.botmaker.sdk.api.signin.NeedsYou;
import com.botmaker.sdk.api.signin.Refusal;

/**
 * Google's sign-in pages, one class and one constant each, told by what every look of the page shows: a field
 * Android states, and words, whatever their case. A message shown over a page (an address not found, a wrong
 * password) makes a page of its own, which the plain page is told apart from by that message's absence.
 *
 * <p>The map alone signs in: a page's {@code Do}s serve {@link Goals#SIGNED_IN}, the {@code first} preferred and the
 * {@code last} a last resort, and a field types a part of the dealt {@link Login} by its method, BotMaker typing the
 * secrets. A page only the user can answer is marked {@code needsYou}, one that ends the sign-in {@code refuses}.
 */
@SdkValue(SdkValue.Id.SCREEN_MAP)
public final class Screens {

    public static final class Email extends Screen {
        public final Mark forgot = Mark.of(Elements.FORGOT_EMAIL);
        public final Mark field = Mark.of(Elements.FIELD);
        public final Mark notFound = Mark.of(Elements.COULDNT_FIND).absent();
        public final Mark invalid = Mark.of(Elements.VALID_EMAIL).absent();
        public final Mark noCaptcha = Mark.of(Elements.CAPTCHA).absent();
        public final Button email = Button.of(Elements.FIELD).typesParameter(Login::email).clearing().submitting();
        public final Do signIn = Do.press(email).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Email").start().shot("screens/EMAIL.png");
        }
    }

    public static final class Password extends Screen {
        public final Mark field = Mark.of(Elements.PASSWORD);
        public final Mark wrong = Mark.of(Elements.WRONG_PASSWORD).absent();
        public final Mark again = Mark.of(Elements.ITS_YOU).absent();
        public final Mark noCaptcha = Mark.of(Elements.CAPTCHA).absent();
        public final Button password = Button.of(Elements.PASSWORD).typesSecret(Login::password).submitting();
        public final Do signIn = Do.press(password).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Password").from(EMAIL.email);
        }
    }

    /** The same list under the heading Google gives it after a way that didn't suit. */
    public static final class OtherWays extends Screen {
        public final Mark heading = Mark.of(Elements.ANOTHER_WAY_LIST);
        public final Button authenticator = Button.of(Elements.AUTHENTICATOR_ROW).occasional()
                .when(Params.when(Login::authenticator).isSet());
        public final Button phone = Button.of(Elements.PHONE_ROW).occasional();
        public final Button code = Button.of(Elements.CODE_ROW).occasional();
        public final Do byAuthenticator = Do.press(authenticator).serves(Goals.SIGNED_IN).first();
        public final Do byPhone = Do.press(phone).serves(Goals.SIGNED_IN);
        public final Do byCode = Do.press(code).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Other ways to sign in");
        }
    }

    /** A passkey is the device's owner's: another way. */
    public static final class Passkey extends Screen {
        public final Mark passkey = Mark.of(Elements.PASSKEY);
        public final Mark noWays = Mark.of(Elements.CHOOSE_HOW).absent();
        public final Button anotherWay = Button.of(Elements.TRY_ANOTHER_WAY).occasional();
        public final Do away = Do.press(anotherWay).serves(Goals.SIGNED_IN);

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

        @Override
        public Setup setup() {
            return Setup.named("Captcha").needsYou(NeedsYou.CAPTCHA);
        }
    }

    public static final class Refused extends Screen {
        public final Mark words = Mark.of(Elements.REFUSED);

        @Override
        public Setup setup() {
            return Setup.named("Sign-in refused").refuses(Refusal.REFUSED);
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
        public final Do scroll = Do.press(more).serves(Goals.SIGNED_IN).first();
        public final Do signIn = Do.press(accept).serves(Goals.SIGNED_IN);

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
        public final Do skipIt = Do.press(skip).serves(Goals.SIGNED_IN).first();
        public final Do notYet = Do.press(notNow).serves(Goals.SIGNED_IN);

        @Override
        public Setup setup() {
            return Setup.named("Add a phone number");
        }
    }

    public static final Email EMAIL = new Email();
    public static final Password PASSWORD = new Password();
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
