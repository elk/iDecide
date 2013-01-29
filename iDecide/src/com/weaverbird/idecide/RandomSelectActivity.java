package com.weaverbird.idecide;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.weaverbird.idecide.db.IDecideDBHelper;

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

	private IDecideDBHelper dbHelper = null;

	private boolean isStarted = false;

	private Button button = null;
	
	private TextView textView = null;

	private Handler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_select);

		dbHelper = new IDecideDBHelper(this, "idecide.db3", 1);

		button = (Button) findViewById(R.id.start_pickup_button);
		button.setOnClickListener(new RandomSelectButtonClickListener());
		
		textView = (TextView) findViewById(R.id.show_items_textview);
		
			
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// get the bundle and extract data by key
				Bundle bundle = msg.getData();
				String scenarioName = bundle.getString("SCENARIO_NAME");
				textView.setText(scenarioName);
			}
		};

	}
	
	static class runableHandler extends Handler {
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		initScenarioList();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

		if (dbHelper != null) {
			dbHelper.close();
		}
	}

	public void initScenarioList() {
		Spinner spinner = (Spinner) findViewById(R.id.show_scenarios_spinner);

		Cursor cursor = dbHelper.getAllScenarios();
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, cursor,
				new String[] { "scenario_name" },
				new int[] { android.R.id.text1 }, 0);
		spinner.setAdapter(adapter);
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

	private class RandomSelectButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Log.i(TAG, "In onClick, isStarted=" + isStarted);
			if (isStarted) {
				button.setText(getString(R.string.start_pickup));
				isStarted = false;
			} else {
				;
				button.setText(getString(R.string.stop_pickup));
				isStarted = true;
				Thread background = new Thread(new Runnable() {

					@Override
					public void run() {
						int i = 0;
						while (isStarted) {
							try {
								Thread.sleep(100);
								Message msg = new Message();
								Bundle b = new Bundle();
								
								b.putString("SCENARIO_NAME",
										"My Value: " + String.valueOf(i++));
								msg.setData(b);
								// send message to the handler with the current
								// message handler
								handler.sendMessage(msg);
							} catch (Exception e) {
								Log.v("Error", e.toString());
							}
						}
					}
				});
				background.start();
			}

		}

	}

}
