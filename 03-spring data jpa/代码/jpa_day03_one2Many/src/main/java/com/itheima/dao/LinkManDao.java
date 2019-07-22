package com.itheima.dao;

import com.itheima.domain.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
 * JpaSpecificationExecutor<LinkMan>：封装动态的条件查询
 *     参数：指定查询的实体
 */
public interface LinkManDao extends JpaRepository<LinkMan,Long>,JpaSpecificationExecutor<LinkMan>{

    /** jpql的联合查询语句
     *  from LinkMan lm inner join lm.customer c where c.custName like ?：
     *          获取的是一个Object[]对象
     *          对象中第1个值LinkMan对象
     *          对象中第2个值Customer对象
     *          此时不能封装到List<LinkMan>中
     *  from LinkMan lm inner join fetch lm.customer c where c.custName like ?
     *      封装返回from后面的对象
     * @param custName
     * @return
     */
    @Query(value = "from LinkMan lm inner join fetch lm.customer c where c.custName like ?")
    List<LinkMan> findAllLinkManJoinCustomerByCustName(String custName);
}
