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
import com.botmaker.sdk.api.geometry.Point;

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

    public static final class Home extends Screen {
        public final Mark privateDns = Mark.of(Elements.PRIVATE_DNS);
        public final Button passwordsAccounts = Button.of(Elements.PASSWORDS_ACCOUNTS).free();

        @Override
        public Setup setup() {
            return Setup.named("Home").start().at(new Point(-266, -48)).shot("screens/HOME.png");
        }
    }

    public static final Home HOME = new Home();

    private Screens() {
    }
}
