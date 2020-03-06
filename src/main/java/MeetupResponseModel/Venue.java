package MeetupResponseModel;

public class Venue
{
    private String venue_name;

    private String lon;

    private String lat;

    private String venue_id;

    public String getVenue_name ()
    {
        return venue_name;
    }

    public void setVenue_name (String venue_name)
    {
        this.venue_name = venue_name;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    public String getVenue_id ()
    {
        return venue_id;
    }

    public void setVenue_id (String venue_id)
    {
        this.venue_id = venue_id;
    }

    @Override
    public String toString()
    {
        return "\n\tVenue [\n\t\tvenue_name = "+venue_name+", \n\t\tlon = "+lon+", \n\t\tlat = "+lat+", \n\t\tvenue_id = "+venue_id+"]";
    }
}
