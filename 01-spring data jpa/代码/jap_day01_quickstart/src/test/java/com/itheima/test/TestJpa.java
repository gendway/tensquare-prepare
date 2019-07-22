package com.itheima.test;

import com.itheima.domain.Customer;
import com.itheima.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName TestJpa
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2018/10/12 10:00
 * @Version V1.0
 */
public class TestJpa {

    /**
     * Persistence：JPA提供的api类，提供静态的方法createEntityManagerFactory，用来创建EntityManagerFactory
     *              * 指定persistence.xml文件中的，持久化单元的name属性
     *
     * * EntityManagerFactory：表示实体管理的工厂对象。（开发中，1个工厂；进程级别）
     * * EntityManager：表示实体管理的类（开发中，多个实体管理的类；线程级别）--操作数据库的核心对象
     *          * persist：保存数据
     * * EntityTransaction：表示JPA中的事务管理
     *          * begin：开启事务
     *          * commit：提交事务
     *          * rollback：回滚事务
     */
    /**保存*/
    @Test
    public void save(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("customerJpa");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        // 操作对象，就相当于操作数据库
        Customer customer = new Customer();
        customer.setCustName("中科软");
        customer.setCustIndustry("IT行业");
        customer.setCustSource("互联网");
        customer.setCustLevel("VIP");
        customer.setCustAddress("廷威大厦");
        customer.setCustPhone("0755-28343323");
        em.persist(customer);
        transaction.commit();
        em.close();
        entityManagerFactory.close();
    }

