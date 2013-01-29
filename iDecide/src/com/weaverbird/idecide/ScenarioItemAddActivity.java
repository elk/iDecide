package com.weaverbird.idecide;

import com.weaverbird.idecide.db.IDecideDBHelper;
import com.weaverbird.idecide.db.Util;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ScenarioItemAddActivity extends Activity {

	private IDecideDBHelper dbHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scenario_item_add);
		dbHelper = new IDecideDBHelper(this, "idecide.db3", 1);
	}

	public void onOKClicked(View v) {
		TextView textView = (TextView)this.findViewById(R.id.scenario_item_name_added);
		String scenarioItemName = textView.getText().toString();
		if (Util.isEmptyString(scenarioItemName)) {
			
		} else {
			Intent intent = getIntent();
			int scenarioId = intent.getExtras().getInt("SCENARIO_ID");
			dbHelper.insertScenarioItem(scenarioId, scenarioItemName);
			setResult(ScenarioItemListActivity.REQUEST_CODE, intent);
			finish();
		}
	}
	
	public void onCancelClicked(View v) {
		finish();
	}
	
	@Override
	protected void onDestroy() {

		super.onDestroy();

		if (dbHelper != null) {
			dbHelper.close();
		}
	}

}
