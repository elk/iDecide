package com.weaverbird.idecide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.weaverbird.idecide.db.IDecideDBHelper;
import com.weaverbird.idecide.db.Util;

public class ScenarioAddActivity extends Activity {
	
	private IDecideDBHelper dbHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scenario_add);
		dbHelper = new IDecideDBHelper(this, "idecide.db3", 1);
	}

	public void onOKClicked(View v) {
		TextView textView = (TextView)this.findViewById(R.id.scenario_name_added);
		String scenarioName = textView.getText().toString();
		if (Util.isEmptyString(scenarioName)) {
			
		} else {
			dbHelper.insertScenario(scenarioName);
			Intent intent = getIntent();
			Bundle data = new Bundle();
			data.putString("scenarioName", scenarioName);
			intent.putExtras(data);
			setResult(ScenarioListActivity.REQUEST_CODE, intent);
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
