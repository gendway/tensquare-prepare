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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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

    /**新增*/
    @Test
    public void testSave(){
        Customer c = new Customer();
        c.setCustName("中国移动");
        c.setCustSource("移动通信");
        c.setCustLevel("VIP客户");
        c.setCustIndustry("通信");
        c.setCustPhone("0755-88822233");
        c.setCustAddress("兴东地铁附近");
        customerDao.save(c);
    }

    /**
     * 根据id查询
     */
    @Test
    public void testFindById(){
        Customer c = customerDao.findOne(6L);
        System.out.println(c);
    }

    /**
     * save : 保存或者更新
     *   * 如果实体的id没有值，值在数据库表中不存在，此时新增
     *   * 如果实体的id有值，并且值在数据库中存在，执行更新
     */
    @Test
    public void testSave_update(){
        // 方案一：new 对象，指定id，更新；保证更新的实体中都有值，否则null值更新到数据库
//        Customer c = new Customer();
//        c.setCustId(6L);
//        c.setCustName("联想-lenovo");
//        c.setCustSource("计算机");
//        c.setCustLevel("VIP客户");
//        c.setCustIndustry("通信");
        // 方案二：使用id 查询数据库，通过set方法设置发生变化的属性，没有发生变化的属性，仍然保持原来的值，完成更新
        Customer c = customerDao.findOne(6L);
        c.setCustName("联想品牌");
        customerDao.save(c);

    }

    /**
     * delete :删除
     */
    @Test
    public void testDelete(){
        customerDao.delete(6L);
    }

    /**
     * 查询数量
     */
    @Test
    public void testCount(){
        long count = customerDao.count();
        System.out.println(count);
    }

    /**
     * 判断id是否存在
     */
    @Test
    public void testIdExist(){
        boolean exists = customerDao.exists(6L);
        System.out.println(exists);
    }

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

    /**
     * 查询所有（排序）
     */
    @Test
    public void testFindAllSort(){
        List<Customer> list = customerDao.findAll(new Sort(Sort.Direction.DESC,"custId"));
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    /**
     * 查询所有（分页+排序）
     */
    @Test
    public void testFindAllPage(){
        /**
         * PageRequest
         *  * @param page zero-based page index.（当前页，0表示第1页）
         *  * @param size the size of the page to be returned.（每页显示的记录数）
         */
        Pageable pageable = new PageRequest(0,2,new Sort(Sort.Direction.DESC,"custId"));
        Page<Customer> page = customerDao.findAll(pageable);
        System.out.println("总记录数："+page.getTotalElements());
        System.out.println("总页数："+page.getTotalPages());
        for (Customer customer : page.getContent()) {
            System.out.println(customer);
        }
    }

    /**
     * 根据id查询
     *    * findOne：表示立即检索
     *          * 底层：em.find(domainType, id);
     *          * 执行findOne的方法，马上查询数据库，返回当前对象，并封装查询结果，使用System.out.println，既能输出结果
     *    * getOne：表示延迟检索
     *          * 底层：em.getReference(getDomainClass(), id);
     *          * 执行getOne的方法，不会马上查询数据库，返回一个代理对象；使用System.out.println输出，是否可以查询数据库？（昨天：保证EntityManager对象不能关闭）
     *             * 抛出异常：org.hibernate.LazyInitializationException: could not initialize proxy - no Session
     *             * 原因：当执行 getOne方法的时候，此时要求执行：
     *                  Customer c = customerDao.getOne(7L);
                        System.out.println(c);
                    需要在一个事务中完成，而现在操作完getOne的方法，事务就结束了（EntityManager也就关闭了）
                   * 解决方案：保证以下2行代码，在同一个事务中完成就可以了，在测试的方法上添加@Transactional
     *                  Customer c = customerDao.getOne(7L);
                        System.out.println(c);

     */
    @Test
    @Transactional
    public void testFindAndGetById(){
        Customer c = customerDao.getOne(7L);
        System.out.println(c);
    }

    /******************************************测试接口中自定义的方法*******************************************************************/
    // 查询所有
    @Test
    public void testQueryAll(){
        List<Customer> list = customerDao.queryAll();
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    // 按照名称模糊查询
    @Test
    public void testQueryByCustName(){
        String custName = "%中%";
        List<Customer> list = customerDao.queryCustName(custName);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    // 按照名称模糊查询 和按照客户的所属行业完全匹配查询
    @Test
    public void testQueryCustNameAndCustIndustry(){
        String custName = "%中%";
        String custIndustry= "IT教育行业";
        List<Customer> list = customerDao.queryCustNameAndCustIndustry(custIndustry,custName);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    // 更新custName的字段
    @Test
    @Transactional // 测试增删改的时候，必须添加事务
    @Rollback(value = false) // 默认情况下，当执行增删改的时候，操作完成之后，默认事务回滚，所以需要设置事务不回滚
    public void testUpdateCustName(){
        Long id = 7L;
        String custName = "移动公司";
        customerDao.updateCustName(id,custName);
    }

    // 查询所有（sql语句）
    @Test
    public void testQueryAllSql(){
        List<Object[]> list = customerDao.findAllSql();
        for (Object o[] : list) {
            System.out.println(Arrays.toString(o));
        }
    }

    // 按照名称模糊查询（sql语句）
    @Test
    public void testFindByCustNameLikeSql(){
        String custName = "%中%";
        List<Object[]> list = customerDao.findByCustNameLikeSql(custName);
        for (Object o[] : list) {
            System.out.println(Arrays.toString(o));
        }
    }

    // 按照名称模糊查询+按照客户所属行业完全匹配（sql语句）
    @Test
    public void testFindByCustNameLikeAndCustIndustrySql(){
        String custName = "%中%";
        String custIndustry = "IT教育行业";
        List<Object[]> list = customerDao.findByCustNameLikeAndCustIndustrySql(custName,custIndustry);
        for (Object o[] : list) {
            System.out.println(Arrays.toString(o));
        }
    }

    // 按照客户名称的完全匹配查询（使用findBy语法）
    @Test
    public void testFindByCustName(){
        String custName = "中科软";
        List<Customer> list = customerDao.findByCustName(custName);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    // 按照客户名称的模糊查询（使用findBy语法）
    @Test
    public void testFindByCustNameLike(){
        String custName = "%中%";
        List<Customer> list = customerDao.findByCustNameLike(custName);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    // 按照客户名称的模糊查询+按照客户所属行业完全匹配
    @Test
    public void testFindByCustNameLikeAndCustIndustry(){
        String custName = "%中%";
        String custIndustry = "IT教育行业";
        List<Customer> list = customerDao.findByCustNameLikeAndCustIndustry(custName,custIndustry);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }
}
