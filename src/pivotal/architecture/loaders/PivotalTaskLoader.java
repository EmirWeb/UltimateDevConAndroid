package pivotal.architecture.loaders;

import pivotal.architecture.PivotalApplication;
import pivotal.workshop.database.PivotalTasksTable;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class PivotalTaskLoader extends CursorLoader {

	private final Uri mUri;

	public PivotalTaskLoader(Context context, final Uri uri) {
		super(context);
		mUri = uri;
	}

	@Override
	protected void onStartLoading() {
		Log.d(PivotalApplication.DEBUG_TAG, "onStartLoading");

		super.onStartLoading();
	}

	@Override
	public Cursor loadInBackground() {
		final Context context = getContext();
		final ContentResolver contentResolver = context.getContentResolver();
		final String whereClause = PivotalTasksTable.Columns.URI + "=?";
		final String[] whereArguments = new String[] { mUri.toString() };
		final Cursor cursor = contentResolver.query(PivotalTasksTable.URI, null, whereClause, whereArguments, null);
		return cursor;
	}

}