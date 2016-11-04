package ch.epfl.sweng.project.media;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import Util.GlobalSetting;
import ch.epfl.sweng.project.Model.ModelApplication;
import ch.epfl.sweng.project.Model.Music;
import ch.epfl.sweng.project.R;
import ch.epfl.sweng.project.ServerRequest.OnServerRequestComplete;
import ch.epfl.sweng.project.ServerRequest.ServiceHandler;

// Some code has been taken from this website :
// http://www.codeproject.com/Articles/992398/Getting-Current-Playing-Song-with-BroadcastReceive

public class MusicInfoService extends Service {
    public static final String ARTIST_NAME = "artistName";
    public static final String MUSIC_NAME = "musicName";
    private final ModelApplication modelApplication = ModelApplication.getModelApplication();
    private String artist = "";
    private String track = "";
    private Music music;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String newTrack = intent.getStringExtra("track");
            String newArtist = intent.getStringExtra("artist");
            //Log.d("MusicInfoService", "mReceiver.onReceive : " + newArtist + " - " + newTrack);

            boolean playing = intent.getBooleanExtra("playing", false);


            if (playing) {
                if (artist.equals(newArtist) && track.equals(newTrack)) {
                    // It happens when you pause and play the same song. We don't want to send several times the
                    // same song info.
                    // Do nothing
                    Log.d("MusicInfoService", "mReceiver.onReceive : new song is the same as the last one");
                } else if (newArtist != null && newTrack != null) {
                    // Send the newly played song instead of just displaying a toast
                    Log.d("MusicInfoService", "mReceiver.onReceive new song playing: " + newArtist + " - " + newTrack);
                    sendPost(newArtist, newTrack, GlobalSetting.MUSIC_API);
                }
                // Keep track of new song
                artist = newArtist;
                track = newTrack;

            } else {
                Log.d("MusicInfoService", "Music is paused");
            }

        }
    };



    @Override
    public void onCreate() {
        Log.i("MusicInfoService", "Service started");

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.musicservicecommand");
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.updateprogress");

        registerReceiver(mReceiver, iF);

        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (!manager.isMusicActive()) {
            Log.d("MusicInfoService", "Music is paused or stopped");
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendPost(String artistName, String trackName, @SuppressWarnings("SameParameterValue") String
            requestApi) {
        ServiceHandler serviceHandler = new ServiceHandler(new OnServerRequestComplete() {

            @Override
            public void onSucess(ResponseEntity response) {

                // We associated the user to the new.
                if (Integer.parseInt(response.getStatusCode().toString()) == GlobalSetting.GOOD_ANSWER) {
                    modelApplication.setMusic((Music) response.getBody());
                    Log.d("MusicInfoService", "new track in modelApplication : " + modelApplication.getMusic()
                            .getArtist() + " - " + modelApplication.getMusic().getName());
                } else {
                    // Erreur pas pu communiquer avec le serveur
                    Toast.makeText(getApplicationContext(), getString(R.string.server_error_music_info), Toast
                            .LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed() {
// Erreur 404
                Toast.makeText(getApplicationContext(), getString(R.string.server_error_music_info), Toast
                        .LENGTH_SHORT).show();
            }
        });

        // Building the parameters for the
        Map<String, String> params = new HashMap<>();
        params.put(ARTIST_NAME, artistName);
        params.put(MUSIC_NAME, trackName);

        // the interface is already initiate above
        serviceHandler.doPost(params, GlobalSetting.URL + requestApi + modelApplication.getUser().getIdApiConnection(), Music.class);
    }

}
