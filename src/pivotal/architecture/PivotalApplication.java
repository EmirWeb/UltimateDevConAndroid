package pivotal.architecture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import pivotal.architecture.models.PivotalModel;
import android.app.Application;

import com.google.gson.stream.JsonWriter;

public class PivotalApplication extends Application {

	public static final String DEBUG_TAG = "PivotalLabs";

	public static final String NETWORK_ROOT_DIRECTORY = "pivotalNetwork";
	public static final String NETWORK_PEOPLE_DIRECTORY = "people";

	@Override
	public void onCreate() {
		super.onCreate();

		for (int id = 0; id < 100; id++) {
			final File file = new File(NETWORK_PEOPLE_DIRECTORY + "/" + NETWORK_PEOPLE_DIRECTORY + "/" + id + ".json");
			FileOutputStream fileOutputStream = null;
			OutputStreamWriter outputStreamWriter = null;
			JsonWriter jsonWriter  = null;
			try {
				fileOutputStream = new FileOutputStream(file);
				outputStreamWriter = new OutputStreamWriter(fileOutputStream);
				jsonWriter = new JsonWriter(outputStreamWriter);
				jsonWriter.beginObject();
				
				jsonWriter.name(PivotalModel.Keys.ADDRESS);
				jsonWriter.value("45 Madison Ave.");
				
				jsonWriter.name(PivotalModel.Keys.LAST_NAME);
				jsonWriter.value("Hasanbegovic");
				
				jsonWriter.name(PivotalModel.Keys.FIRST_NAME);
				jsonWriter.value("Emir" + id);
				
				jsonWriter.name(PivotalModel.Keys.CITY);
				jsonWriter.value("Toronto");
				jsonWriter.endObject();
				jsonWriter.flush();
				outputStreamWriter.flush();
				fileOutputStream.flush();
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}finally{
				if (jsonWriter != null){
					try {
						jsonWriter.close();
					} catch (IOException e) {
					}
				}
				if (outputStreamWriter != null){
					try {
						outputStreamWriter.close();
					} catch (IOException e) {
					}
				}

				if (fileOutputStream != null){
					try {
						fileOutputStream.close();
					} catch (IOException e) {
					}
				}
			}
			
		}

	}
}
