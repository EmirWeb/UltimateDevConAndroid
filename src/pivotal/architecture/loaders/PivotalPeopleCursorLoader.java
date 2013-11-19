package pivotal.architecture.loaders;

import pivotal.architecture.callbacks.PivotalCursorLoaderCallbacks;
import pivotal.workshop.database.PivotalPeopleView;
import android.app.LoaderManager;
import android.content.Context;
import android.net.Uri;

public class PivotalPeopleCursorLoader extends PivotalCursorLoader {

	private static final Uri URI = PivotalPeopleView.URI;
	private static final int LOADER_ID = 0;

	public PivotalPeopleCursorLoader(Context context, LoaderManager loaderManager, PivotalCursorLoaderCallbacks pivotalCursorLoaderCallbacks) {
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
