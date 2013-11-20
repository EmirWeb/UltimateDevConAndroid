package pivotal.architecture.activities;

import pivotal.architecture.R;
import pivotal.architecture.adapters.PivotalCursorAdapter;
import pivotal.architecture.callbacks.PivotalLoaderCallbacksListener;
import pivotal.architecture.database.PivotalCreatePeopleTable;
import pivotal.architecture.database.PivotalPeopleView;
import pivotal.architecture.database.PivotalTasksTable;
import pivotal.architecture.loaders.PivotalPeopleTableTaskCursorLoader;
import pivotal.architecture.loaders.PivotalPeopleViewLoaderCallbacks;
import pivotal.architecture.services.PivotalUploadService;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class PivotalActivity extends Activity implements PivotalLoaderCallbacksListener {

	private ListView mListView;
	private PivotalCursorAdapter mPivotalCursorAdapter;
	private PivotalPeopleViewLoaderCallbacks mPeopleCursorLoader;
	private PivotalPeopleTableTaskCursorLoader mPivotalPeopleTaskCursorLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_pivotal);
		super.onCreate(savedInstanceState);
		mListView = (ListView) findViewById(R.id.activity_pivotal_list_view);
		mPeopleCursorLoader = new PivotalPeopleViewLoaderCallbacks(getApplicationContext(), getLoaderManager(), this);
		mPivotalPeopleTaskCursorLoader = new PivotalPeopleTableTaskCursorLoader(getApplicationContext(), getLoaderManager(), this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mPeopleCursorLoader.onStart(getApplicationContext());
		mPivotalPeopleTaskCursorLoader.onStart(getApplicationContext());
	}

	@Override
	protected void onStop() {
		mPeopleCursorLoader.onStop(getApplicationContext());
		mPivotalPeopleTaskCursorLoader.onStop(getApplicationContext());
		super.onStop();
	}

	@Override
	public void onLoadFinished(final Uri uri, final Cursor cursor) {
		if (uri.equals(PivotalPeopleView.URI)) {
			if (mPivotalCursorAdapter == null) {
				mPivotalCursorAdapter = new PivotalCursorAdapter(getApplicationContext(), cursor);
				mListView.setAdapter(mPivotalCursorAdapter);
			} else
				mPivotalCursorAdapter.swapCursor(cursor);
		} else {
			final boolean isCursorEmpty = cursor.getCount() == 0;
			if (isCursorEmpty) {
				setProgressBarIndeterminateVisibility(true);
				return;
			}

			final int stateColumnIndex = cursor.getColumnIndex(PivotalTasksTable.Columns.STATE);
			final String state = cursor.getString(stateColumnIndex);

			if (PivotalTasksTable.State.SUCCESS.equals(state))
				setProgressBarIndeterminateVisibility(false);
			else if (PivotalTasksTable.State.FAIL.equals(state)) {
				setProgressBarIndeterminateVisibility(false);
				Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
			} else if (PivotalTasksTable.State.RUNNING.equals(state))
				setProgressBarIndeterminateVisibility(true);
		}
	}

	@Override
	public void onLoaderReset(final Loader<Cursor> loader) {
		mListView.setAdapter(null);
	}

	public void onCreatePerson(final View view) {
		final ContentValues contentValues = new ContentValues();
		contentValues.put(PivotalCreatePeopleTable.Columns.ADDRESS, "SOME ADDRESS");
		contentValues.put(PivotalCreatePeopleTable.Columns.LAST_NAME, "SOME LAST_NAME");
		contentValues.put(PivotalCreatePeopleTable.Columns.FIRST_NAME, "SOME FIRST_NAME");
		contentValues.put(PivotalCreatePeopleTable.Columns.CITY, "SOME CITY");
		contentValues.put(PivotalCreatePeopleTable.Columns.STATE, PivotalCreatePeopleTable.States.PENDING_UPLOAD);

		final Uri uri = getContentResolver().insert(PivotalCreatePeopleTable.URI, contentValues);
		
		getContentResolver().notifyChange(PivotalPeopleView.URI, null);
		startService(new Intent(getApplicationContext(), PivotalUploadService.class));

	}

}
