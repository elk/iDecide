package com.weaverbird.idecide.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IDecideDBHelper extends SQLiteOpenHelper {

	private static final String CREATE_SCENARIO_TABLE_SQL = "create table if not exists scenarios (_id integer primary key autoincrement, scenario_name varchar(255));";

	private static final String CREATE_SCENARIO_ITEMS_TABLE_SQL = "create table if not exists scenario_items (_id integer primary key autoincrement, item_name varchar(255)，scenario_id integer, FOREIGN KEY(scenario_id) REFERENCES scenarios(id));";

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
		return this.getReadableDatabase().rawQuery("select scenario_name from scenarios", new String[]{});
	}
	
	public Cursor getScenarioItems(int scenarioId) {
		return this.getReadableDatabase().rawQuery("select item_name from scenario_items where scenario_id = ?", new String[] {String.valueOf(scenarioId)});
	}

}
