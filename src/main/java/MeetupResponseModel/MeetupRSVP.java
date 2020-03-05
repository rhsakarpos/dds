package MeetupResponseModel;

public class MeetupRSVP {

    private Venue venue;

    private String visibility;

    private String response;

    private String guests;

    private Member member;

    private String rsvp_id;

    private String mtime;

    private Event event;

    private Group group;

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getRsvp_id() {
        return rsvp_id;
    }

    public void setRsvp_id(String rsvp_id) {
        this.rsvp_id = rsvp_id;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "MeetupRSVP [venue = " + venue + ", visibility = " + visibility + ", response = " + response + ", guests = " + guests + ", member = " + member + ", rsvp_id = " + rsvp_id + ", mtime = " + mtime + ", event = " + event + ", group = " + group + "]";
    }
}
