package pivotal.architecture.activities;

import pivotal.architecture.R;
import pivotal.architecture.adapters.PivotalCursorAdapter;
import pivotal.architecture.callbacks.PivotalCursorLoaderCallbacks;
import pivotal.architecture.loaders.PivotalPeopleCursorLoader;
import pivotal.architecture.loaders.PivotalPeopleTaskCursorLoader;
import pivotal.workshop.database.PivotalTasksTable;
import android.app.Activity;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class PivotalActivity extends Activity implements PivotalCursorLoaderCallbacks {

	private ListView mListView;
	private PivotalPeopleCursorLoader mPeopleCursorLoader;
	private PivotalPeopleTaskCursorLoader mPivotalPeopleTaskCursorLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_pivotal);
		super.onCreate(savedInstanceState);
		mListView = (ListView) findViewById(R.id.activity_pivotal_list_view);
		mPeopleCursorLoader = new PivotalPeopleCursorLoader(getApplicationContext(), getLoaderManager(), this);
		mPivotalPeopleTaskCursorLoader = new PivotalPeopleTaskCursorLoader(getApplicationContext(), getLoaderManager(), this);
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
	public void onLoadFinished(final Loader<Cursor> loader, final Cursor cursor) {
		if (loader instanceof PivotalPeopleCursorLoader) {
			final PivotalCursorAdapter pivotalCursorAdapter = new PivotalCursorAdapter(getApplicationContext(), cursor);
			mListView.setAdapter(pivotalCursorAdapter);
		} else if (loader instanceof PivotalPeopleTaskCursorLoader) {
			final boolean isCursorEmpty = cursor.getCount() == 0;
			if (isCursorEmpty){
				setProgressBarIndeterminate(false);
				return;
			}
			
			final int stateColumnIndex = cursor.getColumnIndex(PivotalTasksTable.Columns.STATE);
			final String state = cursor.getString(stateColumnIndex);

			if (PivotalTasksTable.State.SUCCESS.equals(state))
				setProgressBarIndeterminate(false);
			else if (PivotalTasksTable.State.FAIL.equals(state)) {
				setProgressBarIndeterminate(false);
				Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
			} else if (PivotalTasksTable.State.RUNNING.equals(state))
				setProgressBarIndeterminate(true);
		}
	}

	@Override
	public void onLoaderReset(final Loader<Cursor> loader) {
		mListView.setAdapter(null);
	}

}
