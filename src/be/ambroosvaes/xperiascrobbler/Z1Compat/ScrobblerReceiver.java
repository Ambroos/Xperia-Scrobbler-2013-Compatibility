/* Hello! This code is heavily based on what I could make out from the original Last.FM Scrobbler for Xperia
 * You can find the original app here: https://play.google.com/store/apps/details?id=com.mobilesolutionworks.semcmusic.scrobbler
 * Thanks to that author for figuring it out. I fixed it up a little and removed compatibility with other scrobblers. */

package be.ambroosvaes.xperiascrobbler.Z1Compat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ScrobblerReceiver extends BroadcastReceiver {
	public static final int COMPLETE = 3;
	public static final int PAUSE = 2;
	public static final int START = 1;
	public static final String LAST_FM_META_CHANGED = "fm.last.android.metachanged";
	public static final String LAST_FM_PLAYBACK_COMPLETE = "fm.last.android.playbackcomplete";
	public static final String LAST_FM_PLAYBACK_PAUSED = "fm.last.android.playbackpaused";

	public static void scrobbleToLastFM(Context paramContext,
			Bundle paramBundle, String type) {

		if (paramBundle != null) {
			if (type.equals(LAST_FM_PLAYBACK_PAUSED)) {
				Intent localIntent = new Intent(LAST_FM_PLAYBACK_PAUSED);
				paramContext.sendBroadcast(localIntent);
			} else {
				Intent localIntent = new Intent(type);
				localIntent.putExtra("artist",
						paramBundle.getString("ARTIST_NAME"));
				localIntent.putExtra("album",
						paramBundle.getString("ALBUM_NAME"));
				localIntent.putExtra("track",
						paramBundle.getString("TRACK_NAME"));
				localIntent.putExtra("duration",
						paramBundle.getInt("TRACK_DURATION") / 1000);
				localIntent.putExtra("source", "semc");
				int i = paramBundle.getInt("TRACK_POSITION", -1);
				if (i != -1) {
					localIntent.putExtra("position", i / 1000);
				}
				paramContext.sendBroadcast(localIntent);
			}
		}
	}

	public void onReceive(Context paramContext, Intent paramIntent) {
		String type = "fm.last.android.unknown";

		String state = paramIntent.getAction();
		Log.d("Ambroos", "Received action: " + state);
		if (state
				.equals("com.sonyericsson.music.playbackcontrol.ACTION_TRACK_STARTED"))
			type = LAST_FM_META_CHANGED;
		else if (state
				.equals("com.sonyericsson.music.playbackcontrol.ACTION_PAUSED"))
			type = LAST_FM_PLAYBACK_PAUSED;
		else if (state.equals("com.sonyericsson.music.TRACK_COMPLETED"))
			type = LAST_FM_PLAYBACK_COMPLETE;
		Bundle localBundle = paramIntent.getExtras();
		scrobbleToLastFM(paramContext, localBundle, type);

	}

}
