package com.itheima.domain;

/**
 * @ClassName User
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2018/10/15 11:21
 * @Version V1.0
 */

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * CREATE TABLE `sys_user` (
 `user_id` BIGINT(32) NOT NULL AUTO_INCREMENT COMMENT '用户id',
 `user_code` VARCHAR(32) NOT NULL COMMENT '用户账号',
 `user_name` VARCHAR(64) NOT NULL COMMENT '用户名称',
 `user_password` VARCHAR(32) NOT NULL COMMENT '用户密码',
 `user_state` CHAR(1) NOT NULL COMMENT '1:正常,0:暂停',
 PRIMARY KEY (`user_id`)
 ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
@Entity
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_code")
    private String userCode;

    @Column(name="user_name")
    private String userName;
    @Column(name="user_password")
    private String userPassword;
    @Column(name="user_state")
    private String userState;

    /**
     * CREATE TABLE `sys_user_role` (
         `sys_role_id` BIGINT(32) NOT NULL COMMENT '角色id',
         `sys_user_id` BIGINT(32) NOT NULL COMMENT '用户id',
         PRIMARY KEY (`sys_role_id`,`sys_user_id`),
         CONSTRAINT `FK_user_role_role_id` FOREIGN KEY (`sys_role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
         CONSTRAINT `FK_user_role_user_id` FOREIGN KEY (`sys_user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
         ) ENGINE=INNODB DEFAULT CHARSET=utf8;
     * @return

     @ManyToMany：建立多对多的关联关系
        * targetEntity：表示对端实体的字节码类型
     @JoinTable：表示关联的中间表
        * name:表示多对多关联的中间表的名称
     *  * joinColumns：当前对象所关联的中间表的外键
     *  * inverseJoinColumns：表示对端对象关联的中间表的外键
     */
    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL)
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "sys_user_id",referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name="sys_role_id",referencedColumnName = "role_id")})
    private Set<Role> roles = new HashSet<Role>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userState='" + userState + '\'' +
                '}';
    }
}
