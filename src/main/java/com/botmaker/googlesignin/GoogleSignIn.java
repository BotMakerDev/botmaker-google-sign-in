package com.botmaker.googlesignin;

import com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk.Sdk;
import com.botmaker.sdk.api.bot.Bot;

/**
 * Signs an emulator instance into one Google account, the {@link Login} its run is dealt, by its screen map alone:
 * the map's fields type the address, and BotMaker types the password and the authenticator's code from this PC's
 * keyring. What only the user can do (a phone's prompt, a texted code, a captcha) the map marks, and the run tells
 * them and waits.
 */
public final class GoogleSignIn extends Bot {

    private GoogleSignIn() {}

    public static void main(String[] args) {
        run(Sdk.class);
    }
}
