package com.weaverbird.idecide;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.weaverbird.idecide.db.IDecideDBHelper;

public class ScenarioItemListActivity extends ListActivity {

	private static final String TAG = ScenarioListActivity.class.getName();

	public static final int REQUEST_CODE = 1;

	private IDecideDBHelper dbHelper = null;

	private Cursor cursor = null;
	
	private int scenarioId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		dbHelper = new IDecideDBHelper(this, "idecide.db3", 1);
		Log.i(TAG, "Successfully created the IDecideDBHelper.");
		
		this.scenarioId = this.getIntent().getExtras().getInt("SCENARIO_ID");

		registerForContextMenu(getListView());

		this.initScenarioItemList();
	}

	public void initScenarioItemList() {
		cursor = dbHelper.getScenarioItems(scenarioId);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, cursor,
				new String[] { "item_name" },
				new int[] { android.R.id.text1 }, 0);

		this.setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_scenario_item_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.menu_adding_item:
			Intent intent = new Intent(this, ScenarioItemAddActivity.class);
			intent.putExtra("SCENARIO_ID", scenarioId);
			this.startActivityForResult(intent, REQUEST_CODE);
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			this.initScenarioItemList();
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
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.scenario_item_list_contextual_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.delete_scenario_item:
	        	cursor.moveToPosition(info.position);
	        	dbHelper.deleteScenarioItem(cursor.getInt(cursor.getColumnIndex("_id")));
	        	this.initScenarioItemList();
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

}
