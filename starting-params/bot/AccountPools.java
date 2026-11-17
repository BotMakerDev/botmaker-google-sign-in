package com.botmaker.googlesignin.params.bot;

import com.botmaker.googlesignin.Login;
import com.botmaker.plugin.api.params.Pool;
import com.botmaker.plugin.api.params.Pools;
import com.botmaker.sdk.api.secrets.Secret;

/** The accounts several runs at once are dealt from: each run, on its own instance, one login. */
@Pools
public class AccountPools {

    public Pool<Login> logins() {
        return Pool.of(new Login("", Secret.of(""), Secret.of(""))).fills(SignInParams::login);
    }
}
