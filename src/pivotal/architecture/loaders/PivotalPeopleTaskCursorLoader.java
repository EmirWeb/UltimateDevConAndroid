package pivotal.architecture.loaders;

import pivotal.architecture.callbacks.PivotalCursorLoaderCallbacks;
import pivotal.architecture.providers.PivotalContentProvider;
import pivotal.workshop.database.PivotalPeopleTable;
import pivotal.workshop.database.PivotalTasksTable;
import android.app.LoaderManager;
import android.content.Context;
import android.net.Uri;

public class PivotalPeopleTaskCursorLoader extends PivotalCursorLoader {

	private static final Uri URI = PivotalTasksTable.URI.buildUpon().appendQueryParameter(PivotalContentProvider.TASK_URI, PivotalPeopleTable.URI.toString()).build();
	private static final int LOADER_ID = 1;

	public PivotalPeopleTaskCursorLoader(Context context, LoaderManager loaderManager, PivotalCursorLoaderCallbacks pivotalCursorLoaderCallbacks) {
		super(context, loaderManager, pivotalCursorLoaderCallbacks);
	}

	@Override
	public Uri getUri() {
		return URI;
	}

	@Override
	public int getLoaderId() {
		return LOADER_ID;
	}

}
