package com.inn.cafe.serviceImpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateCategoryMap(requestMap, false)){
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity("Category successfully added", HttpStatus.OK);
                }
                else{
                    return CafeUtils.getResponseEntity("Invalid request", HttpStatus.BAD_REQUEST);
                }
            }
            else{
                return CafeUtils.getResponseEntity("You are not authorized to perform this action", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }
            else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }
}
