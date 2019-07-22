package com.itheima.dao;

import com.itheima.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName CustomerDao
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2018/10/13 9:21
 * @Version V1.0
 */

/**
 * Repository<T, ID extends Serializable>
 *     第一个参数：指定操作实体的类型
 *     第二个参数：指定操作实体的主键类型（该类型一定要实现Serializable）
 */
public interface CustomerDao extends JpaRepository<Customer,Long>{

    /**
     * @Query：表示支持JPQL语句
     *      * value：指定JPQL语句，当调用接口中的方法的时候，执行JPQL语句完成操作
     *              如果使用JPQL语句，将查询对象的结果封装到方法的返回值上
     *
     *              如果在JPQL语句中出现参数，在Dao定义的方法中参数指定，按照参数的顺序指定占位符?，第一个参数表示第一个?
     *              如果参数的顺序和?号的顺序不一致呢？此时可以使用?1,?2,?3（使用数字表示第几个参数）
     * @return
     */
    // 查询所有
    @Query(value = "from Customer")
    public List<Customer> queryAll();

    // 按照客户的名称的模糊查询
    @Query(value = "from Customer where custName like ?")
    public List<Customer> queryCustName(String custName);

    // 按照客户的名称的模糊查询 和按照客户的所属行业完全匹配查询
    @Query(value = "from Customer where custName like ?2 and custIndustry = ?1")
    public List<Customer> queryCustNameAndCustIndustry(String custIndustry,String custName);

    // 更新custName的字段
    @Query(value = "update Customer set custName=?2 where custId=?1")
    @Modifying
    public void updateCustName(Long id,String custName);



    // 使用sql语句
    // 查询所有
    /**
     * nativeQuery：默认值是false：false表示jpql语法
     *              true：true表示sql语法
     *
     *  当使用sql语句进行查询，将查询的结果通过映射关系将数据封装到Customer对象中
     *  如果 cst_customer表没有建立映射呢？（正常情况，都会建立关系）
     * @return
     */
//    @Query(value = "select * from cst_customer",nativeQuery = true)
//    public List<Customer> findAllSql();

    // 如果没有建立映射关系，必须返回List<Object[]>
    @Query(value = "select * from cst_customer",nativeQuery = true)
    public List<Object[]> findAllSql();

    // 按照名称模糊查询
    @Query(value = "select * from cst_customer where cust_name like ?",nativeQuery = true)
    public List<Object[]> findByCustNameLikeSql(String custName);

    // 按照名称模糊查询+按照客户所属行业完全匹配
    @Query(value = "select * from cst_customer where cust_name like ?1 and cust_industry = ?2",nativeQuery = true)
    public List<Object[]> findByCustNameLikeAndCustIndustrySql(String custName,String custIndustry);


    /**
     *  条件查询
     *  语法：查询方法以findBy开头
     *          * findBy + "字段的名称（首字母大写）"   ----生成的sql语句  where custName = ?
     *          * findBy + "字段的名称（首字母大写）" + "条件的关键字，例如like，isnull（首字母大写）"
     *          * findBy + "字段的名称（首字母大写）" + "条件的关键字，例如like，isnull（首字母大写）" + "连接的语法，例如and、or（首字母大写）"
     */
    // 按照客户名称的完全匹配查询
    public List<Customer> findByCustName(String custName);
    // 按照客户名称的模糊查询
    public List<Customer> findByCustNameLike(String custName);

    // 按照客户名称的模糊查询+按照客户所属行业完全匹配
    public List<Customer> findByCustNameLikeAndCustIndustry(String custName,String CustIndustry);
}
