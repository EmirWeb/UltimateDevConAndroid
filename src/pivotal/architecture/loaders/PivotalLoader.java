package pivotal.architecture.loaders;

import pivotal.architecture.PivotalApplication;
import pivotal.workshop.database.PivotalTable;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.util.Log;

public class PivotalLoader extends CursorLoader {

	public PivotalLoader(Context context) {
		super(context);
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
		Log.d(PivotalApplication.DEBUG_TAG, "PivotalDataSet.URI: " + PivotalTable.URI.toString());
		final Cursor cursor = contentResolver.query(PivotalTable.URI, null, null, null, null);
		Log.d(PivotalApplication.DEBUG_TAG, "cursor: " + cursor);
		return cursor;
	}
	
}