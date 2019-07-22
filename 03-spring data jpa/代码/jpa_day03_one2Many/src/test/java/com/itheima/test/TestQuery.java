package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.LinkManDao;
import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

/**
 * @ClassName TestQuery
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2018/10/15 12:20
 * @Version V1.0
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestQuery {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    LinkManDao linkManDao;

    /**
     * 5.1对象导航查询（常用）
     * 需求：查询16号客户，16号客户所对应的联系人
     */
    @Test
    //@Transactional
    public void TestQueryObject(){
        Customer c = customerDao.findOne(16L);
        System.out.println(c.getCustName()+"   "+c.getCustAddress());
        Set<LinkMan> linkMans = c.getLinkMans();
        for (LinkMan linkMan : linkMans) {
            System.out.println(linkMan.getLkmName()+"    "+linkMan.getLkmGender());
        }
    }

    /**
     * 2：查询一个8号联系人，获取该联系人的所有客户
     */
    @Test
    @Transactional
    public void TestQueryObject1(){
        LinkMan linkMan = linkManDao.findOne(8L);
        System.out.println(linkMan.getLkmName()+"   "+linkMan.getLkmGender());
        Customer customer = linkMan.getCustomer();
        System.out.println(customer.getCustName()+"    "+customer.getCustAddress());
    }

    /**
     * 5.2使用Specification查询
     * 需求：使用联合查询，按照客户名称查询(custName)，返回联系人的集合
     */
    @Test
    @Transactional
    public void TestSpcification(){
        Specification<LinkMan> specification = new Specification() {
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Join<LinkMan,Customer> join = root.join("customer", JoinType.INNER);
                Predicate p = cb.like(join.get("custName").as(String.class),"%巴巴%");
                return p;
            }
        };
        List<LinkMan> list = linkManDao.findAll(specification);
        for (LinkMan linkMan : list) {
            System.out.println(linkMan.getLkmName()+"   "+linkMan.getLkmGender());
            System.out.println(linkMan.getCustomer().getCustName()+"    "+linkMan.getCustomer().getCustAddress());
        }
    }

    /**
     * 5.3使用@Query注解
     * 需求：使用联合查询，按照客户名称查询(custName)，返回联系人的集合
     */
    @Test
    @Transactional
    public void TestQuery(){
        String custName = "%巴巴%";
        List<LinkMan> list = linkManDao.findAllLinkManJoinCustomerByCustName(custName);
        for (LinkMan linkMan : list) {
            System.out.println(linkMan.getLkmName()+"   "+linkMan.getLkmGender());
            System.out.println(linkMan.getCustomer().getCustName()+"    "+linkMan.getCustomer().getCustAddress());
        }
    }
}