package pivotal.architecture.tasks;

import pivotal.architecture.services.PivotalService;
import pivotal.workshop.database.PivotalTasksTable;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public abstract class PivotalTask implements Runnable {

	private final Context mContext;
	private final Uri mUri;

	public PivotalTask(final Context context, final Uri uri) {
		mContext = context;
		mUri = uri;
	}

	public Context getContext() {
		return mContext;
	}

	public Uri getUri() {
		return mUri;
	}

	@Override
	public void run() {
		try {
			notifyRunning();
			executeTask();
			onSuccess();
		} catch (final Exception exception) {
			onFailure();
		} 
	}

	private void notifyRunning() {
		final ContentResolver contentResolver = getContext().getContentResolver();
		final String whereClause = PivotalTasksTable.Columns.URI + "=? AND " + PivotalTasksTable.Columns.STATE + "<>?";
		final String[] whereArguments = new String[] { mUri.toString(), PivotalTasksTable.State.RUNNING };
		final ContentValues contentValues = new ContentValues();
		contentValues.put(PivotalTasksTable.Columns.STATE, PivotalTasksTable.State.RUNNING);
		contentValues.put(PivotalTasksTable.Columns.URI, mUri.toString());
		contentValues.put(PivotalTasksTable.Columns.TIME, System.nanoTime());
		// THE FOLLOWING NEEDS TO BE ATOMIC
		final int rows = contentResolver.update(PivotalTasksTable.URI, contentValues, whereClause, whereArguments);
		if (rows == 0) {
			final String queryWhereClause = PivotalTasksTable.Columns.URI + "=? AND " + PivotalTasksTable.Columns.STATE + "=?";
			final String[] queryWhereArguments = new String[] { mUri.toString(), PivotalTasksTable.State.RUNNING };

			final Cursor cursor = contentResolver.query(PivotalTasksTable.URI, null, queryWhereClause, queryWhereArguments, null);
			try {
				if (cursor.getCount() != 0)
					return;
			} finally {
				cursor.close();
			}
			contentResolver.insert(PivotalTasksTable.URI, contentValues);

		}
		contentResolver.notifyChange(PivotalTasksTable.URI, null);
	}

	private void onFailure() {
		notifyState(PivotalTasksTable.State.FAIL);
	}

	private void onSuccess() {
		notifyState(PivotalTasksTable.State.SUCCESS);
	}

	private void notifyState(final String state) {
		final ContentResolver contentResolver = getContext().getContentResolver();
		final String whereClause = PivotalTasksTable.Columns.URI + "=?";
		final String[] whereArguments = new String[] { mUri.toString() };
		final ContentValues contentValues = new ContentValues();
		contentValues.put(PivotalTasksTable.Columns.STATE, state);
		contentValues.put(PivotalTasksTable.Columns.URI, mUri.toString());
		contentValues.put(PivotalTasksTable.Columns.TIME, System.nanoTime());
		contentResolver.update(PivotalTasksTable.URI, contentValues, whereClause, whereArguments);
		contentResolver.notifyChange(PivotalTasksTable.URI, null);
	}

	public abstract void executeTask() throws Exception;

}
