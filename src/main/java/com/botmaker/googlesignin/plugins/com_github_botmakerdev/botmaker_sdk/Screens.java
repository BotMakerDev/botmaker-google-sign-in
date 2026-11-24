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

    public static final Email EMAIL = new Email();
    public static final Password PASSWORD = new Password();
    private Screens() {}
}
