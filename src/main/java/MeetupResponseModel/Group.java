package MeetupResponseModel;

public class Group
{
    private Group_topics[] group_topics;

    private String group_city;

    private String group_country;

    private String group_id;

    private String group_name;

    private String group_lon;

    private String group_urlname;

    private String group_lat;

    public Group_topics[] getGroup_topics ()
    {
        return group_topics;
    }

    public void setGroup_topics (Group_topics[] group_topics)
    {
        this.group_topics = group_topics;
    }

    public String getGroup_city ()
    {
        return group_city;
    }

    public void setGroup_city (String group_city)
    {
        this.group_city = group_city;
    }

    public String getGroup_country ()
    {
        return group_country;
    }

    public void setGroup_country (String group_country)
    {
        this.group_country = group_country;
    }

    public String getGroup_id ()
    {
        return group_id;
    }

    public void setGroup_id (String group_id)
    {
        this.group_id = group_id;
    }

    public String getGroup_name ()
    {
        return group_name;
    }

    public void setGroup_name (String group_name)
    {
        this.group_name = group_name;
    }

    public String getGroup_lon ()
    {
        return group_lon;
    }

    public void setGroup_lon (String group_lon)
    {
        this.group_lon = group_lon;
    }

    public String getGroup_urlname ()
    {
        return group_urlname;
    }

    public void setGroup_urlname (String group_urlname)
    {
        this.group_urlname = group_urlname;
    }

    public String getGroup_lat ()
    {
        return group_lat;
    }

    public void setGroup_lat (String group_lat)
    {
        this.group_lat = group_lat;
    }

    @Override
    public String toString()
    {
        return "\n\tGroup [\n\t\tgroup_topics = "+group_topics+", \n\t\tgroup_city = "+group_city+", \n\t\tgroup_country = "+group_country+", \n\t\tgroup_id = "+group_id
                +", \n\t\tgroup_name = "+group_name+", \n\t\tgroup_lon = "+group_lon+", \n\t\tgroup_urlname = "+group_urlname+", \n\t\tgroup_lat = "+group_lat+"]";
    }
}