package de.htw.mbsnw_projekt.end_to_end_tests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static de.htw.mbsnw_projekt.end_to_end_tests.EspressoCustomAddons.waitFor;

import android.Manifest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.ui.SpielActivity;
import de.htw.mbsnw_projekt.ui.navigation_drawer.MainMenuActivity;

@RunWith(AndroidJUnit4.class)
public class UIRecyclerViewTest {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION
    );

    @Rule public ActivityScenarioRule<MainMenuActivity> activityScenarioRule = new ActivityScenarioRule<>(MainMenuActivity.class);


    @Test
    public void laufendesSpielAnklicken() {
        //Spiel starten
        onView(withId(R.id.startGameButton)).perform(click());
        onView(withId(R.id.create_spiel)).perform(click());
        onView(isRoot()).perform(waitFor(500));
        //Zurück zum Home Menu
        Espresso.pressBack();
        onView(withId(R.id.resumeGameButton)).check(matches(isDisplayed()));
        //Zu Spiele Fragment navigieren
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_spiele));
        onView(isRoot()).perform(waitFor(500));
        //Item in RecyclerView anklicken
        init();
        int position = 0;
        onView(withId(R.id.recycler_view_spiele)).perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
        //SpielActivity geöffnet
        intended(hasComponent(SpielActivity.class.getName()));
        release();
    }


}
