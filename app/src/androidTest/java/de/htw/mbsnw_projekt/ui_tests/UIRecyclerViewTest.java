package de.htw.mbsnw_projekt.ui_tests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.contrib.RecyclerViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.htw.mbsnw_projekt.R;
import de.htw.mbsnw_projekt.ui.navigation_drawer.HomeFragment;
import de.htw.mbsnw_projekt.ui.navigation_drawer.MainMenuActivity;

@RunWith(AndroidJUnit4.class)
public class UIRecyclerViewTest {


    //@Rule public FragmentScenario<HomeFragment> fragmentScenario = new FragmentScenario<>(HomeFragment.class);

    @Test
    public void laufendesSpielAnklicken() {
        int position = 1;
        onView(withId(R.id.recycler_view_spiele)).perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

    }


}
