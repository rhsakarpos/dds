package MeetupResponseModel;

public class Group_topics
{
    private String urlkey;

    private String topic_name;

    public String getUrlkey ()
    {
        return urlkey;
    }

    public void setUrlkey (String urlkey)
    {
        this.urlkey = urlkey;
    }

    public String getTopic_name ()
    {
        return topic_name;
    }

    public void setTopic_name (String topic_name)
    {
        this.topic_name = topic_name;
    }

    @Override
    public String toString()
    {
        return "Group_topics [urlkey = "+urlkey+", topic_name = "+topic_name+"]";
    }
}