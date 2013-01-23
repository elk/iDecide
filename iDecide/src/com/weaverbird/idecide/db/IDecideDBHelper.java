package com.weaverbird.idecide.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class IDecideDBHelper extends SQLiteOpenHelper {
	
	private static final String TAG = IDecideDBHelper.class.getName();

	private static final String CREATE_SCENARIO_TABLE_SQL = "create table if not exists scenarios (_id integer primary key autoincrement, scenario_name varchar(255));";

	private static final String CREATE_SCENARIO_ITEMS_TABLE_SQL = "create table if not exists scenario_items (_id integer primary key autoincrement, item_name varchar(255), scenario_id integer, FOREIGN KEY(scenario_id) REFERENCES scenarios(_id));";

	public IDecideDBHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_SCENARIO_TABLE_SQL);
		db.execSQL(CREATE_SCENARIO_ITEMS_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public Cursor getAllScenarios() {
		Log.i(TAG, "Begin to get all the scenario names.");
		//return this.getReadableDatabase().query("scenarios", new String[]{"scenario_name"}, null, null, null, null, null);
		return this.getReadableDatabase().rawQuery("select _id, scenario_name from scenarios", null);
	}
	
	public Cursor getScenarioItems(int scenarioId) {
		Log.i(TAG, "Begin to get the senario items for scenario " + scenarioId);
		return this.getReadableDatabase().rawQuery("select _id, item_name from scenario_items where scenario_id = ?", new String[] {String.valueOf(scenarioId)});
	}
	
	public long insertScenario(String scenarioName) {
		Log.i(TAG, "Begin to insert the scenario: " + scenarioName);
		ContentValues values= new ContentValues();
		values.put("scenario_name", scenarioName);
		return this.getReadableDatabase().insert("scenarios", null, values);
	}
	
	public void insertScenarioItems(int scenarioId, List<String> itemNames) {
		Log.i(TAG, "Begin to insert the scenario items for scenario: " + scenarioId);
		ContentValues values = new ContentValues();
		values.put("scenario_id", scenarioId);
		SQLiteDatabase db = this.getReadableDatabase();
		for (String itemName : itemNames) {
			values.put("item_name", itemName);
			db.insert("scenario_items", null, values);
			
		}
	}
	
}
