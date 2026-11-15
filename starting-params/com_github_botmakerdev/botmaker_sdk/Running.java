package com.botmaker.googlesignin.params.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk.Plans;
import com.botmaker.plugin.api.params.Param;
import com.botmaker.sdk.api.bot.SdkValue;
import com.botmaker.sdk.api.plan.Plan;

/** The BotMaker SDK's run parameters on this computer: each method is one, its body the value. */
@Param
public class Running {

    @SdkValue(SdkValue.Id.PLAN)
    @Param.Shown(description = "What the bot runs: one of its plans")
    public Plan plan() {
        return Plans.SIGN_IN;
    }
}
