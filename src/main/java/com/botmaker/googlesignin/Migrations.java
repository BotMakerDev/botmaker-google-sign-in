package com.botmaker.googlesignin;

import com.botmaker.plugin.api.meta.Migrate;
import com.botmaker.plugin.api.meta.Refactor;

/**
 * How this bot moves off the API its plugins changed (Review ▸ Migrate…).
 * <p>Each @Migrate method names one old method, constructor, field or type by its own signature, and its
 * body is what each use becomes. BotMaker reads it and never runs it; the bot never calls it. Delete a
 * method, or this class, once nothing uses its old element.
 */
public final class Migrations {
    private Migrations() {
    }

    @Refactor(value = "write the replacement", done = true)
    @Migrate(name = "com.botmaker.sdk.api.signin.NeedsYou", kind = Migrate.Kind.FIELD)
    static Object CAPTCHA() {
        return null;
    }

    @Refactor(value = "write the replacement", done = true)
    @Migrate(name = "com.botmaker.sdk.api.signin.SecondStep", kind = Migrate.Kind.FIELD)
    static Object BACKUP_CODE() {
        return null;
    }
}
