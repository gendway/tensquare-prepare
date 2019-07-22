package com.itheima.test;

import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.Role;
import com.itheima.domain.User;
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
public class TestManyToMany {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    //
    @Test
    public void createTable(){
        System.out.println("创建表");
    }


    /**
     * 4.6.1保存
     * 需求：保存用户，再保存一个客户，并建立用户和客户的关联关系（中间表）
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testSave(){
        User u = new User();
        u.setUserName("张三");
        u.setUserCode("zhangsan");
        u.setUserPassword("123");
        u.setUserState("1");

        Role r = new Role();
        r.setRoleName("Java程序员");
        r.setRoleMemo("高薪");
        // 建立关联关系（2端维护关联关系）（多对多，在开发中，哪个对象操作的频繁，就由谁来维护关联关系）
        u.getRoles().add(r);
        r.getUsers().add(u); // 当配置用户一端维护关联关系（用户是主控方），此时角色关联用户就是不能维护中间表外键），该行代码不起作用了，可以注释

        // 保存
        userDao.save(u);
        roleDao.save(r);
    }

    /**
     * 4.6.2级联保存
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testSave_1(){
        User u = new User();
        u.setUserName("张三");
        u.setUserCode("zhangsan");
        u.setUserPassword("123");
        u.setUserState("1");

        Role r = new Role();
        r.setRoleName("Java程序员");
        r.setRoleMemo("高薪");
        // 建立关联关系（2端维护关联关系）（多对多，在开发中，哪个对象操作的频繁，就由谁来维护关联关系）
        u.getRoles().add(r);// 1：起到外键维护的作用，2：级联
        //r.getUsers().add(u); // 当配置用户一端维护关联关系（用户是主控方），此时角色关联用户就是不能维护中间表外键），该行代码不起作用了，可以注释

        // 保存
        userDao.save(u);
        //roleDao.save(r);
    }

    /**
     * 4.6.3删除

     测试删除id为1的角色：不能删除，抛出异常，因为此时是用户一端维护关联关系，所以使用角色删除，如果中间表中存在数据，无法删除。
     测试删除id为1的用户：可以删除，因为此时是用户一端维护关联关系，所以使用用户删除，中间表存在数据，先删除中间表，再删除用户表。
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testDelete(){
        // roleDao.delete(1L);
        userDao.delete(1L);
    }

    /**
     * 4.6.4级联删除
     级联删除：删除操作
     1：在多对多的删除时，双向级联删除根本不能配置
     2：如果配了的话，如果数据之间有相互引用关系，可能会清空所有数据，所以应该禁用
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testDelete_2(){
        userDao.delete(3L);
    }
}
