package com.itheima.domain;

/**
 * @ClassName Customer
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2018/10/12 9:15
 * @Version V1.0
 */

/**
 *
    CREATE TABLE cst_customer (
            cust_id BIGINT(32) NOT NULL AUTO_INCREMENT COMMENT '客户编号(主键)',
            cust_name VARCHAR(32) NOT NULL COMMENT '客户名称(公司名称)',
            cust_source VARCHAR(32) DEFAULT NULL COMMENT '客户信息来源',
            cust_industry VARCHAR(32) DEFAULT NULL COMMENT '客户所属行业',
            cust_level VARCHAR(32) DEFAULT NULL COMMENT '客户级别',
            cust_address VARCHAR(128) DEFAULT NULL COMMENT '客户联系地址',
            cust_phone VARCHAR(64) DEFAULT NULL COMMENT '客户联系电话',
            PRIMARY KEY (`cust_id`)
            ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */

import javax.persistence.*;

/**编写实体类和数据库表的映射配置
 * @Entity：放置到类上，表示该类和数据库表建立映射，默认的表名就是类的名称
 * @Table：放置到类上，表示定义表的名称
 *
 * 编写实体类的属性和数据库表的字段建立映射关系
 * @Column：放置到字段上，表示建立实体类的属性和数据库表的字段的映射，默认属性名就是表的列名
 * @Id：放置到字段上，表示该字段就是一个主键
 * @GeneratedValue(strategy = GenerationType.IDENTITY)：表示主键生成策略
 *      * GenerationType.IDENTITY：表示，mysql，数据库用什么策略（自增长），采用什么策略
 * */
@Entity
@Table(name = "cst_customer")
public class Customer {

    /**
     * 主键生成策略
     * JPA提供的四种标准用法为TABLE,SEQUENCE,IDENTITY,AUTO。
     *   * IDENTITY:主键由数据库自动生成（主要是自动增长型）
     *   * SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。（oracle）
     *   * AUTO：主键由程序控制（默认使用SEQUENCE）
     *   * TABLE：使用一个特定的数据库表格来保存主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "generationGenerator")
//    @SequenceGenerator(name = "generationGenerator",sequenceName = "seq_payment")

//    @GeneratedValue(strategy = GenerationType.AUTO)

//    @GeneratedValue(strategy = GenerationType.TABLE, generator="payablemoney_gen")
//    @TableGenerator(name = "payablemoney_gen",
//            table="tb_generator",
//            pkColumnName="gen_name",
//            valueColumnName="gen_value",
//            pkColumnValue="PAYABLEMOENY_PK123",
//            allocationSize=1
//    )
    @Column(name = "cust_id")
    private Long custId;
    @Column(name = "cust_name")
    private String custName;
    @Column(name = "cust_source")
    private String custSource;
    @Column(name = "cust_industry")
    private String custIndustry;
    @Column(name = "cust_level")
    private String custLevel;
    @Column(name = "cust_address")
    private String custAddress;
    @Column(name = "cust_phone")
    private String custPhone;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custPhone='" + custPhone + '\'' +
                '}';
    }
}
