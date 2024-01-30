package ecommerence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ecommerence.models.Category;
import ecommerence.models.Merchant;
import ecommerence.models.Product;
import ecommerence.utils.ConnectionDB;

public class MerchantDAO 
{
    public static int MERCHANTID;
    static Connection con = ConnectionDB.getConnection();
    
    public void newMerchant(Merchant merchant)
    {
        String query = "INSERT INTO merchant (firstname, lastname, email, password, mobile_no, companyname, address, gst_no, pan_no) VALUES"+
                        "(?, ?, ?, ?, ?, ?, ?, ? ,?)";

        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, merchant.getFirstName());
            ps.setString(2, merchant.getLastName());
            ps.setString(3, merchant.getEmail());
            ps.setString(4, merchant.getPassword());
            ps.setLong(5, merchant.getMobile_no());
            ps.setString(6, merchant.getCompanyName());
            ps.setString(7, merchant.getAddress());
            ps.setString(8, merchant.getGST_no());
            ps.setString(9, merchant.getPAN_no());

            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully New Merchant Account was Created...!");   
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Merchant getMerchant()
    {
        String query = "SELECT * FROM merchant WHERE merchantid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, MERCHANTID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                Merchant merchant = new Merchant(rs.getString("firstname"),
                                                rs.getString("lastname"), 
                                                rs.getString("email"), 
                                                rs.getString("password"), 
                                                rs.getLong("mobile_no"), 
                                                rs.getString("companyname"), 
                                                rs.getString("address"), 
                                                rs.getString("gst_no"), 
                                                rs.getString("pan_no")
                                                );
                merchant.setMerchantId(rs.getInt("merchantid"));
                merchant.setAmount(rs.getLong("amount"));
                return merchant;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int login(String email, String password)
    {
        String customer_login_query = "SELECT merchantid, password FROM merchant WHERE email = ?";

        try(PreparedStatement ps = con.prepareStatement(customer_login_query))
        {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString("password").equals(password))
                {
                    MERCHANTID = rs.getInt("merchantid");
                    return MERCHANTID;
                }
            }
            rs.close();
        }
        catch(SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void newProduct(Product product)
    {
        String query = "INSERT INTO products (productname, productdesc, price, categoryid, merchantid, available) VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductDesc());
            ps.setInt(3, product.getProductPrice());
            ps.setInt(4, product.getCategoryId());
            ps.setInt(5, product.getMerchantId());
            ps.setInt(6, product.getStockQuantity());

            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully new product listed");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public int getCategoryId(String categoryName)
	{
		String query = "SELECT categoryid FROM category WHERE category_name = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setString(1, categoryName);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return rs.getInt("categoryid");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return -1;
	}

    public List<Category> getAllCategories()
    {
        String query = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Category category = new Category(rs.getString("category_name"));
                category.setCategoryId(rs.getInt("categoryid"));
                categories.add(category);
            }
            return categories;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    public void updateProductQuantity(int quantity, int productid)
    {
        String query = "UPDATE products set available = available + ? WHERE productid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, quantity);
            ps.setInt(2, productid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Sucessfully updated in the product quantity");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void updateProductPrice(int price, int productid)
    {
        String query = "UPDATE products set price = ? WHERE productid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, price);
            ps.setInt(2, productid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Sucessfully updated in the product price");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Product getProduct(int productId)
	{
		String query = "SELECT * FROM products WHERE productid = ?";
		try(PreparedStatement ps = con.prepareStatement(query))
		{
			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				Product product = new Product(rs.getString("productname"), 
												rs.getString("productdesc"), 
												rs.getInt("price"), 
												rs.getInt("categoryid"), 
												rs.getInt("merchantid"), 
												rs.getInt("available")
												);
				product.setProductId(rs.getInt("productid"));
				product.setRatings(rs.getInt("ratings"));
				return product;	
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

    public List<String> getAllProducts()
	{
		String query = "SELECT * FROM products WHERE merchantid = ?";
		List<String> products = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement(query))
		{
            ps.setInt(MERCHANTID, MERCHANTID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				String product = new String("Product Id :"+rs.getInt("productid")+"\n"
											+"Product Name :"+rs.getString("productname")+"\n"
											+"Product Description :"+rs.getString("productdesc")+"\n"
											+"Product Price :"+rs.getInt("price")+"\n"
											+"Stock Available :"+rs.getInt("available")
											);
				products.add(product);
			}
			return products;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

    public List<String> getProductSummaryByMerchant() 
    {
        String query = "SELECT p.productname, " +
                       "SUM(o.quantity) AS total_quantity, " +
                       "SUM(o.amount) AS total_amount " +
                       "FROM ordertable o " +
                       "JOIN products p ON p.productid = o.productid " +
                       "WHERE o.merchantid = ? AND o.d_status = 'Delivered' " +
                       "GROUP BY p.productname";
    
        List<String> productSummaries = new ArrayList<>();
        try(PreparedStatement ps = con.prepareStatement(query)) 
        {
            ps.setInt(1, MERCHANTID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) 
            {
                String productName = rs.getString("productname");
                int totalQuantity = rs.getInt("total_quantity");
                double totalAmount = rs.getDouble("total_amount");
    
                String productSummary = "Product: " + productName +"\n"+
                                        "Sales Quantity: " + totalQuantity +"\n"+
                                        "Total Sales Amount: " + totalAmount;
    
                productSummaries.add(productSummary);
            }
            return productSummaries;
        } 
        catch(SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
    

}
