package pivotal.architecture.database;

import pivotal.architecture.providers.PivotalContentProvider;
import android.net.Uri;
import android.provider.BaseColumns;

public class PivotalPeopleView {
	public static final class Columns {
		public static final String LAST_NAME = PivotalPeopleTable.Columns.LAST_NAME;
		public static final String FIRST_NAME = PivotalPeopleTable.Columns.FIRST_NAME;
		public static final String ADDRESS = PivotalPeopleTable.Columns.ADDRESS;
		public static final String CITY = PivotalPeopleTable.Columns.CITY;
		public static final String STATE = PivotalCreatePeopleTable.Columns.STATE;
	}
	
	private static final class IntermediateColumns {
		public static final String ID = PivotalCreatePeopleTable.Columns.ID;
	}

	public static String VIEW_NAME = "peopleListView";

	public static final String DROP = "DROP VIEW IF EXISTS " + VIEW_NAME;

	public static final String CREATE = "CREATE VIEW " + VIEW_NAME + " AS " + 
	" SELECT" +  
	" NULL AS " + BaseColumns._ID + ", " +
	Columns.STATE + ", " +
	Columns.FIRST_NAME + ", " +
	Columns.ADDRESS + ", " +
	Columns.CITY + ", " +
	Columns.LAST_NAME + " " +
	" FROM " +
	" ( " +
		" SELECT "+
		" NULL AS " + Columns.STATE + ", " +
		PivotalPeopleTable.TABLE_NAME + "." + PivotalPeopleTable.Columns.ID + " AS " + IntermediateColumns.ID + ", " +
		PivotalPeopleTable.TABLE_NAME + "." + PivotalPeopleTable.Columns.FIRST_NAME + " AS " + Columns.FIRST_NAME + ", " +
		PivotalPeopleTable.TABLE_NAME + "." + PivotalPeopleTable.Columns.ADDRESS + " AS " + Columns.ADDRESS + ", " +
		PivotalPeopleTable.TABLE_NAME + "." + PivotalPeopleTable.Columns.CITY + " AS " + Columns.CITY + ", " +
		PivotalPeopleTable.TABLE_NAME + "." + PivotalPeopleTable.Columns.LAST_NAME + " AS " + Columns.LAST_NAME + " " +
		" FROM " + 
		PivotalPeopleTable.TABLE_NAME +
		
		" UNION ALL" +
		
		" SELECT " +
		PivotalCreatePeopleTable.TABLE_NAME + "." + PivotalCreatePeopleTable.Columns.STATE + " AS " + Columns.STATE + ", " +
		PivotalCreatePeopleTable.TABLE_NAME + "." + PivotalCreatePeopleTable.Columns.ID + " AS " + IntermediateColumns.ID + ", " +
		PivotalCreatePeopleTable.TABLE_NAME + "." + PivotalCreatePeopleTable.Columns.FIRST_NAME + " AS " + Columns.FIRST_NAME + ", " +
		PivotalCreatePeopleTable.TABLE_NAME + "." + PivotalCreatePeopleTable.Columns.ADDRESS + " AS " + Columns.ADDRESS + ", " +
		PivotalCreatePeopleTable.TABLE_NAME + "." + PivotalCreatePeopleTable.Columns.CITY + " AS " + Columns.CITY + ", " +
		PivotalCreatePeopleTable.TABLE_NAME + "." + PivotalCreatePeopleTable.Columns.LAST_NAME + " AS " + Columns.LAST_NAME + " " +
		" FROM " + PivotalCreatePeopleTable.TABLE_NAME + 
		" WHERE " +
		PivotalCreatePeopleTable.Columns.ID + " IS NULL " +
		" OR " + 
		PivotalCreatePeopleTable.Columns.ID + " NOT IN " +
		" ( " +
		" SELECT " +
		PivotalPeopleTable.TABLE_NAME + "." + PivotalPeopleTable.Columns.ID + " AS " + IntermediateColumns.ID + " " +
		" FROM " + 
		PivotalPeopleTable.TABLE_NAME +
		" ) " +
	" ) " +
	";";

	public static final String URI_PATH = VIEW_NAME;
	public static final Uri URI = Uri.parse(PivotalContentProvider.CONTENT + PivotalContentProvider.AUTHORITY + "/" + VIEW_NAME);
	public static final int CODE = 2;
}
