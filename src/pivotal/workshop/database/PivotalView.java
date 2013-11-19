package pivotal.workshop.database;

import pivotal.architecture.providers.PivotalContentProvider;
import android.net.Uri;
import android.provider.BaseColumns;

public class PivotalView {
	public static final class Columns {
		public static final String LAST_NAME = PivotalTable.Columns.LAST_NAME;
		public static final String FIRST_NAME = PivotalTable.Columns.FIRST_NAME;
		public static final String ADDRESS = PivotalTable.Columns.FIRST_NAME;
		public static final String CITY = PivotalTable.Columns.FIRST_NAME;
	}

	public static String VIEW_NAME = "peopleListView";

	public static final String DROP = "DROP VIEW IF EXISTS " + VIEW_NAME;

	public static final String CREATE = "CREATE VIEW " + VIEW_NAME + " AS SELECT NULL AS " + BaseColumns._ID + ", * FROM " + PivotalTable.TABLE_NAME + ";";

	public static final String URI_PATH = VIEW_NAME;
	public static final Uri URI = Uri.parse(PivotalContentProvider.CONTENT + PivotalContentProvider.AUTHORITY + "/" + VIEW_NAME);
	public static final int CODE = 2;
}
