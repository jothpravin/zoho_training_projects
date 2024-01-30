package librarymanagement;

public class Rack 
{
    private int rackID;
    private String rackName;

    public Rack(String rackName) 
    {
        this.rackID = 0;
        this.rackName = rackName;
    }

    public void setRackID(int rackID) 
    {
        this.rackID = rackID;
    }

    public int getRackID() 
    {
        return rackID;
    }

    public String getRackName() 
    {
        return rackName;
    }

    
}
