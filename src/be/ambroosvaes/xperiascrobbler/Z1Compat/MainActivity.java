package be.ambroosvaes.xperiascrobbler.Z1Compat;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!appInstalledOrNot("fm.last.android")) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=fm.last.android"));
			Toast toast = Toast.makeText(getApplicationContext(), R.string.install_last_fm_toast, Toast.LENGTH_LONG);
			toast.show();
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_main);
		
	}

	private boolean appInstalledOrNot(String uri) {
		PackageManager pm = getPackageManager();
		boolean app_installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
