package com.weaverbird.idecide;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.weaverbird.idecide.db.IDecideDBHelper;

public class ScenarioListActivity extends ListActivity {
	
	private static final String TAG = ScenarioListActivity.class.getName();
	
	public static final int REQUEST_CODE = 1;

	private IDecideDBHelper dbHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		dbHelper = new IDecideDBHelper(this, "idecide.db3", 1);
		Log.i(TAG, "Successfully created the IDecideDBHelper.");


		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, dbHelper.getAllScenarios(), new String[] {"scenario_name"},
				new int[] {android.R.id.text1}, 0);
		
		Log.i(TAG, "Successfully created the SimpleCursorAdapter.");

		this.setListAdapter(adapter);

	}
	
	@Override
    protected void onListItemClick(ListView lv, View v, int pos, long id) {
        
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.menu_adding_scenario:
			Intent intent = new Intent(this, ScenarioAddActivity.class);
			this.startActivityForResult(intent, REQUEST_CODE);
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == 0) {
			Toast.makeText(this, "scenario added", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

		if (dbHelper != null) {
			dbHelper.close();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_scenario_list, menu);
		return true;
	}

}
