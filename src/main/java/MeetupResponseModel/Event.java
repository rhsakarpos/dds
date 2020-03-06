package MeetupResponseModel;

public class Event
{
    private String event_id;

    private String event_name;

    private String time;

    private String event_url;

    public String getEvent_id ()
    {
        return event_id;
    }

    public void setEvent_id (String event_id)
    {
        this.event_id = event_id;
    }

    public String getEvent_name ()
    {
        return event_name;
    }

    public void setEvent_name (String event_name)
    {
        this.event_name = event_name;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getEvent_url ()
    {
        return event_url;
    }

    public void setEvent_url (String event_url)
    {
        this.event_url = event_url;
    }

    @Override
    public String toString()
    {
        return "\n\tEvent [\n\t\tevent_id = "+event_id+", \n\t\tevent_name = "+event_name+", \n\t\ttime = "+time+", \n\t\tevent_url = "+event_url+"]";
    }
}
