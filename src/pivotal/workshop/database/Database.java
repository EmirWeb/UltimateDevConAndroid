package pivotal.workshop.database;

import pivotal.architecture.PivotalApplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;

	public Database(Context context, String name) {
		super(context, name, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		Log.d(PivotalApplication.DEBUG_TAG, "onCreate Database");
		db.execSQL(PivotalTable.DROP);
		db.execSQL(PivotalTable.CREATE);
		db.execSQL(PivotalTable.DATA_CREATE);

		db.execSQL(PivotalView.DROP);
		db.execSQL(PivotalView.CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion != newVersion)
			onCreate(db);

	}

}
