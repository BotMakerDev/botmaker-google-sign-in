package com.botmaker.googlesignin;

import com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk.Goals;
import com.botmaker.googlesignin.plugins.com_github_botmakerdev.botmaker_sdk.Screens;
import com.botmaker.sdk.api.screens.Custom;
import com.botmaker.sdk.api.screens.Do;
import com.botmaker.sdk.api.screens.Screen;
import com.botmaker.sdk.api.screens.ScreenCheck;
import com.botmaker.sdk.api.screens.Setup;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Each of Google's pages reads as exactly one of the map's screens: the trees captured on the emulators, and small
 * trees built in Google's shapes for the pages past the email, which need a real account to capture. A page the map
 * leaves to the user reads as none, as a run's home then asks the user.
 */
class ScreensTest {

    private static List<String> on(String xml) {
        return ScreenCheck.which(Screens.class, xml);
    }

    private static String captured(String name) {
        try (InputStream in = ScreensTest.class.getResourceAsStream("/trees/" + name + ".xml")) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /** A one-window tree of Google's app holding {@code nodes}. */
    private static String tree(String... nodes) {
        return "<hierarchy rotation=\"0\"><node resource-id=\"\" text=\"\" class=\"android.webkit.WebView\""
                + " package=\"com.google.android.gms\" bounds=\"[0,0][1600,900]\">" + String.join("", nodes)
                + "</node></hierarchy>";
    }

    /** Words at row {@code row}, each 100 px high. */
    private static String words(String text, int row) {
        return node("android.widget.TextView", text, row, "");
    }

    private static String field(int row) {
        return node("android.widget.EditText", "", row, "");
    }

    private static String password(int row) {
        return node("android.widget.EditText", "", row, " password=\"true\"");
    }

    private static String node(String className, String text, int row, String more) {
        return "<node resource-id=\"\" text=\"" + text + "\" class=\"" + className + "\" package=\"com.google.android.gms\""
                + more + " bounds=\"[100," + row * 100 + "][700," + (row * 100 + 80) + "]\"/>";
    }

    /** A row as a WebView gives it: a clickable box around its words. */
    private static String box(String text, int row) {
        return "<node resource-id=\"\" text=\"\" class=\"android.view.View\" package=\"com.google.android.gms\""
                + " clickable=\"true\" bounds=\"[50," + row * 100 + "][900," + (row * 100 + 90) + "]\">"
                + words(text, row) + "</node>";
    }

    private static String challenges(String... rows) {
        List<String> nodes = new ArrayList<>(List.of(words("2-Step Verification", 1),
                words("Choose how you want to sign in:", 2)));
        int row = 3;
        for (String text : rows) nodes.add(box(text, row++));
        return tree(nodes.toArray(String[]::new));
    }

    @Test
    void readsTheCapturedPages() {
        assertEquals(List.of("EMAIL"), on(captured("mumu-a15-email")), "MuMu 15, with Google's ids");
        assertEquals(List.of("EMAIL"), on(captured("ldplayer-a14-email")), "LDPlayer 14: no id, a native NEXT");
        assertEquals(List.of("NO_SUCH_ACCOUNT"), on(captured("mumu-a15-no-such-account")));
        assertEquals(List.of(), on(captured("mumu-a15-accounts-none")), "Settings' accounts are no sign-in page");
    }

    @Test
    void readsTheEmailPageByItsWordsAndItsOneField() {
        assertEquals(List.of("INVALID_EMAIL"), on(tree(words("Forgot email?", 4), field(2),
                words("Enter a valid email or phone number", 3))));
        assertEquals(List.of("CODE"), on(tree(words("2-Step Verification", 1), words("Enter the code", 2), field(4),
                words("Create account", 6))), "a code's one field is the code's, even beside the email page's words");
        assertEquals(List.of(), on(tree(words("Create account", 1), field(4), field(5))));
        assertEquals(List.of(), on(tree(field(4))), "a page with no words yet is loading");
    }

    @Test
    void readsThePasswordPages() {
        assertEquals(List.of("PASSWORD"), on(tree(words("Welcome", 1), words("user@example.com", 2), password(4),
                words("Next", 5))));
        assertEquals(List.of("WRONG_PASSWORD"), on(tree(words("Welcome", 1), password(4),
                words("Wrong password. Try again or click Forgot password to reset it.", 6), words("Next", 5))));
        assertEquals(List.of("PASSWORD_AGAIN"), on(tree(words("Verify it’s you", 1), password(4), words("Next", 5))),
                "a password asked again is the user's");
        assertEquals(List.of("PASSWORD"), on(tree(words("Welcome", 1),
                words("Select “Try another way” to use your passkey for an easier, more secure sign-in", 2),
                password(4), words("Next", 5), words("Try another way", 6))),
                "the password page's passkey hint is not the passkey's page");
    }

    @Test
    void readsEachSecondStep() {
        assertEquals(List.of("PHONE_TAP"), on(tree(words("2-Step Verification", 1), words("Check your phone", 2),
                words("Try another way", 6))));
        assertEquals(List.of("WAYS"), on(challenges("Tap Yes on your phone or tablet",
                "Get a verification code from the Google Authenticator app", "Enter one of your 8-digit backup codes")));
        assertEquals(List.of("OTHER_WAYS"), on(tree(words("Verify it’s you another way", 1),
                box("Use your passkey", 2), box("Tap Yes on your phone or tablet", 3),
                box("Enter one of your 8-digit backup codes", 4))));
        assertEquals(List.of("PASSKEY"), on(tree(words("Use your passkey to confirm it’s really you", 1),
                words("2-Step Verification", 4), words("Your device will ask for your fingerprint, face, or screen lock", 5),
                words("Try another way", 6), words("Continue", 7))));
        assertEquals(List.of("TWO_STEP"), on(tree(words("Confirm you’re signing in", 1),
                words("Try another way", 5))), "a page offering another way is a second step, whatever its heading");
        assertEquals(List.of(), on(tree(words("2-Step Verification", 1), words("Insert your security key", 2))),
                "a second step with no other way is the user's");
        assertEquals(List.of(), on(tree(words("Welcome", 1), words("More ways to verify", 5))));
    }

    /** An account with a passkey: Google offers the screen-lock sheet, then the list of ways before the password. */
    @Test
    void readsThePasskeyPagesBeforeThePassword() {
        assertEquals(List.of("WAYS"), on(tree(words("Welcome", 1), words("Choose how you want to sign in:", 2),
                box("Use your passkey", 3), box("Enter your password", 4), words("Try another way", 6))));
        String sheet = "<hierarchy rotation=\"0\"><node resource-id=\"com.google.android.gms:id/touch_outside\""
                + " text=\"\" class=\"android.view.View\" package=\"com.google.android.gms\" clickable=\"true\""
                + " bounds=\"[0,0][1600,900]\"/>" + "<node resource-id=\"\" text=\"\" class=\"android.widget.FrameLayout\""
                + " package=\"com.google.android.gms\" bounds=\"[300,500][1300,900]\">"
                + words("Set up screen lock to use passkeys", 6) + words("Use a different device", 8) + "</node></hierarchy>";
        assertEquals(List.of("PASSKEY_SCREEN_LOCK"), on(sheet));
    }

    @Test
    void readsTheCodePages() {
        assertEquals(List.of("CODE"), on(tree(words("2-Step Verification", 1), field(3), words("Enter the code", 4),
                words("Next", 5), words("Try another way", 6))));
        assertEquals(List.of("WRONG_CODE"), on(tree(words("2-Step Verification", 1), field(3),
                words("Wrong code. Try again.", 4), words("Next", 5), words("Try another way", 6))),
                "a refused code is never typed again");
        assertEquals(List.of("TEXTED_CODE"), on(tree(words("2-Step Verification", 1), field(3),
                words("A text message with a 6-digit verification code was just sent", 4), words("Next", 5))),
                "a texted code is the user's");
        assertEquals(List.of("CODE"), on(tree(words("2-Step Verification", 1), field(3),
                words("Lost your phone? Use a backup code", 4))), "a backup code offered is not its page");
        assertEquals(List.of("BACKUP_CODE"), on(tree(words("2-Step Verification", 1), field(3),
                words("Enter one of your 8-digit backup codes", 4), words("Next", 5))));
    }

    @Test
    void readsThePagesAroundTheSignIn() {
        assertEquals(List.of("CAPTCHA"), on(tree(field(3), words("Type the text you hear or see", 4))));
        assertEquals(List.of("CAPTCHA"), on(tree(words("Forgot email?", 2), field(3), field(5),
                words("Type the text you hear or see", 4))), "a captcha over the email page is the user's");
        assertEquals(List.of("CAPTCHA"), on(tree(password(3), field(5),
                words("Type the text you hear or see", 4))), "a web page's no-break space is a space");
        assertEquals(List.of("REFUSED"), on(tree(words("Couldn’t sign you in", 1),
                words("This browser or app may not be secure.", 2))));
        assertEquals(List.of("TERMS"), on(tree(words("Terms of Service", 1), words("I agree", 5))));
        assertEquals(List.of("SERVICES"), on(tree(words("Google services", 1), words("Back up device data", 2),
                words("Accept", 5))));
        assertEquals(List.of("SERVICES"), on(tree(words("Google services", 1), words("More", 5))));
        assertEquals(List.of("PHONE_PROMPT"), on(tree(words("Add phone number?", 1), words("Skip", 5))));
        assertEquals(List.of(), on(tree(words("Choose an account", 1))), "the chooser never adds an account");
    }

    /** The map's screens, by constant name. */
    private static Map<String, Screen> screens() throws IllegalAccessException {
        Map<String, Screen> screens = new TreeMap<>();
        for (Field constant : Screens.class.getFields()) {
            if (Modifier.isStatic(constant.getModifiers()) && constant.get(null) instanceof Screen screen) {
                screens.put(constant.getName(), screen);
            }
        }
        return screens;
    }

    @Test
    void theMapSignsInWithNoCodeOfTheBot() throws IllegalAccessException {
        for (Map.Entry<String, Screen> screen : screens().entrySet()) {
            for (Field field : screen.getValue().getClass().getFields()) {
                assertFalse(Custom.class.isAssignableFrom(field.getType()),
                        screen.getKey() + "." + field.getName() + " is code of the bot, not the map");
            }
        }
        List<String> methods = Arrays.stream(GoogleSignIn.class.getDeclaredMethods())
                .filter(method -> !method.isSynthetic()).map(Method::getName).toList();
        assertEquals(List.of("main"), methods, "the bot's class only starts the run");
    }

    @Test
    void everyPageServesTheSignInOrIsMarked() throws IllegalAccessException {
        for (Map.Entry<String, Screen> entry : screens().entrySet()) {
            Screen screen = entry.getValue();
            Setup setup = screen.setup();
            boolean marked = setup.needsYou() != null || setup.refuses() != null;
            boolean serves = false;
            for (Field field : screen.getClass().getFields()) {
                if (field.get(screen) instanceof Do action && action.serves().contains(Goals.SIGNED_IN)) serves = true;
            }
            assertTrue(marked != serves, entry.getKey() + " serves the sign-in or is marked, one of the two");
        }
    }
}
