package com.fvthree.ecommerce.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fvthree.ecommerce.auth.models.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users",
		uniqueConstraints = {
			@UniqueConstraint(columnNames="name"),	
			@UniqueConstraint(columnNames="email"),
})
public class User implements Serializable {

	private static final long serialVersionUID = 7435687342692361857L;

	@Id
	@SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@Column(name="user_id")
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="address", nullable=false, length=200)
	private String address;
	
	@Column(name="role", nullable=false)
	private int role;
	
    @Column(nullable = false, name="is_active", columnDefinition = "TINYINT(1)")
    private boolean isActive;
    
    @Column(nullable = false, name="is_admin", columnDefinition = "BOOLEAN")
    private Boolean isAdmin;
    
    @Column(nullable = false, name="is_not_locked", columnDefinition = "TINYINT(1)")
    private boolean isNotLocked;
    
    @Column(name = "is_verified", columnDefinition = "boolean default false", nullable = false)
	private boolean isVerified;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
	
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(updatable = false, name="date_created")
    private LocalDateTime dateCreated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name="last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
	public void setDateCreated() {
		this.dateCreated = LocalDateTime.now();
	}
    
    @PreUpdate
	public void setLastUpdated() {
		this.lastUpdated = LocalDateTime.now();
	}
}
