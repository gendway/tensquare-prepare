package com.itheima.utils;

/**
 * @ClassName JpaUtils
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2018/10/12 10:59
 * @Version V1.0
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 1：EntityManagerFactory：加载persistence.xml文件，每次线程操作都去加载，保证EntityManagerFactory类被加载一次（进程级别）
 * 2：EntityManager：保证每个线程使用一个EntityManager对象（多个），用它来操作数据库
 */
public class JpaUtils {

    private static EntityManagerFactory entityManagerFactory;

    static{
        entityManagerFactory = Persistence.createEntityManagerFactory("customerJpa");
    }

    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
