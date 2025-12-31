package com.mindteck.models_cas;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindteck.common.constants.Enum.UserSubType;
import com.mindteck.common.constants.Enum.UserType;
import com.mindteck.common.models.AbstractModel;
import com.mindteck.common.utils.WebUtils;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_user")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "position")
    private String position;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "report_access")
    private Integer reportAccess;

    @Column(name = "access_right")
    private Integer accessRight;

    @Column(name = "active")
    private Integer active;

    @Column(name = "bio")
    private String bio;

    @Column(name = "parent_inst")
    private Long parentInst;

    @Column(name = "created_ip")
    private String createdIp;

    @Column(name = "updated_ip")
    private String updatedIp;

    @Column(name = "created_app_id")
    private Long createdAppId;

    @Column(name = "updated_app_id")
    private Long updatedAppId;

    @Column(name = "created_time")
    public Long createdTime;

    @Column(name = "last_updated_time")
    public Long lastUpdatedTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    public void prePersist() {
        createdTime = System.currentTimeMillis();
        lastUpdatedTime = System.currentTimeMillis();
        createdIp = WebUtils.getClientIpAddress();
        updatedIp = WebUtils.getClientIpAddress();
        createdAppId = WebUtils.getAppId();
        updatedAppId = WebUtils.getAppId();

    }

    public Set<Long> getRoleIds() {
        if (this.roles != null) {
            return this.roles.stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
    @Transient
    public Set<Map<String, Object>> getRoleIdNamePairs() {
        if (this.roles != null) {
            return this.roles.stream()
                    .map(role -> {
                        Map<String, Object> pair = new HashMap<>();
                        pair.put("userType", role.getUserType());
                        pair.put("subType", role.getSubType());
                        pair.put("roleName",role.getUserType() == UserType.ILEP_MEMBER.getCode() && role.getSubType() == UserSubType.ADMIN.getCode() ? "PANEL_MEMBER" : role.getName());
                        return pair;
                    })
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }


    @PreUpdate
    public void preUpdate() {
        lastUpdatedTime = System.currentTimeMillis();
        updatedIp = WebUtils.getClientIpAddress();
        updatedAppId = WebUtils.getAppId();
    }


    @Transient
    public void appUpdateSave() {
        lastUpdatedTime = System.currentTimeMillis();
    }

}
