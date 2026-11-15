package com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.sdk.api.plan.Goal;
import com.botmaker.sdk.api.signin.SignIn;

/** What the bot wants: the request's account on the device, as the host sees it. */
public final class Goals {

    public static final Goal SIGNED_IN = Goal.of(SignIn::signedIn);

    private Goals() {}
}
