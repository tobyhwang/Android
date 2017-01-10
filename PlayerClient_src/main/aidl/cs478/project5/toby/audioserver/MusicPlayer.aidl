// MusicPlayer.aidl
package cs478.project5.toby.audioserver;

// Declare any non-default types here with import statements

interface MusicPlayer {

    String[] GetSongNames();

    void SetSongName(int song);

    void MediaControls(int control);
}

