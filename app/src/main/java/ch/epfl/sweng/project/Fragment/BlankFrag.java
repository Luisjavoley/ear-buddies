package ch.epfl.sweng.project.Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.project.Model.ModelApplication;
import ch.epfl.sweng.project.Model.User;
import ch.epfl.sweng.project.R;

import static ch.epfl.sweng.project.Model.ModelApplication.getModelApplication;

public class BlankFrag extends Fragment{

    private User[] usersAround;
    private User user;
    private String[] userNames;
    private ArrayList<User> listUsers;
    private String[] userDescription;
    private int[] images = {R.drawable.alfa_romeo,
                            R.drawable.bmw,
            R.drawable.porsche,
            R.drawable.peugeot,
            R.drawable.volkswagen,
            R.drawable.volvo,
            R.drawable.opel,
            R.drawable.nissan,
            R.drawable.hyundai,
            R.drawable.mercedes,

};

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_blank, container, false);

        Resources res = getResources();


        usersAround = ModelApplication.getModelApplication().getOtherUsers();
         if (usersAround == null){
            Log.i("No users", "" + 0);
            usersAround = new User[10];
        }

        int indx;
        for(indx = 0; indx < usersAround.length; indx++){
            usersAround[indx] = new User();
        }

        int userNumber;
        for(indx = 0; indx < usersAround.length; indx++) {
            userNumber = indx + 1;
            usersAround[indx].setFirstname("User number" + userNumber);
            usersAround[indx].setDescrition("Description of the user number: " + userNumber);
        }
        userNames = new String[usersAround.length];
        userDescription = new String[usersAround.length];

        int userIndex;
        for(userIndex = 0; userIndex < usersAround.length; userIndex++){
            userNames[userIndex] = usersAround[userIndex].getFirstname();
            userDescription[userIndex] = usersAround[userIndex].getDescrition();
        }



        ListView list = (ListView) view.findViewById(R.id.listView);

        VivzAdapter adapter = new VivzAdapter(getContext(), userNames, images, userDescription);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int profileNumber = position + 1;
               Toast.makeText(getActivity(),"You clicked on profile number: "+ profileNumber,Toast.LENGTH_SHORT).show();
                }
            }
        );

        return view;
    }


//ListView calls the Adapter

    class VivzAdapter extends  ArrayAdapter<String>{

        Context context;
        int[] images;
        String[] titleArray;
        String[] descriptionArray;
        VivzAdapter(Context c,String[] titles, int[] imgs, String[] desc){

            super(c, R.layout.single_row_blankfrag,R.id.textView2,titles);
            this.context = c;
            this.images = imgs;
            this.titleArray = titles;
            this.descriptionArray = desc;
        }

        //This metod is called each time a row has to be displayed to a user
        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            //Create a java object from xml
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Contains the reference to Relative Layout in single_row_blankfrag
            View row = inflater.inflate(R.layout.single_row_blankfrag, parent, false);

            ImageView myImage = (ImageView) row.findViewById(R.id.imageView);
            TextView myTitle = (TextView) row.findViewById(R.id.textView2);
            TextView myDescription = (TextView) row.findViewById(R.id.textView3);

            myImage.setImageResource(images[position]);
            myTitle.setText(titleArray[position]);
            myDescription.setText(descriptionArray[position]);

            return row;

        }


    }


}

