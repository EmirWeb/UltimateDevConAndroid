package pivotal.architecture.services;

import java.io.File;
import java.io.IOException;

import pivotal.architecture.PivotalApplication;
import pivotal.architecture.database.PivotalCreatePeopleTable;
import pivotal.architecture.database.PivotalPeopleTable;
import pivotal.architecture.database.PivotalPeopleView;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class PivotalUploadService extends IntentService {

	public PivotalUploadService() {
		this(null);
	}

	public PivotalUploadService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		final String selectionClause = PivotalCreatePeopleTable.Columns.STATE + "=?";
		final String[] selectionArguments = new String[] { PivotalCreatePeopleTable.States.PENDING_UPLOAD };
		final Cursor cursor = getContentResolver().query(PivotalCreatePeopleTable.URI, null, selectionClause, selectionArguments, null);
		while (cursor.moveToNext()) {
			upload(cursor);
		}
		cursor.close();
		PivotalService.startTask(getApplicationContext(), PivotalPeopleTable.URI);
	}

	private void upload(Cursor cursor) {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
		
		final int createIdColumnIndex = cursor.getColumnIndex(BaseColumns._ID);
		final long createId = cursor.getLong(createIdColumnIndex);

		setState(createId, PivotalCreatePeopleTable.States.UPLOADING);
		final int firstNameColumnIndex = cursor.getColumnIndex(PivotalCreatePeopleTable.Columns.FIRST_NAME);
		final String firstName = cursor.getString(firstNameColumnIndex);

		final int lastNameColumnIndex = cursor.getColumnIndex(PivotalCreatePeopleTable.Columns.LAST_NAME);
		final String lastName = cursor.getString(lastNameColumnIndex);

		final int addressColumnIndex = cursor.getColumnIndex(PivotalCreatePeopleTable.Columns.ADDRESS);
		final String address = cursor.getString(addressColumnIndex);

		final int cityColumnIndex = cursor.getColumnIndex(PivotalCreatePeopleTable.Columns.CITY);
		final String city = cursor.getString(cityColumnIndex);

		final String directoryPath = getExternalCacheDir() + "/" + PivotalApplication.NETWORK_PEOPLE_DIRECTORY + "/" + PivotalApplication.NETWORK_PEOPLE_DIRECTORY + "/";
		final File directory = new File(directoryPath);
		int id = 0;
		File file = new File(directory, id + ".json");
		while (file.exists()) {
			id++;
			file = new File(directory, id + ".json");
		}

		try {
			PivotalApplication.createPerson(file, id, address, lastName, firstName, city);
			setId(createId, id);
			setState(createId, PivotalCreatePeopleTable.States.UPLOADED);
		} catch (IOException e) {
			setState(createId, PivotalCreatePeopleTable.States.PENDING_UPLOAD);
		}

	}

	private void setId(long createId, int id) {
		final ContentValues contentValues = new ContentValues();
		contentValues.put(PivotalCreatePeopleTable.Columns.ID, id);
		update(createId, contentValues);
	}
	
	private void update(final long createId, final ContentValues contentValues){
		final ContentResolver contentResolver = getContentResolver();
		final String selectionClause = BaseColumns._ID + "=?";
		final String[] selectionArguments = new String[] { Long.toString(createId) };
		getContentResolver().update(PivotalCreatePeopleTable.URI, contentValues, selectionClause, selectionArguments);
		final Uri uri = Uri.withAppendedPath(PivotalCreatePeopleTable.URI, Long.toString(createId));
		getContentResolver().notifyChange(uri, null);
		contentResolver.notifyChange(PivotalPeopleView.URI, null);
	}

	private void setState(final long createId, final String state) {
		final ContentValues contentValues = new ContentValues();
		contentValues.put(PivotalCreatePeopleTable.Columns.STATE, state);
		update(createId, contentValues);
	}
}
