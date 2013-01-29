package com.weaverbird.idecide;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.weaverbird.idecide.db.IDecideDBHelper;

public class ScenarioListActivity extends ListActivity {

	private static final String TAG = ScenarioListActivity.class.getName();

	public static final int REQUEST_CODE = 1;

	private IDecideDBHelper dbHelper = null;
	
	private Cursor cursor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		dbHelper = new IDecideDBHelper(this, "idecide.db3", 1);
		Log.i(TAG, "Successfully created the IDecideDBHelper.");
		
		registerForContextMenu(getListView());

		this.initScenarioList();

	}
	
	public void initScenarioList() {
		cursor = dbHelper.getAllScenarios();
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1,
				cursor, new String[] { "scenario_name" },
				new int[] { android.R.id.text1 }, 0);

		Log.i(TAG, "Successfully created the SimpleCursorAdapter.");

		this.setListAdapter(adapter);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.scenario_list_contextual_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.delete_scenario:
	        	cursor.moveToPosition(info.position);
	        	dbHelper.deleteScenario(cursor.getInt(cursor.getColumnIndex("_id")));
	        	this.initScenarioList();
	            return true;
	        case R.id.view_scenario:
	        	cursor.moveToPosition(info.position);
	        	Intent intent = new Intent(this, ScenarioItemListActivity.class);
	        	intent.putExtra("SCENARIO_ID", cursor.getInt(cursor.getColumnIndex("_id")));
	        	startActivity(intent);
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
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
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			this.initScenarioList();
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

	/*public class ScenarioCursorAdapter extends SimpleCursorAdapter {

		public ScenarioCursorAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to) {
			super(context, layout, c, from, to, 0);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			super.bindView(view, context, cursor);
			TextView scenarioTextView = (TextView) view.findViewById(R.id.scenario_item_name_textview);

			scenarioTextView.setText(cursor.getString(cursor
					.getColumnIndex("scenario_name")));
			

		}

	}*/

}
