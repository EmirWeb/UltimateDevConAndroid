package pivotal.architecture.models;

import com.google.gson.annotations.SerializedName;

import android.content.ContentValues;

public class PivotalModel {

	public static final class Keys {
		public static final String LAST_NAME = "lastName";
		public static final String FIRST_NAME = "firstName";
		public static final String ADDRESS = "address";
		public static final String CITY = "city";
	}

	@SerializedName(Keys.LAST_NAME)
	private final String mLastName;
	@SerializedName(Keys.FIRST_NAME)
	private final String mFirstName;
	@SerializedName(Keys.ADDRESS)
	private final String mAddress;
	@SerializedName(Keys.CITY)
	private final String mCity;

	public PivotalModel(final String lastName, final String firstName, final String address, final String city) {
		mLastName = lastName;
		mFirstName = firstName;
		mAddress = address;
		mCity = city;
	}

	public String getLastName() {
		return mLastName;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public String getAddress() {
		return mAddress;
	}

	public String getCity() {
		return mCity;
	}

	public ContentValues getcontentValues() {
		final ContentValues value = new ContentValues();
		value.put("mFirstName", getFirstName());
		value.put("mLastName", getLastName());
		value.put("mAddress", getAddress());
		value.put("mCity", getCity());
		return value;
	}

	public static ContentValues getContentValues(PivotalModel item) {
		return item.getcontentValues();
	}
}
