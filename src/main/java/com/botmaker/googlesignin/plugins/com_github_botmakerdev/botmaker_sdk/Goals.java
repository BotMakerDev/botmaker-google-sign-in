package com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.googlesignin.Login;
import com.botmaker.sdk.api.plan.Goal;
import com.botmaker.sdk.api.signin.Accounts;

/** What the bot wants: the dealt account on the instance, as BotMaker reads Android's accounts. */
public final class Goals {

    public static final Goal SIGNED_IN = Goal.of(Accounts.signedIn(Login::email));

    private Goals() {}
}
