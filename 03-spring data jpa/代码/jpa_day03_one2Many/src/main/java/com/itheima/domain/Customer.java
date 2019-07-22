package com.itheima.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName Customer
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2018/10/13 9:10
 * @Version V1.0
 */
@Entity //声明实体类
@Table(name="cst_customer") //建立实体类和表的映射关系
public class Customer {

    @Id//声明当前私有属性为主键
    @GeneratedValue(strategy= GenerationType.IDENTITY) //配置主键的生成策略
    @Column(name="cust_id") //指定和表中cust_id字段的映射关系
    private Long custId;

    @Column(name="cust_name") //指定和表中cust_name字段的映射关系
    private String custName;

    @Column(name="cust_source")//指定和表中cust_source字段的映射关系
    private String custSource;

    @Column(name="cust_industry")//指定和表中cust_industry字段的映射关系
    private String custIndustry;

    @Column(name="cust_level")//指定和表中cust_level字段的映射关系
    private String custLevel;

    @Column(name="cust_address")//指定和表中cust_address字段的映射关系
    private String custAddress;

    @Column(name="cust_phone")//指定和表中cust_phone字段的映射关系
    private String custPhone;

    // 在一对多（多对多）的映射中（一的一端应该是一个集合（List【有序】、Set【无序】性能更高））
    /**
     * @OneToMany:表示一个一对多的关系
     *  * targetEntity：关联对象的目标的实体
     * @JoinColumn:表示关联的外键列
     *  * name：外键列的名称
     *  * mappedBy：映射到对端的关联属性上，此时就表示由对端维护关联关系，此时该对象放弃了关联关系（如果是一对多的案例，由多的一端维护关联关系有助于程序的应用）
     *  * cascade：表示级联
     *          CascadeType.PERSIST：表示级联保存，当保存客户的对象的时候，客户也关联了联系人，那么隐式执行save操作，将联系人也保存到数据库中
     *          CascadeType.REMOVE：表示级联删除，当删除客户的时候，级联删除跟客户关联的所有联系人的信息
     *          CascadeType.ALL：具有级联的所有特性
     *  * fetch：表示是立即检索还是延迟检索
     *     fetch = FetchType.EAGER：立即检索，查询客户，同时将客户所关联的联系人也查询出来
     */
    @OneToMany(targetEntity = LinkMan.class,mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    //@JoinColumn(name = "lkm_cust_id",referencedColumnName = "cust_id")
    private Set<LinkMan> linkMans = new HashSet<LinkMan>();

    public Set<LinkMan> getLinkMans() {
        return linkMans;
    }

    public void setLinkMans(Set<LinkMan> linkMans) {
        this.linkMans = linkMans;
    }

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
