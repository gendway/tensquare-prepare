package com.itheima.domain;

import javax.persistence.*;

/**
 * @ClassName LinkMan
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2018/10/15 9:47
 * @Version V1.0
 */

/**
 *   CREATE TABLE cst_linkman (
         lkm_id BIGINT(32) NOT NULL AUTO_INCREMENT COMMENT '联系人编号(主键)',
         lkm_name VARCHAR(16) DEFAULT NULL COMMENT '联系人姓名',
         lkm_gender CHAR(1) DEFAULT NULL COMMENT '联系人性别',
         lkm_phone VARCHAR(16) DEFAULT NULL COMMENT '联系人办公电话',
         lkm_mobile VARCHAR(16) DEFAULT NULL COMMENT '联系人手机',
         lkm_email VARCHAR(64) DEFAULT NULL COMMENT '联系人邮箱',
         lkm_position VARCHAR(16) DEFAULT NULL COMMENT '联系人职位',
         lkm_memo VARCHAR(512) DEFAULT NULL COMMENT '联系人备注',
         lkm_cust_id BIGINT(32) NOT NULL COMMENT '客户id(外键)',
         PRIMARY KEY (`lkm_id`),
         KEY `FK_cst_linkman_lkm_cust_id` (`lkm_cust_id`),
         CONSTRAINT `FK_cst_linkman_lkm_cust_id` FOREIGN KEY (`lkm_cust_id`) REFERENCES `cst_customer` (`cust_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
         ) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
 */
@Entity
@Table(name = "cst_linkman")
public class LinkMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lkm_id")
    private Long lkmId; //联系人主键ID

    @Column(name = "lkm_name")
    private String lkmName; // 联系人姓名

    @Column(name = "lkm_gender")
    private String lkmGender;//联系人性别
    @Column(name = "lkm_phone")
    private String lkmPhone;//联系人办公电话
    @Column(name = "lkm_mobile")
    private String lkmMobile;//联系人手机
    @Column(name = "lkm_email")
    private String lkmEmail;//联系人邮箱
    @Column(name = "lkm_position")
    private String lkmPosition;//联系人职位
    @Column(name = "lkm_memo")
    private String lkmMemo;//联系人备注

    // 在多对一（一对一）的映射中（多的一端应该是一个对象）
    /**
     * @ManyToOne：表示多对一的映射
     *    * targetEntity：表示关联对象的目标实体
     *
     *    注意：如果2端都放置@JoinColumn(name = "lkm_cust_id",referencedColumnName = "cust_id")
     *          表示2端都能维护外键
     */
    @ManyToOne(targetEntity = Customer.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "lkm_cust_id",referencedColumnName = "cust_id")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getLkmId() {
        return lkmId;
    }

    public void setLkmId(Long lkmId) {
        this.lkmId = lkmId;
    }

    public String getLkmName() {
        return lkmName;
    }

    public void setLkmName(String lkmName) {
        this.lkmName = lkmName;
    }

    public String getLkmGender() {
        return lkmGender;
    }

    public void setLkmGender(String lkmGender) {
        this.lkmGender = lkmGender;
    }

    public String getLkmPhone() {
        return lkmPhone;
    }

    public void setLkmPhone(String lkmPhone) {
        this.lkmPhone = lkmPhone;
    }

    public String getLkmMobile() {
        return lkmMobile;
    }

    public void setLkmMobile(String lkmMobile) {
        this.lkmMobile = lkmMobile;
    }

    public String getLkmEmail() {
        return lkmEmail;
    }

    public void setLkmEmail(String lkmEmail) {
        this.lkmEmail = lkmEmail;
    }

    public String getLkmPosition() {
        return lkmPosition;
    }

    public void setLkmPosition(String lkmPosition) {
        this.lkmPosition = lkmPosition;
    }

    public String getLkmMemo() {
        return lkmMemo;
    }

    public void setLkmMemo(String lkmMemo) {
        this.lkmMemo = lkmMemo;
    }

    @Override
    public String toString() {
        return "LinkMan{" +
                "lkmId=" + lkmId +
                ", lkmName='" + lkmName + '\'' +
                ", lkmGender='" + lkmGender + '\'' +
                ", lkmPhone='" + lkmPhone + '\'' +
                ", lkmMobile='" + lkmMobile + '\'' +
                ", lkmEmail='" + lkmEmail + '\'' +
                ", lkmPosition='" + lkmPosition + '\'' +
                ", lkmMemo='" + lkmMemo + '\'' +
                '}';
    }
}
