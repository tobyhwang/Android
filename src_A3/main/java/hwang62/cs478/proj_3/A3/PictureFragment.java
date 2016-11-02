package hwang62.cs478.proj_3.A3;

import android.app.Fragment;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by tobyh on 10/26/2016.
 */
public class PictureFragment extends Fragment {

    //init variables
    private ImageView mImageView = null;
    private int mCurrIndex = -1;
    private int mPicLength = 0;

    //get the current index of the selected image
    int getShownIndex() {
        return mCurrIndex;
    }

    //show the picture
    public void ShowPicture(int index){
        if(index < 0 || index >= mPicLength){
            return;
        }
        mCurrIndex = index;
        mImageView.setImageResource(MainActivity.Pics[mCurrIndex]);
    }

    //attach the fragment to the activity
    public void onAttach(Context context){
        super.onAttach(context);
    }

    //save the retained instance of the picture
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    //set the instance of the fragment in onCreate
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.image_fragement, container, false);

    }

    //set the picture
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        mPicLength = MainActivity.Pics.length;
        mImageView = (ImageView) getActivity().findViewById(R.id.PictureFragment);
        ShowPicture(mCurrIndex);
    }

}
