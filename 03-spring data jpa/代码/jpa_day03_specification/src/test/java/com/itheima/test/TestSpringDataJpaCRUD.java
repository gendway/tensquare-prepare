package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @ClassName TestSpringDataJpaCRUD
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2018/10/13 10:01
 * @Version V1.0
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestSpringDataJpaCRUD {

    @Autowired
    CustomerDao customerDao;

    /**
     * 查询所有
     */
    @Test
    public void testFindAll(){
        List<Customer> list = customerDao.findAll();
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /***
     * 1.2使用Specifications完成条件查询
     *  按照客户的名称完全匹配的查询
     */
    @Test
    public void testFindAllSpecificationByCustName(){
        Specification specification = new Specification() {
            /**
             * 组织查询条件（API）
             * @param root：获取查询的字段
             * @param query：用来连接查询条件
             * @param cb：用于封装高级的查询条件，获取用来连接语法（比如：equal()、like()、gt()、ge()...)
             * @return
             */
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Predicate predicate = null;
                // 组织按照客户名称查询
                // 1：获取查询的字段（返回查询字段的Path对象）
                Path custName = root.get("custName");
                // 2：组织查询条件
                predicate = cb.equal(custName, "中科软");
                return predicate;
            }
        };
        List<Customer> list = customerDao.findAll(specification);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 1.3使用Specifications完成多条件查询
     * 按照客户名称和客户的所属行业，完成查询
     */
    @Test
    public void testFindAllSpecificationByCustNameAndCustIndustry(){
        Specification specification = new Specification() {
            /**
             * 组织查询条件（API）
             * @param root：获取查询的字段
             * @param query：用来连接查询条件
             * @param cb：用于封装高级的查询条件，获取用来连接语法（比如：equal()、like()、gt()、ge()...)
             * @return
             */
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                // 组织按照客户名称查询
                // 1：获取查询的字段（返回查询字段的Path对象）
                Path custName = root.get("custName");
                Path custIndustry = root.get("custIndustry");
                // 2：组织查询条件
                Predicate p1 = cb.equal(custName, "中科软"); // and custName = '中科软'
                Predicate p2 =  cb.equal(custIndustry,"IT教育行业");// and custIndustry = 'IT教育行业'

                Predicate predicate = cb.and(p1, p2);
                return predicate;
            }
        };
        List<Customer> list = customerDao.findAll(specification);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 1.4使用Specifications完成模糊条件查询
     * 按照客户名称完成模糊查询
     */
    @Test
    public void testFindAllSpecificationByCustNameLike(){
        Specification specification = new Specification() {
            /**
             * 组织查询条件（API）
             * @param root：获取查询的字段
             * @param query：用来连接查询条件
             * @param cb：用于封装高级的查询条件，获取用来连接语法（比如：equal()、like()、gt()、ge()...)
             * @return
             */
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                // 组织按照客户名称查询
                // 1：获取查询的字段（返回查询字段的Path对象）
                Path custName = root.get("custName");
                // 2：组织查询条件
                Predicate predicate = cb.like(custName.as(String.class), "%联%"); // and custName like '%联%'

                return predicate;
            }
        };
        List<Customer> list = customerDao.findAll(specification);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 1.5使用Specifications完成模糊条件查询+排序查询
     */
    @Test
    public void testFindAllSpecificationByCustNameLikeOrderBy(){
        Specification specification = new Specification() {
            /**
             * 组织查询条件（API）
             * @param root：获取查询的字段
             * @param query：用来连接查询条件
             * @param cb：用于封装高级的查询条件，获取用来连接语法（比如：equal()、like()、gt()、ge()...)
             * @return
             */
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                // 组织按照客户名称查询
                // 1：获取查询的字段（返回查询字段的Path对象）
                Path custName = root.get("custName");
                // 2：组织查询条件
                Predicate predicate = cb.like(custName.as(String.class), "%联%"); // and custName like '%联%'

                return predicate;
            }
        };
        // 添加排序的对象
        Sort sort = new Sort(Sort.Direction.DESC,"custId");
        List<Customer> list = customerDao.findAll(specification,sort);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 1.6基于Specifications的分页查询
     */

    @Test
    public void testFindAllSpecificationByCustNameLikeOrderByPage(){
        Specification specification = new Specification() {
            /**
             * 组织查询条件（API）
             * @param root：获取查询的字段
             * @param query：用来连接查询条件
             * @param cb：用于封装高级的查询条件，获取用来连接语法（比如：equal()、like()、gt()、ge()...)
             * @return
             */
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                // 组织按照客户名称查询
                // 1：获取查询的字段（返回查询字段的Path对象）
                Path custName = root.get("custName");
                // 2：组织查询条件
                Predicate predicate = cb.like(custName.as(String.class), "%联%"); // and custName like '%联%'

                return predicate;
            }
        };
        // 添加排序的对象
        Sort sort = new Sort(Sort.Direction.ASC,"custId");
        Pageable pageable = new PageRequest(1,2,sort); // 从第2页开始，每页显示2条数据
        Page<Customer> page = customerDao.findAll(specification, pageable);
        System.out.println("总记录数："+page.getTotalElements());
        System.out.println("总页数："+page.getTotalPages());
        for (Customer customer : page.getContent()) {
            System.out.println(customer);
        }
    }
}
