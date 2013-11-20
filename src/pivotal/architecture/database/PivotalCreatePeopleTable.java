package pivotal.architecture.database;

import pivotal.architecture.providers.PivotalContentProvider;
import android.net.Uri;
import android.provider.BaseColumns;

public class PivotalCreatePeopleTable {

	public static final class Columns implements BaseColumns{
		public static final String LAST_NAME = PivotalPeopleTable.Columns.LAST_NAME;
		public static final String FIRST_NAME = PivotalPeopleTable.Columns.FIRST_NAME;
		public static final String ADDRESS = PivotalPeopleTable.Columns.ADDRESS;
		public static final String CITY = PivotalPeopleTable.Columns.CITY;
		public static final String STATE = "state";
		public static final String ID = PivotalPeopleTable.Columns.ID;
	}
	
	public static final class States {
		public static final String UPLOADING = "uploading";
		public static final String UPLOADED = "uploaded";
		public static final String PENDING_UPLOAD = "pendingUpload";
	}

	public static String TABLE_NAME = "createPeople";
	
	public static final String DROP = "DROP TABLE IF EXISTS "+ TABLE_NAME;
	
	public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
	Columns._ID + " INTEGER PRIMARY KEY, " +
	Columns.ID + " INTEGER, " +
	Columns.LAST_NAME + " varchar(255), " + 
	Columns.FIRST_NAME + " varchar(255), " + 
	Columns.ADDRESS + "	varchar(255), " +
	Columns.STATE + "	varchar(255), " +
	Columns.CITY + " varchar(255) " + ");";

	public static final String URI_PATH = TABLE_NAME;
	public static final Uri URI = Uri.parse(PivotalContentProvider.CONTENT + PivotalContentProvider.AUTHORITY + "/" +  TABLE_NAME); 
	public static final int CODE = 4;
}
