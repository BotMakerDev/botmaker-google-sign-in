package com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.googlesignin.GoogleSignIn;
import com.botmaker.sdk.api.bot.Run;
import com.botmaker.sdk.api.bot.SdkValue;

/** The BotMaker SDK's values for this bot, each an {@code @SdkValue} the SDK reads off this class. */
public final class Sdk {

    /** No home to walk to: a page the map doesn't know is the user's. */
    @SdkValue(SdkValue.Id.RUN)
    public static Run run() {
        return Run.of().home(GoogleSignIn::lost);
    }

    private Sdk() {}
}
