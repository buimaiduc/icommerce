package com.icommerce.app.shopping.user.service.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SHOPPING_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "EMAIL", length = 36, nullable = false, unique = true)
    private String email;

    @Column(name = "EXTERNAL_ID", length = 100, nullable = false, unique = true)
    private Long externalId;

    @Column(name = "NAME", length = 36)
    private String name;

    @Column(name = "DELETED")
    private Boolean deleted;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