    /**保存*/
    @Test
    public void testSave(){
        // 操作对象，就相当于操作数据库
        Customer customer = new Customer();
        customer.setCustName("联想");
        customer.setCustIndustry("计算机硬件行业");
        customer.setCustSource("互联网");
        customer.setCustLevel("VIP");
        customer.setCustAddress("廷威大厦");
        customer.setCustPhone("0755-28343323");

        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            em.persist(customer);
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    /**修改*/
    @Test
    public void testUpdate(){
        // 操作对象，就相当于操作数据库
        // 1：new Customer，设置id的方式完成：如果是修改，需要将对象中所有的字段赋值，如果不赋值，将null值设置到字段中
//        Customer customer = new Customer();
//        customer.setCustId(2L);
//        customer.setCustName("传智播客.黑马程序员");
//        customer.setCustIndustry("最牛逼的IT教育行业");
//        customer.setCustSource("互联网");
//        customer.setCustPhone("0755-28343323");

        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            // 使用id查询，将查询的对象，进行修改
            Customer customer = em.find(Customer.class, 2L);
            customer.setCustIndustry("IT教育培训机构");
            em.merge(customer);
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    /**删除*/
    @Test
    public void testDelete(){
        // 操作对象，就相当于操作数据库

        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            // 使用id查询，将查询的对象，进行修改
            Customer customer = em.find(Customer.class, 1L);
            em.remove(customer);
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    /** ID查询
     *   * find方法：表示的是立即检索，只要调用find方法，马上查询数据库，返回的是一个真实的Customer对象
     * */
    @Test
    public void testGetOne(){
        // 操作对象，就相当于操作数据库
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            // 使用id查询，将查询的对象，进行修改
            Customer customer = em.find(Customer.class, 2L);
            System.out.println(customer);
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    // 缓存，第一次使用id=2查询数据库，从数据库中查询；第二次再次查询id=2的数据，只要当前EntityManager没有关闭，从缓存中查询，不会查询数据库
    @Test
    public void testCache(){
        // 操作对象，就相当于操作数据库
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            // 使用id查询，将查询的对象，进行修改
            Customer customer1 = em.find(Customer.class, 2L);  // 查询数据库
            System.out.println(customer1);
            Customer customer2 = em.find(Customer.class, 2L);
            System.out.println(customer2);
            System.out.println(customer1 == customer2);// true，从缓存中获取数据
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * 延迟加载的问题：
         * ID查询
         *   * find方法：表示的是立即检索，只要调用find方法，马上查询数据库，返回的是一个真实的Customer对象
     *   *   * getReference方法：表示的是延迟检索，调用getReference方法的时候，不会马上查询数据库，而是返回一个代理对象，代理对象只初始化id的值，不会初始化其他的值
     *                           当调用除了id之外其他属性的时候，就会查询数据库，将查询的结果封装到代理对象中
     *
     *                           使用延迟加载的时候：
     *                                  * 确实能增加性能，不会查询数据库，只初始化id的字段的值
     *                                  * 当使用延迟加载的对象的时候，输出里面的属性，必须要保证在EntityManager对象（hibernate中叫做Session）关闭之前，要进行初始化，否则就会抛出懒加载异常。
     */
    @Test
    public void testLoadOne(){
        // 操作对象，就相当于操作数据库
        EntityManager em = null;
        EntityTransaction transaction = null;
        Customer customer = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            // 使用id查询，将查询的对象，进行修改
            customer = em.getReference(Customer.class, 2L);
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
        /**抛出异常：org.hibernate.LazyInitializationException: could not initialize proxy - no Session*/
        System.out.println(customer);
    }

    /**
     * 7.5.1查询全部
     * * 使用面向对象的方式操作数据库
     * * 操作实体--->操作数据库的表
     * * 操作实体的属性--->操作数据库表的字段
     * * JPQL语句：from Customer
     *             select c from Customer c
     *               能把查询的结果封装到Customer对象中。
     *   SQL语句：select * from cst_customer
     *              不能把查询的结果封装到Customer对象中的，而是返回Object[]。
     */
    @Test
    public void testFindAll(){
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            String jpql = "from Customer";
            Query query = em.createQuery(jpql);
            List<Customer> list = query.getResultList();
            for (Customer customer : list) {
                System.out.println(customer);
            }
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * 分页
     * query.setFirstResult()：表示当前页从第几条开始检索，默认是0,0表示第1条数据
       query.setMaxResults()：表示当前页最多显示的记录数
     */
    @Test
    public void testFindAllByPage(){
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            String jpql = "from Customer";
            Query query = em.createQuery(jpql);
            // 添加分页
            query.setFirstResult(4);
            query.setMaxResults(2);
            List<Customer> list = query.getResultList();
            for (Customer customer : list) {
                System.out.println(customer);
            }
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * 7.5.3条件查询
     */
    @Test
    public void testFindAllByCondition(){
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            String jpql = "from Customer where custName like ? and custSource = ?";
            Query query = em.createQuery(jpql);
            // 添加?的占位符，1表示第一个?
            query.setParameter(1,"%联%");
            query.setParameter(2,"互联网");
            List<Customer> list = query.getResultList();
            for (Customer customer : list) {
                System.out.println(customer);
            }
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }


    /**
     * 7.5.4排序查询
     *   * order by custId desc：降序
     *   * order by custId asc：升序
     */
    @Test
    public void testFindAllOrderBy(){
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            String jpql = "from Customer where custName like ? and custSource = ? order by custId desc";
            Query query = em.createQuery(jpql);
            // 添加?的占位符，1表示第一个?
            query.setParameter(1,"%联%");
            query.setParameter(2,"互联网");
            List<Customer> list = query.getResultList();
            for (Customer customer : list) {
                System.out.println(customer);
            }
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * 7.5.5统计查询
     */
    @Test
    public void testFindCount(){
        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = JpaUtils.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); // 开启事务
            String jpql = "select count(*) from Customer";
            Query query = em.createQuery(jpql);
            //List<Customer> list = query.getResultList(); // 返回的是一个集合
            Long count = (Long)query.getSingleResult(); // 返回的是单一的结果集
            System.out.println(count);
            transaction.commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            em.close();
        }
    }
}
