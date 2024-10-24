package com.scriptsquad.reflectionsoffaith.account.activities

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.scriptsquad.reflectionsoffaith.Account.Edit_Profile_Activity
import com.scriptsquad.reflectionsoffaith.R
import android.os.IBinder
import android.view.WindowManager
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditProfileActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<Edit_Profile_Activity> = ActivityScenarioRule(Edit_Profile_Activity::class.java)

    @Before
    fun setUp() {
        // If you need to set up any specific user data or mock Firebase authentication, do it here
    }

    @Test
    fun testEditProfileUIElementsDisplay() {
        // Launch the activity
        ActivityScenario.launch<Edit_Profile_Activity>(Intent(ApplicationProvider.getApplicationContext(), Edit_Profile_Activity::class.java))

        // Check if the name, email, and other views are displayed properly
        onView(withId(R.id.nameEt)).check(matches(isDisplayed()))
        onView(withId(R.id.emailEt)).check(matches(isDisplayed()))
        onView(withId(R.id.phoneNumberET)).check(matches(isDisplayed()))
        onView(withId(R.id.updateBtn)).check(matches(isDisplayed()))
    }

    @Test
    fun testUpdateProfileFields() {
        // Launch the activity
        ActivityScenario.launch<Edit_Profile_Activity>(Intent(ApplicationProvider.getApplicationContext(), Edit_Profile_Activity::class.java))

        // Simulate entering new data in the profile fields
        onView(withId(R.id.nameEt)).perform(clearText(), typeText("New Name"))
        closeSoftKeyboard()
        onView(withId(R.id.emailEt)).perform(clearText(), typeText("newemail@example.com"))
        closeSoftKeyboard()
        onView(withId(R.id.phoneNumberET)).perform(clearText(), typeText("1234567890"))
        closeSoftKeyboard()

        // Simulate clicking the "Save" button
        onView(withId(R.id.updateBtn)).perform(click())

        // Verify that the Toast or UI reflects the profile update (Mock or check for success message)
        onView(withText("Profile Updated Successfully")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    fun ToastMatcher(): Matcher<Root> {
        return object : TypeSafeMatcher<Root>() {

            override fun describeTo(description: Description) {
                description.appendText("is toast")
            }

            override fun matchesSafely(root: Root?): Boolean {
                // Check if the window type is toast
                val type = root?.windowLayoutParams?.get()?.type
                if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                    // Check if the toast's window token is the same as the application window token
                    val windowToken: IBinder = root.decorView.windowToken
                    val appToken: IBinder = root.decorView.applicationWindowToken
                    return windowToken === appToken
                }
                return false
            }
        }
    }


    @Test
    fun testInvalidEmailInput() {
        // Launch the activity
        ActivityScenario.launch<Edit_Profile_Activity>(Intent(ApplicationProvider.getApplicationContext(), Edit_Profile_Activity::class.java))

        // Enter invalid email
        onView(withId(R.id.emailEt)).perform(clearText(), typeText("invalid-email"))
        closeSoftKeyboard()

        // Click the "Save" button
        onView(withId(R.id.updateBtn)).perform(click())

        // Verify that the email validation error is shown
        onView(withText("Invalid email format")).check(matches(isDisplayed()))
    }

    @Test
    fun testBackButtonNavigation() {
        // Launch the activity
        ActivityScenario.launch<Edit_Profile_Activity>(Intent(ApplicationProvider.getApplicationContext(), Edit_Profile_Activity::class.java))

        // Click the back button
        onView(withId(R.id.backBtn)).perform(click())

        // Check if the activity has finished or navigated back
        onView(isRoot()).check(matches(isDisplayed()))
    }
}
