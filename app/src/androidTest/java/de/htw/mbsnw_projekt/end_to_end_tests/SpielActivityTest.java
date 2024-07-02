package de.htw.mbsnw_projekt.end_to_end_tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static de.htw.mbsnw_projekt.end_to_end_tests.EspressoCustomAddons.waitFor;

import android.Manifest;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.ui.navigation_drawer.HomeFragment;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SpielActivityTest {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION
    );

    @Test
    public void aufgebenTest() {
        try (FragmentScenario<HomeFragment> fragmentScenario = FragmentScenario.launchInContainer(HomeFragment.class)) {
            //Start Button anklicken
            onView(withId(R.id.startGameButton)).perform(click());
            //Create Spiel Button anklicken
            onView(withId(R.id.create_spiel)).perform(click());
            //Zurück zum Home Menu
            // wait during 15 seconds for a view
            onView(isRoot()).perform(waitFor(500));
            Espresso.pressBack();
            //Resume Button statt Start Button
            onView(withId(R.id.resumeGameButton)).check(matches(isDisplayed()));
            //Resume Button anklicken
            onView(withId(R.id.resumeGameButton)).perform(click());
            //Return Button ist nicht da
            onView(withId(R.id.return_button)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));
            //Aufgeben anklicken
            onView(withId(R.id.aufgeben_button)).perform(click());
            //Return Button erscheint
            onView(withId(R.id.return_button)).check(matches(isDisplayed()));
            //Return Button anklicken
            onView(withId(R.id.return_button)).perform(click());
            //Start Button statt Resume Button
            onView(withId(R.id.startGameButton)).check(matches(isDisplayed()));
        }
    }



}
