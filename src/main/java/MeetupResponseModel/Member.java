package MeetupResponseModel;

public class Member
{
    private String member_id;

    private String photo;

    private String member_name;

    public String getMember_id ()
    {
        return member_id;
    }

    public void setMember_id (String member_id)
    {
        this.member_id = member_id;
    }

    public String getPhoto ()
    {
        return photo;
    }

    public void setPhoto (String photo)
    {
        this.photo = photo;
    }

    public String getMember_name ()
    {
        return member_name;
    }

    public void setMember_name (String member_name)
    {
        this.member_name = member_name;
    }

    @Override
    public String toString()
    {
        return "\n\tMember [\n\t\tmember_id = "+member_id+", \n\t\tphoto = "+photo+", \n\t\tmember_name = "+member_name+"]";
    }
}
