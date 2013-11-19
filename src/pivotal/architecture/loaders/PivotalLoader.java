package pivotal.architecture.loaders;

import pivotal.architecture.PivotalApplication;
import pivotal.workshop.database.PivotalTable;
import pivotal.workshop.database.PivotalView;
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
		final Cursor cursor = contentResolver.query(PivotalView.URI, null, null, null, null);
		return cursor;
	}
	
}