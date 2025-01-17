package com.saeromteo.app.service.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saeromteo.app.dao.product.ProductCategoryDAO;
import com.saeromteo.app.model.product.ProductCategoryEntity;

@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryDAO productCategoryDAO;

    public List<String> readAll_major() {
        return productCategoryDAO.readAll_major();
    }
    public List<Map<String,String>> readAll_middle() {
        return productCategoryDAO.readAll_middle();
    }
    public List<ProductCategoryEntity> readAll() {
        return productCategoryDAO.readAll();
    }

    public ProductCategoryEntity readByCategoryNumber(int categoryNumber) {
        return productCategoryDAO.readByCategoryNumber(categoryNumber);
    }

    public int insertCategory(ProductCategoryEntity category) {
       return productCategoryDAO.insertCategory(category);
    }

    public int updateCategory(ProductCategoryEntity category) {
       return productCategoryDAO.updateCategory(category);
    }

    public int deleteCategory(int categoryNumber) {
       return productCategoryDAO.deleteCategory(categoryNumber);
    }
    
    public List<ProductCategoryEntity> readByCategoryDetails(String majorCategory, String middleCategory, String smallCategory) {
        return productCategoryDAO.readByCategoryDetails(majorCategory, middleCategory, smallCategory);
    }
    
	public List<ProductCategoryEntity> readCategoriesByProductCode(String productCode) {
		 return productCategoryDAO.readCategoriesByProductCode(productCode);
	}
}
