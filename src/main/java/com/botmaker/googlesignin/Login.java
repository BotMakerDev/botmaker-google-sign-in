package com.botmaker.googlesignin;

import com.botmaker.sdk.api.secrets.Secret;

/**
 * The account a run signs in, dealt whole by a pool: its address, and the names of its password and authenticator
 * key as this PC's keyring keeps them. The map's fields name a part by its method ({@code Login::email}); BotMaker
 * types the secrets, the run never holds them. An account with no authenticator names a blank one.
 */
public record Login(String email, Secret password, Secret authenticator) {}
