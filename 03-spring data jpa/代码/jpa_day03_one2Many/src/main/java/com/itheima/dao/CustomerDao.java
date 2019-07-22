package com.itheima.dao;

import com.itheima.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
 * JpaSpecificationExecutor<Customer>：封装动态的条件查询
 *     参数：指定查询的实体
 */
public interface CustomerDao extends JpaRepository<Customer,Long>,JpaSpecificationExecutor<Customer>{

}
