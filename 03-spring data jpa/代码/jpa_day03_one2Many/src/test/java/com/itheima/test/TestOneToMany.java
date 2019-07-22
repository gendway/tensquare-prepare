package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.LinkManDao;
import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
public class TestOneToMany {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    LinkManDao linkManDao;

    /**
     * 需求：新增一个客户，同时新增一个联系人，并将联系人关联客户
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testSave(){
        // 新增一个客户
        Customer c = new Customer();
        c.setCustName("阿里巴巴");
        c.setCustSource("互联网");
        c.setCustIndustry("电商");
        c.setCustLevel("VID会员");
        c.setCustAddress("洪浪北");
        c.setCustPhone("0755-32343234");
        // 新增一个联系人
        LinkMan lm = new LinkMan();
        lm.setLkmName("马云");
        lm.setLkmGender("男");
        lm.setLkmPosition("总经理");
        lm.setLkmEmail("mayun@123.com");
        lm.setLkmMobile("13923432222");
        lm.setLkmPhone("0755-88882222");
        lm.setLkmMemo("公司集团负责人");

        // 客户关联联系人（2端建立关联关系）
        //c.getLinkMans().add(lm); // c对象此时不再维护外键的关联关系，所以该代码可以注释的
        lm.setCustomer(c);
        // 保存
        customerDao.save(c);
        linkManDao.save(lm);

    }

    /**
     * 需求：新增一个客户，同时新增一个联系人，并将联系人关联客户（级联保存-更新）
     * 只保存客户、不保存联系人
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testSave_1(){
        // 新增一个客户
        Customer c = new Customer();
        c.setCustName("阿里巴巴1");
        c.setCustSource("互联网");
        c.setCustIndustry("电商");
        c.setCustLevel("VID会员");
        c.setCustAddress("洪浪北");
        c.setCustPhone("0755-32343234");
        // 新增一个联系人
        LinkMan lm = new LinkMan();
        lm.setLkmName("马云1");
        lm.setLkmGender("男");
        lm.setLkmPosition("总经理");
        lm.setLkmEmail("mayun@123.com");
        lm.setLkmMobile("13923432222");
        lm.setLkmPhone("0755-88882222");
        lm.setLkmMemo("公司集团负责人");

        // 客户关联联系人（2端建立关联关系）
        c.getLinkMans().add(lm); // c对象此时不再维护外键的关联关系，起到了级联的作用
        lm.setCustomer(c);// 起到外键维护权的作用（外键有值）
        // 保存
        customerDao.save(c);
        //linkManDao.save(lm);

    }

    /**
     * 3.6.4级联删除
     * 需求：删除客户
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testDelete(){
        Customer customer = customerDao.findOne(13L);
//        linkManDao.delete(linkMan);
        customerDao.delete(customer);
    }
}
