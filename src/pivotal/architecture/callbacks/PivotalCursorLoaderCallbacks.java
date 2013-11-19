package pivotal.architecture.callbacks;

import android.content.Loader;
import android.database.Cursor;

public interface PivotalCursorLoaderCallbacks {
	public void onLoadFinished(final Loader<Cursor> loader, final Cursor cursor);
	public void onLoaderReset(final Loader<Cursor> loader) ;
}
