package ecommerence.view;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

import ecommerence.dao.MerchantDAO;
import ecommerence.models.Category;
import ecommerence.models.Merchant;
import ecommerence.models.Product;
import ecommerence.utils.Validator;

public class MerchantView 
{
    static Scanner in = new Scanner(System.in);
    static Console con = System.console();

    public Merchant newMerchant()
    {
        System.out.print("Enter the First Name :");
        String fName = in.next();
        System.out.print("Enter the Last Name :");
        String lName = in.next();
        System.out.print("Enter the Email ID: ");
        String email = in.next();
        while(!Validator.emailValidator(email))
        {
            System.out.print("Enter the Valid Email ID: ");
            email = in.next();
        }
        System.out.print("Enter the Password: ");
        char[] pas = con.readPassword();
        String pass = new String(pas);
        while(!Validator.isValidPassword(pass))
        {
            System.out.print("Enter the Valid Password: ");
            pass = in.next();
        }
        System.out.print("Enter the Mobile number: ");
        Long mobile = in.nextLong();in.nextLine();
        System.out.print("Enter the Comapny Name :");
        String companyName = in.nextLine();
        System.out.print("Enter the Comapny address:");
        String address = in.nextLine(); 
        System.out.print("Enter the GST number: ");
        String gst_no = in.next();
        while(!Validator.isValidGSTNo(gst_no))
        {
            System.out.print("Enter the GST number: ");
            gst_no = in.next();
        }
        System.out.print("Enter the pan number: ");
        String pan_no = in.next();
        while(!Validator.isValidPan(pan_no))
        {
            System.out.print("Enter the GST number: ");
            pan_no = in.next();
        }
        Merchant merchant = new Merchant(fName, lName, email, pass, mobile, companyName, address, gst_no, pan_no);
        return merchant;
    }

    public String[] credentials()
    {
        System.out.print("Enter the Email ID: ");
		String email = in.next();
		System.out.print("Enter the Password: ");
        char[] pas = con.readPassword();
		String pass = new String(pas);
		if(Validator.emailValidator(email) && Validator.isValidPassword(pass))
		{
			return new String[]{email, pass};
		}
        return null;
    }

    public void getMerchanName(String merchanName)
    {
        System.out.println(merchanName);
    }

    public void logout()
    {
        System.out.println("Logging out...!");
    }

    private void showAllCategories(List<Category> categories)
    {
        System.out.println("---------All--categories-----------");
        for(Category category : categories)
        {
            System.out.println("Category ID :"+category.getCategoryId()+". Category Name :"+category.getCategoryName());
        }
        System.out.println("-----------------------------------");
    }

    public Product newProduct(List<Category> categories)
    {
        in.nextLine();
        System.out.print("Enter the Product Name :");
        String pName = in.nextLine();
        System.out.print("Enter the Product description :");
        String pdesc = in.nextLine();
        System.out.print("Enter the Product price :");
        int price = in.nextInt();
        showAllCategories(categories);	
        System.out.print("Enter the category id:");
        int categoryid = in.nextInt();
        System.out.print("Enter the stock quantity:");
        int stockQuantity = in.nextInt();
        Product product = new Product(pName, pdesc, price, categoryid, MerchantDAO.MERCHANTID, stockQuantity);
        return product;
    }

    public int[] updateProductPrice(List<String> products)
    {
        if(!showProduct(products))
        {
            return null;
        }
        System.out.print("Enter the Productid to Change the price (0 to back):");
        int input = in.nextInt();
        if(input == 0)
        {
            return null;
        }
        System.out.print("Enter the Product price :");
        int price = in.nextInt();
        return new int[]{input, price};

    }

    public int[] updateProductQuantity(List<String> products)
    {
        if(!showProduct(products))
        {
            return null;
        }
        System.out.print("Enter the Productid to Change the price (0 to back):");
        int input = in.nextInt();
        if(input == 0)
        {
            return null;
        }
        System.out.print("Enter the Product quantity to add:");
        int quantity = in.nextInt();
        return new int[]{input, quantity};
    }

    private boolean showProduct(List<String> products)
    {
        if(products.isEmpty())
        {
            return false;
        }
        for(String product: products)
        {
            System.out.println("------------------------------");
            System.out.println(product);
        }
        System.out.println("------------------------------");
        return true;
    }

    public void showProfile(Merchant merchant)
    {
        System.out.println("------------------------------");
        System.out.println("Merchant Id :"+merchant.getMerchantId());
        System.out.println("Name :"+merchant.getFirstName());
        System.out.println("Company :"+merchant.getCompanyName());
        System.out.println("Email :"+merchant.getEmail());
        System.out.println("Mobile :"+merchant.getMobile_no());
        System.out.println("PAN No :"+merchant.getPAN_no());
        System.out.println("GST No :"+merchant.getGST_no());
        System.out.println("Wallet Amount:"+merchant.getAmount());
        System.out.println("------------------------------");
    }

    public void dashBoard(List<String> saleSummaries)
    {
        System.out.println("---------DashBoard-----------");
        for(String saleSummary : saleSummaries)
        {
            System.out.println(saleSummary);
            System.out.println("------------------------------");
        }
    }
}