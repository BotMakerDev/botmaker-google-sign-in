package com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.plan.Plan;

/** The bot's one plan: sign in. */
@SdkValue(SdkValue.Id.PLANS)
public final class Plans {

    public static final Plan SIGN_IN = Plan.of(Plan.tier(Goals.SIGNED_IN));

    private Plans() {}
}
