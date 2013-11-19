package pivotal.architecture.loaders;

import pivotal.architecture.callbacks.PivotalCursorLoaderCallbacks;
import pivotal.architecture.callbacks.PivotalLoaderCallbacks;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

public abstract class PivotalCursorLoader extends CursorLoader implements PivotalLoaderCallbacks {

	private final ContentObserver mContentObserver = new ContentObserver(null) {

		@Override
		public void onChange(boolean selfChange) {
			reset();
		}

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			reset();
		}
	};
	
	public abstract Uri getUri();
	public abstract int getLoaderId();
	
	private final PivotalCursorLoaderCallbacks mPivotalCursorLoaderCallbacks;
	private LoaderManager mLoaderManager;

	public PivotalCursorLoader(Context context, final LoaderManager loaderManager, PivotalCursorLoaderCallbacks pivotalCursorLoaderCallbacks) {
		super(context);
		mLoaderManager = loaderManager;
		mPivotalCursorLoaderCallbacks = pivotalCursorLoaderCallbacks;
	}

	@Override
	public Cursor loadInBackground() {
		final Context context = getContext();
		final ContentResolver contentResolver = context.getContentResolver();
		final Cursor cursor = contentResolver.query(getUri(), null, null, null, null);
		return cursor;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return this;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		cursor.moveToFirst();
		mPivotalCursorLoaderCallbacks.onLoadFinished(loader, cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mPivotalCursorLoaderCallbacks.onLoaderReset(loader);
	}

	@Override
	public void onStart(Context context) {
		context.getContentResolver().registerContentObserver(getUri(), false, mContentObserver);
		mLoaderManager.initLoader(getLoaderId(), null, this);
	}

	@Override
	public void onStop(Context context) {
		context.getContentResolver().unregisterContentObserver(mContentObserver);
	}

}