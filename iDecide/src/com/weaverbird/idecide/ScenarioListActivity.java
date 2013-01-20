package com.weaverbird.idecide;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.weaverbird.idecide.db.IDecideDBHelper;

public class ScenarioListActivity extends ListActivity {

	private IDecideDBHelper dbHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		dbHelper = new IDecideDBHelper(this, "idecide.db3", 1);

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, dbHelper.getAllScenarios(), new String[] {"scenario_name"},
				new int[] {android.R.id.text1}, 0);

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
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

		if (dbHelper != null) {
			dbHelper.close();
		}
	}

}
