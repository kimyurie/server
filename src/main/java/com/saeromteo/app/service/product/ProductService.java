package com.saeromteo.app.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saeromteo.app.dao.product.ProductDAO;
import com.saeromteo.app.model.product.ProductEntity;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;

    public List<ProductEntity> readAll() {
        return productDAO.readAll();
    }

    public ProductEntity readByProductCode(String productCode) {
        return productDAO.readByProductCode(productCode);
    }

    public List<ProductEntity> readByCategory(int categoryNumber) {
        return productDAO.readByCategory(categoryNumber);
    }
    
    public List<ProductEntity> readByParentCategory(int parentCategoryNumber) {
        return productDAO.readByParentCategory(parentCategoryNumber);
    }

    public int insertProduct(ProductEntity product) {
       return productDAO.insertProduct(product);
    }

    public int updateProduct(ProductEntity product) {
       return productDAO.updateProduct(product);
    }

    public int deleteProduct(int productCode) {
       return productDAO.deleteProduct(productCode);
    }
    
    /* page : 현재 보고자 하는 페이지 번호
       size : 한 페이지에 보여줄 데이터의 개수
       offset : 데이터베이스 쿼리에서 몇 번째 데이터부터 가져올지를 결정하는 값 */
    public List<ProductEntity> readAllPaged(int page, int size) {
        int offset = (page - 1) * size; 
        return productDAO.readAllPaged(offset, size);
    }
    
    public List<ProductEntity> readAllSorted(String sortBy) {
        return productDAO.readAllSorted(sortBy);
    }
    
    // 소분류, 중분류, 대분류로 조회
    public List<ProductEntity> selectBySmallCategory(String majorCategory, String middleCategory, String smallCategory) {
        return productDAO.selectBySmallCategory(majorCategory, middleCategory, smallCategory);
    }

    public List<ProductEntity> selectByMiddleCategory(String majorCategory, String middleCategory) {
        return productDAO.selectByMiddleCategory(majorCategory, middleCategory);
    }

    public List<ProductEntity> selectByMajorCategory(String majorCategory) {
        return productDAO.selectByMajorCategory(majorCategory);
    }
    
}
