package pivotal.architecture.callbacks;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.database.Cursor;

public interface PivotalLoaderCallbacks extends LoaderCallbacks<Cursor> {

	public void onStart(final Context context);

	public void onStop(final Context context);

}
