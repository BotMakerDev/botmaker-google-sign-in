package com.botmaker.googlesignin.params.bot;

import com.botmaker.googlesignin.Login;
import com.botmaker.plugin.api.params.Param;
import com.botmaker.sdk.api.secrets.Secret;

/** The account a run signs in: one run alone signs this one, several are each dealt one by {@link AccountPools}. */
@Param
public class SignInParams {

    /** Its address, and the names of its password and authenticator key in the secret picker. */
    public Login login() {
        return new Login("", Secret.of(""), Secret.of(""));
    }
}
