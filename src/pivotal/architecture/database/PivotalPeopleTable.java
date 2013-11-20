package pivotal.architecture.database;

import pivotal.architecture.models.PivotalPeopleModel;
import pivotal.architecture.providers.PivotalContentProvider;
import android.net.Uri;

public class PivotalPeopleTable {

	public static final class Columns {
		public static final String LAST_NAME = PivotalPeopleModel.Keys.LAST_NAME;
		public static final String FIRST_NAME = PivotalPeopleModel.Keys.FIRST_NAME;
		public static final String ADDRESS = PivotalPeopleModel.Keys.ADDRESS;
		public static final String CITY = PivotalPeopleModel.Keys.CITY;
	}

	public static String TABLE_NAME = "people";
	
	public static final String DROP = "DROP TABLE IF EXISTS "+ TABLE_NAME;
	
	public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + 
	Columns.LAST_NAME + " varchar(255) NOT NULL, " + 
	Columns.FIRST_NAME + " varchar(255), " + 
	Columns.ADDRESS + "	varchar(255), " + 
	Columns.CITY + " varchar(255) " + ");";

	public static final String DATA_CREATE = " INSERT INTO " + TABLE_NAME + 
			" ( " + Columns.LAST_NAME + ", " +Columns.FIRST_NAME + ", " +Columns.ADDRESS + ", " +Columns.CITY + " ) " +
			" values " + 
			" ( 'Emir', 'Hasanbegovic', '69 Yonge Street' , 'Toronto'), " +
			" ( 'Aaron', 'Jarecki', '69 Yonge Street' , 'Toronto'), " +
			" ( 'Rob', 'Ford', 'City Hall' , 'Toronto'), " +
			" ( 'Elliott', 'Garcea', '69 Yonge Street' , 'Toronto'), " +
			" ( 'Adrian', 'Kemp', '69 Yonge Street' , 'Toronto'); ";
	
	public static final String URI_PATH = TABLE_NAME;
	public static final Uri URI = Uri.parse(PivotalContentProvider.CONTENT + PivotalContentProvider.AUTHORITY + "/" +  TABLE_NAME); 
	public static final int CODE = 1;
}
