package ch.epfl.sweng.project.Fragment;



import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import ch.epfl.sweng.project.Model.User;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Dusan Viktor on 2016-12-06.
 */
@RunWith(AndroidJUnit4.class)
public class DetailsFragmentTest  {

    private DetailsFragment myFragment;
    private TextView name;
    private TextView desc;
    private TextView musicId;
    private User user;
    private User userDetails;
    private MapFrag mapFrag;
    private Activity nextActivity;

    @Before
    public void setUp() throws Exception {

        myFragment = new DetailsFragment();
        user = new User();
        user.setDescrition("Love, sport, Rock ´n´ Roll");
        user.setFirstname("Nicolas");
        user.setCurrentMusicId(20161206);
        user.setProfilePicture("111");
        myFragment.setUser(user);
        userDetails = myFragment.detailsGetUser();
    }

    @Test
    public void checkValueFragment() {
        assertNotNull(myFragment);
        assertNotNull(user);
        assertEquals(user.getFirstname(),userDetails.getFirstname() );
        assertEquals(user.getCurrentMusicId(), userDetails.getCurrentMusicId());
        assertEquals(user.getDescription(), userDetails.getDescription());
        assertEquals(user.getProfilePicture(), userDetails.getProfilePicture());
    }

    @Test
    public void ClickOnButtonFacebook(){
        // TODO
    }

}