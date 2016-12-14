package ch.epfl.sweng.project.ModuleRequest;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.project.utils.GlobalTestSettings;
import ch.epfl.sweng.project.MainActivity;
import ch.epfl.sweng.project.Model.ModelApplication;

/**
 * Created by Etienne on 09.12.2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestMatch extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;

    public TestMatch() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();  //TODO avoid null
        GlobalTestSettings.createMockUser();
        ModelApplication.getModelApplication().setTest();
    }

    @Test
    public void gotMatch() throws InterruptedException {
        Thread.sleep(5000);
        // assertTrue(mainActivity.gotMatch());
    }
}
