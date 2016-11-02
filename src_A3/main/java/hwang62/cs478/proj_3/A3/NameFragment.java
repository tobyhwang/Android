package hwang62.cs478.proj_3.A3;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by tobyh on 10/26/2016.
 */
public class NameFragment extends ListFragment {

    //create an instance of ListSelectionListener
    private ListSelectionListener listener = null;

    //create an interface to communicate with the activity and the other fragment
    public interface ListSelectionListener{
        void onListSelection(int indexSelected);
    }

    //when the item is selected, it lets the activity know which item was selected
    public void onListItemClick(ListView l, View v, int pos, long id)
    {
        //gets the item that is clicked
        getListView().setItemChecked(pos, true);

        //let the main activity know which item has been selected
        listener.onListSelection(pos);
    }

    //onAttach for the fragment lifecycle
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            listener= (ListSelectionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    //save the instance of the list on rotation
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    //onCreateView for the fragment lifecycle
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //onActvityCreated for the fragment lifecycle
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //set the adapter for the listview
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.name_fragment, MainActivity.Names));

        //user can only pick one option at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);


    }

}
