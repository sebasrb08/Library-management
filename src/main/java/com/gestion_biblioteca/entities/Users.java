package com.gestion_biblioteca.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;

    @JsonIgnore
    @Column(name = "is_enabled")
    private boolean isEnabled;
    
    @JsonIgnore
    @Column(name = "account_no_expired")
    private boolean accountNoExpired;

    @JsonIgnore
    @Column(name = "account_no_locked")
    private boolean accountNoLocked;
    
    @JsonIgnore
    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL )
    @JoinTable(name = "user_role",joinColumns = @JoinColumn (name= "id_user"),inverseJoinColumns = @JoinColumn(name= "id_role"))
    private Set<Role> role;

}
