package be.ambroosvaes.xperiascrobbler.Z1Compat;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!appInstalledOrNot("fm.last.android")) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=fm.last.android"));
			Toast toast = Toast.makeText(getApplicationContext(),
					R.string.install_last_fm_toast, Toast.LENGTH_LONG);
			toast.show();
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_main);

	}

	public void hideApp(View v) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(R.string.confirm_hide);
		builder.setTitle(R.string.confirm_hide_title);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   PackageManager p = getPackageManager();
		       			p.setComponentEnabledSetting(getComponentName(),
		       				PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0);
		           }
		       });
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User cancelled the dialog
		           }
		       });
		// Create the AlertDialog
		builder.show();
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
