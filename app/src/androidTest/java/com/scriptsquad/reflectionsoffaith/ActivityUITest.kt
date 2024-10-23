package com.scriptsquad.reflectionsoffaith.Account

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.scriptsquad.reflectionsoffaith.R
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard


class Change_Password_ActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Change_Password_Activity::class.java)

    @Test
    fun testEmptyCurrentPasswordShowsError() {
        onView(withId(R.id.changePasswordBtn)).perform(click())
        onView(withId(R.id.currentPasswordEt)).check(matches(hasErrorText("Enter Current Password")))
    }

    @Test
    fun testEmptyNewPasswordShowsError() {
        onView(withId(R.id.currentPasswordEt)).perform(typeText("currentPassword"), closeSoftKeyboard())
        onView(withId(R.id.changePasswordBtn)).perform(click())
        onView(withId(R.id.newPasswordEt)).check(matches(hasErrorText("Enter New Password")))
    }

    @Test
    fun testPasswordMismatchShowsError() {
        onView(withId(R.id.currentPasswordEt)).perform(typeText("currentPassword"), closeSoftKeyboard())
        onView(withId(R.id.newPasswordEt)).perform(typeText("newPassword123"), closeSoftKeyboard())
        onView(withId(R.id.newConfirmPasswordEt)).perform(typeText("differentPassword"), closeSoftKeyboard())
        onView(withId(R.id.changePasswordBtn)).perform(click())
        onView(withId(R.id.newConfirmPasswordEt)).check(matches(hasErrorText("Password Mismatch")))
    }
}
