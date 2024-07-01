package de.htw.mbsnw_projekt.ui_tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.Manifest;
import android.view.View;
import android.widget.SeekBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.ui.CreateSpielActivity;
import de.htw.mbsnw_projekt.ui.SpielActivity;
import de.htw.mbsnw_projekt.ui.navigation_drawer.MainMenuActivity;

@RunWith(AndroidJUnit4.class)
public class UICreateSpielTest {

    private final int progress = 3;
    private final String counterText = "1 / 5";


    @Rule public ActivityScenarioRule<CreateSpielActivity> activityScenarioRule = new ActivityScenarioRule<>(CreateSpielActivity.class);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION
    );

    @Before
    public void setUpIntents() {
        init();
    }

    @After
    public void tearDownIntents() {
        release();
    }

    @Test
    public void spielErstellenSetztText() {
        //Spiel erstellen
        onView(withId(R.id.create_spiel)).perform(click());
        //onView(withId(R.id.ziel_number_seek_bar)).perform(setProgress(progress));
        onView(withId(R.id.ziel_number_seek_bar)).perform(EspressoCustomAddons.setProgress(progress));
        //Prüfen, ob Ziel Anzahl Text richtig gesetzt wird
        onView(withId(R.id.ziel_counter)).check(matches(withText(counterText)));
    }

    @Test
    public void spielErstellenZuSpielActivity() {
        //Spiel erstellen
        onView(withId(R.id.create_spiel)).perform(click());
        //Prüfen, ob SpielActivity aufgerufen wird
        intended(hasComponent(SpielActivity.class.getName()));
    }



}
