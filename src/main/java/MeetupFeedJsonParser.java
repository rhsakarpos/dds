import MeetupResponseModel.MeetupRSVP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MeetupFeedJsonParser {

    public static MeetupRSVP ParseJson(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        MeetupRSVP meetupRSVP = gson.fromJson(jsonString, MeetupRSVP.class);
        return meetupRSVP;
    }
}
