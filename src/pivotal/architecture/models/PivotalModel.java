package pivotal.architecture.models;

import android.content.ContentValues;

public class PivotalModel {
	
	private int P_Id;
	private String LastName;
	private String FirstName;
	private String Address;
	private String City;

	public int getP_Id() {
		return P_Id;
	}

	public void setP_Id(int p_Id) {
		P_Id = p_Id;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public ContentValues getcontentValues() {
		final ContentValues value = new ContentValues();
		value.put("P_ID", getP_Id());
		value.put("FirstName", getFirstName());
		value.put("LastName", getLastName());
		value.put("Address", getAddress());
		value.put("City", getCity());
		return value;
	}

	public static ContentValues getContentValues(PivotalModel item) {
		return item.getcontentValues();
	}
}
