package ch.epfl.sweng.project.ModuleRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.v7.app.AppCompatActivity;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Rule;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.project.MainActivity;
import ch.epfl.sweng.project.WelcomeActivity;

import static android.support.test.internal.util.Checks.checkNotNull;
import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * Created by arnauddupeyrat on 08/11/16.
 */

public class TestSessionUserAlreadyConnected extends ActivityInstrumentationTestCase2<WelcomeActivity> {


    /******************************
     * Set Constant
     *********************************************/
    public static final String ID_FACEBOOK = "121620614972695";
    public static final String ACCESS_TOKEN_FACEBOOK =
            "EAAOZCzloFDqEBAHGnY8Q6I4d6fJRy9c6FWYZAqNxp2ChFBvpv8ZAycQC7a0oT21ZBp0Ku" +
                    "IbZCIUkLWSH4Ev7pIQrjlzAxvrfznhXZAeb8A3ZCZBDks8WekNs4WgtfteZCMhUPQx5ZBPmbBMfwBgjqqAeaHOjtYFe38VYfXV35ZCn" +
                    "Q0yZBzPSDzCKDBBMkGhWA8ZAyrJAcBZA6LCi5XtgZDZD";

    private static final String ID = "id";
    private static final String ACESS_TOKEN = "accesToken";
    private static final String TESTING_ALREADY_CONNECTED = "Testing_already_conected";
    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityRule = new ActivityTestRule<>(
            WelcomeActivity.class);
    private Activity curActivity;


    public TestSessionUserAlreadyConnected() {
        super(WelcomeActivity.class);

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(TESTING_ALREADY_CONNECTED, "true");
        bundle.putString(ID, ID_FACEBOOK);
        bundle.putString(ACESS_TOKEN, ACCESS_TOKEN_FACEBOOK);

        intent.putExtras(bundle);
        // Set the intent.
        setActivityIntent(intent);

    }

    /**
     * Testing actions linked to the button login.
     */

    public void test_session_connection() {

        final CountDownLatch latch = new CountDownLatch(1);

        getActivity();

        try {
            latch.await(10, TimeUnit.SECONDS);
            // check the foreground activity.
            assertCurrentActivityIsInstanceOf(MainActivity.class);

        } catch (InterruptedException e) {
            assertTrue("Error in the time waiting", false);

        }
    }


    public void assertCurrentActivityIsInstanceOf(Class<? extends AppCompatActivity> activityClass) {
        Activity currentActivity = getActivityInstance();
        checkNotNull(currentActivity);
        checkNotNull(activityClass);
        assertTrue(currentActivity.getClass().isAssignableFrom(activityClass));
    }

    public Activity getActivityInstance() {

        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage
                        (RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    setCurrentActivity((Activity) resumedActivities.iterator().next());
                }
            }
        });

        return curActivity;
    }

    private void setCurrentActivity(Activity activity) {
        curActivity = activity;
    }
}
