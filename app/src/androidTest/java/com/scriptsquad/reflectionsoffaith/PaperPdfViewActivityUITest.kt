@file:Suppress("DEPRECATION")

package com.scriptsquad.reflectionsoffaith

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import android.os.IBinder
import android.view.WindowManager
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Matcher
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.scriptsquad.reflectionsoffaith.Bibles.activities.PaperPdfViewActivity
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class PaperPdfViewActivityTest {

    @Test
    fun testLoadPdfAndBackButton() {
        // Launch the activity with an intent containing a bookId
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            PaperPdfViewActivity::class.java
        ).putExtra("bookId", "someValidBookId")

        ActivityScenario.launch<PaperPdfViewActivity>(intent)

        // Check that the progress bar is displayed while the PDF is loading
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

        // Simulate the back button press and check that the back button is functional
        onView(withId(R.id.backBtn)).perform(click())
    }

    @Test
    fun testPdfLoadSuccess() {
        // Launch the activity with a valid bookId
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            PaperPdfViewActivity::class.java
        ).putExtra("bookId", "validBookId")

        ActivityScenario.launch<PaperPdfViewActivity>(intent)

        // Verify the PDFView is displayed after loading
        onView(withId(R.id.pdfView)).check(matches(isDisplayed()))

        // Check if the toolbar title is displayed correctly after loading the PDF
        onView(withId(R.id.toolbarTitleTv)).check(matches(withText("Your PDF Title")))

        // Check the progress bar is hidden after the PDF is loaded
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun testPdfLoadFailure() {
        // Launch the activity with an invalid bookId
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            PaperPdfViewActivity::class.java
        ).putExtra("bookId", "invalidBookId")

        ActivityScenario.launch<PaperPdfViewActivity>(intent)

        // Simulate a failure (in this case, the PDF URL doesn't exist in Firebase)
        // Check that an error message or Toast is shown
        onView(withText("Failed to get url due to")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    // ToastMatcher implementation to match Toast messages in UI tests
    fun ToastMatcher(): Matcher<Root> {
        return object : TypeSafeMatcher<Root>() {
            override fun describeTo(description: Description) {
                description.appendText("is toast")
            }

            override fun matchesSafely(root: Root?): Boolean {
                val type = root?.windowLayoutParams?.get()?.type
                if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                    val windowToken: IBinder = root.decorView.windowToken
                    val appToken: IBinder = root.decorView.applicationWindowToken
                    return windowToken === appToken
                }
                return false
            }
        }
    }
}
