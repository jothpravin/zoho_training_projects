package onlineBanking.model;

public class Branch 
{
    private int branchId;
    private String branchName;
    private String ifscCode;

    public Branch(String branchName)
    {
        this.branchName = branchName;
    }

    public void setBranchId(int branchId) 
    {
        this.branchId = branchId;
    }

    public void setIfscCode(String ifscCode)
    {
        this.ifscCode = ifscCode;
    }

    public int getBranchId() 
    {
        return branchId;
    }

    public String getBranchName() 
    {
        return branchName;
    }

    public String getIfscCode() 
    {
        return ifscCode;
    }    
}