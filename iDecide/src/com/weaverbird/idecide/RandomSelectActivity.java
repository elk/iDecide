package com.weaverbird.idecide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * <p>
 * This activity is used as the main activity in this app.
 * </p>
 * <p>
 * This activity with its layout give the UI to pickup the scenario and then
 * random select one item in the scenario after user clicks START and STOP
 * button.
 * </p>
 * 
 * @author elk
 * 
 */
public class RandomSelectActivity extends Activity {
	
	private static final String TAG = RandomSelectActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_select);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_random_select, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Log.i(TAG, "Begin to start ScenarioListActivity.");
			Intent intent = new Intent(this, ScenarioListActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
