package hwang62.cs478.proj_3.A3;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.jar.Attributes;

import hwang62.cs478.proj_3.A3.NameFragment.ListSelectionListener;

public class MainActivity extends AppCompatActivity implements ListSelectionListener {

    //Init information for the hotels and the restaurants
    public static String[] Names;
    public static Integer[] Pics;

    //init for fragments
    private NameFragment NameFrag = new NameFragment();
    private PictureFragment PicFrag = new PictureFragment();
    private FragmentManager FragManager;
    private FrameLayout NameLayout;
    private FrameLayout PictureLayout;


    @Override
    //onCreate function for the activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Store all the information about the hotels and restaurant
        if(getIntent().getIntExtra("userChoice", 0)==0){
            getSupportActionBar().setTitle("Hotels");
            Names = getResources().getStringArray(R.array.HotelNames);
            Pics = new Integer[]{
                    R.drawable.ritz_carlton, R.drawable.the_drake, R.drawable.omni_hotel,
                    R.drawable.palmer_house, R.drawable.four_seasons, R.drawable.the_wit};
        }
        else {
            getSupportActionBar().setTitle("Restaurants");
            Names = getApplication().getResources().getStringArray(R.array.RestNames);
            Pics = new Integer[]{
                    R.drawable.alinea, R.drawable.juno_sushi, R.drawable.rpm,
                    R.drawable.devon_seafood, R.drawable.the_boarding_house, R.drawable.au_cheval};
        }

        //reference the layouts
        NameLayout = (FrameLayout) findViewById(R.id.name_frag_layout);
        PictureLayout = (FrameLayout) findViewById(R.id.picture_frag_layout);

        //Retrieve the Fragment manager
        FragManager = getFragmentManager();

        //get the saved fragment
        NameFrag = (NameFragment) FragManager.findFragmentByTag("name");

        if(NameFrag == null) {
            //start the fragment transaction
            FragmentTransaction FragTransaction = FragManager.beginTransaction();

            //Add the name fragment to the main activity
            FragTransaction.replace(R.id.name_frag_layout, new NameFragment(), "name");

            //Commit
            FragTransaction.commit();
        }

        //when the backstack changes, reset the layout
        FragManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                setLayout();
            }
        });
    }

        private void setLayout(){

            //if the phone is in portrait mode
            if(getResources().getConfiguration().orientation == 1){
                if (!PicFrag.isAdded()) {
                    //Name list should take up the whole screen
                    NameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    PictureLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
                } else {
                    NameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
                    PictureLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }
            }

            //if the phone is in landscape mode
            else {
                if (!PicFrag.isAdded()) {
                    //Name list should take up the whole screen
                    NameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    PictureLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));

                } else {
                    NameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                    PictureLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2f));
                }
            }

        }

        //the picture will be shown based on the user's selection
        public void onListSelection(int indexSelected){

            //if a picture fragment hasn't been created
            if(!PicFrag.isAdded()){

                //start the fragment transaction
                FragmentTransaction fragmentTransaction = FragManager.beginTransaction();

                //replace the current fragment
                fragmentTransaction.replace(R.id.picture_frag_layout, PicFrag, "picture");

                //add to the back stack
                fragmentTransaction.addToBackStack(null);

                //commit it
                fragmentTransaction.commit();
                //execute any pending transactions
                FragManager.executePendingTransactions();
            }

           //show the picture for the selected index
           if(PicFrag.getShownIndex() != indexSelected)
                PicFrag.ShowPicture(indexSelected);
        }

        //depending on the current active screen
        //the menu will either show a restaurant or hotels
        public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater inflater = getMenuInflater();

            //check what the current intent is
            if(getIntent().getIntExtra("userChoice", 0) == 0)
                inflater.inflate(R.menu.restaurant_menu, menu);

            else{
                inflater.inflate(R.menu.hotel_menu, menu);
            }
            return true;
        }

        //make decision based on the item the user picks from the menu
        public boolean onOptionsItemSelected(MenuItem menu){
            Intent newIntent = getIntent();

            switch(menu.getItemId()){

                case R.id.hotelOption:
                    newIntent.putExtra("userChoice", 0);
                    RestartActivity(newIntent);

                case R.id.restaurantOption:
                    newIntent.putExtra("userChoice", 1);
                    RestartActivity(newIntent);

                default:
                    return super.onOptionsItemSelected(menu);

            }

        }

        //Function to restart the activity after the
        //user selects from the menu
        public void RestartActivity(Intent recIntent)
        {
            finish();
            startActivity(recIntent);
        }
}
