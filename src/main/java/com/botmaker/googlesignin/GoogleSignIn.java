package com.botmaker.googlesignin;

import com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk.Elements;
import com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk.Screens;
import com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk.Sdk;
import com.botmaker.sdk.api.bot.Bot;
import com.botmaker.sdk.api.screens.Button;
import com.botmaker.sdk.api.screens.Navigate;
import com.botmaker.sdk.api.signin.NeedsYou;
import com.botmaker.sdk.api.signin.Refusal;
import com.botmaker.sdk.api.signin.SecondStep;
import com.botmaker.sdk.api.signin.SignIn;
import com.botmaker.sdk.api.signin.Typed;
import com.botmaker.sdk.api.time.Wait;

/**
 * Signs an emulator instance into one Google account, started by BotMaker's host for one request. The run never holds
 * the account: it names the field, and the host types the address, the password or the code into it. What only the
 * user can do (a phone's prompt, a captcha, a page this bot doesn't know) it asks through the host, and reads on.
 */
public final class GoogleSignIn extends Bot {

    /** How long the run leaves the user after asking them, before it looks again. */
    private static final int USER_SECONDS = 10;

    private GoogleSignIn() {}

    public static void main(String[] args) {
        run(Sdk.class);
    }

    /** The address in the page's one field, then Next. */
    public static void enterEmail() {
        typed(SignIn.typeEmail(Elements.FIELD));
        Navigate.press(Screens.EMAIL.next);
    }

    public static void enterPassword() {
        typed(SignIn.typePassword(Elements.PASSWORD));
        Navigate.press(Screens.PASSWORD.next);
    }

    /** The code, when the account answers by one; else another way. */
    public static void enterCode() {
        Screens.Code page = Screens.CODE;
        SecondStep method = method();
        if (method == SecondStep.PHONE || method == SecondStep.BACKUP_CODE) {
            anotherWay(page.anotherWay);
            return;
        }
        typed(SignIn.typeCode(Elements.FIELD));
        submit(page.next, page.verify);
    }

    public static void enterBackupCode() {
        Screens.BackupCode page = Screens.BACKUP_CODE;
        SecondStep method = method();
        if (method != SecondStep.BACKUP_CODE && method != SecondStep.ASK && method != SecondStep.UNKNOWN) {
            anotherWay(page.anotherWay);
            return;
        }
        typed(SignIn.typeCode(Elements.FIELD));
        submit(page.next, page.verify);
    }

    /** The user approves on their phone; an account that answers otherwise goes another way. */
    public static void phoneTap() {
        SecondStep method = method();
        if (method == SecondStep.PHONE || method == SecondStep.ASK || method == SecondStep.UNKNOWN) {
            ask(NeedsYou.PHONE);
        } else {
            anotherWay(Screens.PHONE_TAP.anotherWay);
        }
    }

    /**
     * The password, when the list offers it and no second step (the list Google shows before the password, to an
     * account with a passkey); else the account's second step, so a list past the password never goes back to it.
     */
    public static void pickWay() {
        Screens.Ways list = Screens.WAYS;
        boolean secondStep = shown(list.phone) || shown(list.authenticator) || shown(list.code) || shown(list.backup);
        if (!secondStep && shown(list.password)) Navigate.press(list.password);
        else pick(list.phone, list.authenticator, list.code, list.backup);
    }

    public static void pickOtherWay() {
        Screens.OtherWays list = Screens.OTHER_WAYS;
        pick(list.phone, list.authenticator, list.code, list.backup);
    }

    /** A passkey is the device's owner's: another way, or the user. */
    public static void passkey() {
        anotherWay(Screens.PASSKEY.anotherWay);
    }

    public static void captcha() {
        ask(NeedsYou.CAPTCHA);
    }

    /** A page the bot doesn't read, or one it leaves to the user. */
    public static void look() {
        ask(NeedsYou.LOOK);
    }

    public static void services() {
        Screens.Services page = Screens.SERVICES;
        Navigate.press(shown(page.more) ? page.more : page.accept);
    }

    public static void skipPhone() {
        Screens.PhonePrompt page = Screens.PHONE_PROMPT;
        Navigate.press(shown(page.skip) ? page.skip : page.notNow);
    }

    public static void noSuchAccount() {
        stop(Refusal.NO_SUCH_ACCOUNT);
    }

    public static void wrongPassword() {
        stop(Refusal.WRONG_PASSWORD);
    }

    public static void refused() {
        stop(Refusal.REFUSED);
    }

    /** Tells the host why Google refused, so it deals with the account as that asks, then stops. */
    private static void stop(Refusal why) {
        SignIn.refused(why);
        Bot.stop(why.displayName());
    }

    /** The run's home: on a page the map doesn't know, the user looks; on a known one, nothing. */
    public static void lost() {
        if (Navigate.where().isEmpty()) look();
    }

    /** The row of the account's method, else the user. */
    private static void pick(Button phone, Button authenticator, Button code, Button backup) {
        Button row = switch (method()) {
            case PHONE -> phone;
            case AUTHENTICATOR -> authenticator;
            case CODE -> shown(authenticator) ? authenticator : code;
            case BACKUP_CODE -> backup;
            case ASK, UNKNOWN -> null;
        };
        if (row != null && shown(row)) Navigate.press(row);
        else look();
    }

    private static void anotherWay(Button anotherWay) {
        if (shown(anotherWay)) Navigate.press(anotherWay);
        else look();
    }

    private static void submit(Button next, Button verify) {
        Navigate.press(shown(next) ? next : verify);
    }

    private static boolean shown(Button button) {
        return Navigate.state(button).isPresent();
    }

    private static SecondStep method() {
        return SignIn.request().map(SignIn.Request::method).orElse(SecondStep.ASK);
    }

    private static void ask(NeedsYou kind) {
        SignIn.needsYou(kind);
        Wait.seconds(USER_SECONDS);
    }

    private static void typed(Typed typed) {
        if (!typed.isTyped()) Bot.stop(typed.why());
    }
}
