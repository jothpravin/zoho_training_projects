package ecommerence.controller;

import java.util.List;

import ecommerence.dao.MerchantDAO;
import ecommerence.models.Merchant;
import ecommerence.models.Product;
import ecommerence.view.MerchantView;

public class MerchantController 
{
    private MerchantDAO merchantDAO = new MerchantDAO();
    private MerchantView merchantView = new MerchantView();

    public MerchantController(MerchantDAO merchantDAO, MerchantView merchantView)
    {
        this.merchantDAO = merchantDAO;
        this.merchantView = merchantView;
    }
    
    public void signUp()
    {
        Merchant merchant = merchantView.newMerchant();
        merchantDAO.newMerchant(merchant);
    }

    public int login()
    {
        String[] credentials = merchantView.credentials();
        if(credentials != null)
        {
            return merchantDAO.login(credentials[0], credentials[1]);
        }
        return -1;
    }

    public void getMerchanName()
    {
        String merchant = merchantDAO.getMerchant().getFirstName();
        merchantView.getMerchanName(merchant);
    }

    public boolean logout()
    {
        merchantView.logout();
        return true;
    } 

    public void addNewProduct()
    {
        Product product = merchantView.newProduct(merchantDAO.getAllCategories());
        
        merchantDAO.newProduct(product);
    }

    public void updateProductPrice()
    {
        List<String> products = merchantDAO.getAllProducts();
        int []inputs = merchantView.updateProductPrice(products);
        if(inputs == null)
        {
            return;
        }
        merchantDAO.updateProductPrice(inputs[1], inputs[0]);

    }

    public void updateProductQuantity()
    {
        List<String> products = merchantDAO.getAllProducts();
        int []inputs = merchantView.updateProductQuantity(products);
        if(inputs == null)
        {
            return;
        }
        merchantDAO.updateProductQuantity(inputs[1], inputs[0]);
    }

    public void showProfile()
    {
        merchantView.showProfile(merchantDAO.getMerchant());
    }

    public void showDashboard()
    {
        merchantView.dashBoard(merchantDAO.getProductSummaryByMerchant());
    }

}
