package pivotal.architecture.providers;

import java.util.Locale;

import pivotal.architecture.PivotalApplication;
import pivotal.workshop.database.Database;
import pivotal.workshop.database.PivotalTable;
import pivotal.workshop.database.PivotalView;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class PivotalContentProvider extends ContentProvider {

	private static final String PIVOTAL_DATABASE = "MyDatabase";
	private static final String MIME_TYPE = "pivotal";
	private static SQLiteDatabase sDatabase;
	public static final String AUTHORITY = "pivotal.authority";
	public static final String CONTENT = "content://";
	public static Uri sMyFirstDatasetURI;

	private final UriMatcher mURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	protected static synchronized SQLiteDatabase getDatabase(final Context context) {
		Log.d(PivotalApplication.DEBUG_TAG, "getDatabase");
		if (sDatabase == null) {
			final Database database = new Database(context, PIVOTAL_DATABASE);
			sDatabase = database.getWritableDatabase();
		}
		return sDatabase;
	}

	private SQLiteDatabase getDatabase() {
		return getDatabase(getContext());
	}

	private String getTableName(final Uri uri) {
		Log.d(PivotalApplication.DEBUG_TAG, "getTableName uri: " + uri);
		int match = mURIMatcher.match(uri);
		switch (match) {
		case PivotalTable.CODE:
			return PivotalTable.TABLE_NAME;
		case PivotalView.CODE:
			return PivotalView.VIEW_NAME;
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final int numRowsDeleted = getDatabase().delete(getTableName(uri), selection, selectionArgs);
		return numRowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		final String dataSetName = getTableName(uri);
		final String type = MIME_TYPE + "/" + AUTHORITY + "." + dataSetName;
		return type.toLowerCase(Locale.getDefault());
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final long id = getDatabase().insert(getTableName(uri), null, values);
		return ContentUris.withAppendedId(uri, id);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.d(PivotalApplication.DEBUG_TAG, "query uri: " + uri);
		final Cursor cursor = getDatabase().query(getTableName(uri), projection, selection, selectionArgs, sortOrder, null, null);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		final int numRowsAffected = getDatabase().update(getTableName(uri), values, selection, selectionArgs);
		return numRowsAffected;
	}

	@Override
	public boolean onCreate() {
		Log.d(PivotalApplication.DEBUG_TAG, "onCreate");
		mURIMatcher.addURI(AUTHORITY, PivotalTable.URI_PATH, PivotalTable.CODE);
		mURIMatcher.addURI(AUTHORITY, PivotalView.URI_PATH, PivotalView.CODE);
		return true;
	}

}
