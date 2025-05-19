package com.FragranceCove.model;

public class RoleModel {
    private Integer roleId;
    private String roleName;
    private String description;

    // Default constructor (no-argument constructor)
    public RoleModel() {
    }

    // Constructor with fields
    public RoleModel(Integer roleId, String roleName, String description) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
    }
    
    public RoleModel(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
     
    }
    
    
    

    // Getter methods
    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    // Setter methods
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
               "roleId=" + roleId +
               ", roleName='" + roleName + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
}

