package com.itheima.domain;

/**
 * @ClassName Role
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
 * CREATE TABLE `sys_role` (
     `role_id` BIGINT(32) NOT NULL AUTO_INCREMENT,
     `role_name` VARCHAR(32) NOT NULL COMMENT '角色名称',
     `role_memo` VARCHAR(128) DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`role_id`)
     ) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

 */
@Entity
@Table(name = "sys_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_memo")
    private String roleMemo;

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
     *  mappedBy = "roles"：表示角色一端放弃了关联关系，由用户一端维护中间表
     */
    @ManyToMany(targetEntity = User.class,mappedBy = "roles")
//    @JoinTable(name = "sys_user_role",
//            joinColumns = {@JoinColumn(name = "sys_role_id",referencedColumnName = "role_id")},
//            inverseJoinColumns = {@JoinColumn(name="sys_user_id",referencedColumnName = "user_id")})
    private Set<User> users = new HashSet<User>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleMemo() {
        return roleMemo;
    }

    public void setRoleMemo(String roleMemo) {
        this.roleMemo = roleMemo;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleMemo='" + roleMemo + '\'' +
                '}';
    }
}
