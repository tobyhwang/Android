package cs478.project5.toby.audioserver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


public class AudioService extends Service {

    //init media player variables
    MediaPlayer mPlayer;

    //current track
    int track = 0;

    //controls
    final int PLAY = 0;
    final int PAUSE = 1;
    final int NEXT = 2;
    final int PREV = 3;
    final int STOP = 4;
    final int END = 5;

    //List of music files
    private Integer [] musicFiles= new Integer[]{R.raw.cafe_bleu, R.raw.hep_cat_jive, R.raw.steppin_out};



    //Binding to the client app
    private final MusicPlayer.Stub mBinder = new MusicPlayer.Stub(){

        //return the current song names on the server
        public String[] GetSongNames(){
            String[] SongList = new String[]{"Cafe Blue", "Hep Cat Jive", "Steppin' Out"};
            return SongList;
        }

        //Save the song name
        public void SetSongName(int song){

            //save the current song that is playing
            track = song;
            if(mPlayer != null) {
                mPlayer.stop();
            }
            //select the track and start playing the music
            mPlayer = MediaPlayer.create(AudioService.this, musicFiles[song]);
            mPlayer.start();

            //When the song completes it goes to the next track
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mPlayer = mp;
                    MediaControls(NEXT);
                }
            });

        }

        //Receives the controls from the player client
        public void MediaControls(int control){

            //Execute each control
            switch(control){

                case PLAY:
                    if(mPlayer != null)
                        mPlayer.start();
                    else
                        SetSongName(track);
                    break;

                case PAUSE:
                    if(mPlayer != null)
                        mPlayer.pause();
                    break;

                case NEXT:
                    if(track < musicFiles.length -1)
                        track++;
                    else
                        track = 0;
                    SetSongName(track);
                    break;

                case PREV:
                    if(mPlayer != null)
                        mPlayer.seekTo(0);
                    break;

                case STOP:
                    mPlayer.stop();
                    mPlayer.prepareAsync();
                    break;

                //When the client is destroyed this will be called
                case END:
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = null;
                    break;

            }

        }


    };

    //onBind Method
    public IBinder onBind(Intent intent){return mBinder;}
}
