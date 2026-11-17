package com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk;

import com.botmaker.sdk.api.bot.Run;
import com.botmaker.sdk.api.bot.SdkValue;

/** The BotMaker SDK's values for this bot, each an {@code @SdkValue} the SDK reads off this class. */
public final class Sdk {

    /**
     * No home of the bot's: at start-up and after anything unexpected the run walks back to the map's root, the
     * email page, and signs in from there.
     */
    @SdkValue(SdkValue.Id.RUN)
    public static Run run() {
        return Run.of();
    }

    private Sdk() {}
}
