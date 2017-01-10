package cs478.project5.toby.playerclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

import cs478.project5.toby.audioserver.MusicPlayer;

public class PlayerClient extends AppCompatActivity {

    //Init all the view components
    ImageButton play;
    ImageButton pause;
    ImageButton next;
    ImageButton prev;
    Button stop;
    Button loadSongs;
    ListView songList;
    ListView actionList;

    //connection and aidl
    MusicPlayer mMusicPlayerService;
    ServiceConnection mConnection;

    //store the song names
    String[] arrSongs;

    //Action List
    ArrayList<String> arrActions = new ArrayList<>();
    ArrayAdapter actionAdapter;

    //music controls
    final int PLAY = 0;
    final int PAUSE = 1;
    final int NEXT = 2;
    final int PREV = 3;
    final int STOP = 4;
    final int END = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_client);

        //Create service connection
        createServiceConn();

        //tie variable to each view component
        play = (ImageButton) findViewById(R.id.btnPlay);
        pause = (ImageButton) findViewById(R.id.btnPause);
        next = (ImageButton) findViewById(R.id.btnNext);
        prev = (ImageButton) findViewById(R.id.btnPrev);
        stop = (Button) findViewById(R.id.btnStop);
        loadSongs = (Button) findViewById(R.id.btnLoadSongs);
        songList = (ListView) findViewById(R.id.lsvSongList);
        actionList = (ListView) findViewById(R.id.lsvAction);

        //on click for play
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppendText("Playing Song");
                SendControl(PLAY);
            }
        });

        //on click for pausing the song
        pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppendText("Paused Song");
                SendControl(PAUSE);
            }
        });

        //on click for the next song
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppendText("Next Song");
                SendControl(NEXT);
            }
        });

        //on click for the previous song
        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppendText("Previous Song");
                SendControl(PREV);
            }
        });

        //on click for the stopped song
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppendText("Stopped Song");
                SendControl(STOP);
            }
        });

        //on click for loading the list of songs
        loadSongs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LoadSongs();
                AppendText("Loaded Songs");
            }
        });

        //on click for the list of songs
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                try{
                    mMusicPlayerService.SetSongName(position);
                }catch (RemoteException e){e.printStackTrace(); }

                AppendText("Playing " + arrSongs[position]);
            }
        });

        //Set up for action list
        actionAdapter = new ArrayAdapter(this, R.layout.text_list, arrActions);
        actionList.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        actionList.setStackFromBottom(true);

    }

    //contract with server, send command
    public void SendControl(int command){
        try {
            mMusicPlayerService.MediaControls(command);
        }catch (RemoteException e) {e.printStackTrace();}
    }

    //load the list of songs from the server
    public void LoadSongs(){

        //Create the list of songs
        try {
            arrSongs = mMusicPlayerService.GetSongNames();
        }
        catch(RemoteException e){e.printStackTrace();}
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.text_list, arrSongs);
        songList.setAdapter(adapter);

    }

    //Append to the list of controls
    public void AppendText(String action){
        arrActions.add(action);
        actionList.setAdapter(actionAdapter);
    }

    //Create a service connection
    public void createServiceConn() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mMusicPlayerService = MusicPlayer.Stub.asInterface(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mMusicPlayerService = null;
            }
        };


        //Bind the service to the client
        if(mMusicPlayerService == null){
            Intent intent = new Intent("service.cs478.hwang62.music_player");
            intent.setPackage("cs478.project5.toby.audioserver");
            bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
        }
    }

    //on destroy make sure to unbind and disable the service
    protected void onDestroy(){
        super.onDestroy();
        SendControl(END);
        unbindService(mConnection);
    }


}
