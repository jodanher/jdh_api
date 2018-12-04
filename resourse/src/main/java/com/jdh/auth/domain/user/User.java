package com.jdh.auth.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jdh.auth.domain.user.phone.Phone;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_user")
public class User<getPassword> extends AbstractPersistable<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "token", length = 1000)
    private String token;

    @Column(name = "created", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "CET")
    private LocalDate createdDate;

    @Column(name = "modified", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "CET")
    private LocalDate lastModifiedDate;

    @Column(name = "last_login")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "CET")
    private LocalDate lastLoginDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_user_phone", joinColumns = @JoinColumn(name = "user_id"))
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "CET")
    private List<Phone> phones;

    @PrePersist
    public void setCreatedDate() {
        this.createdDate = LocalDate.now();
        this.lastModifiedDate = LocalDate.now();
    }

    @PreUpdate
    public void setLastModifiedDate() {
        this.lastModifiedDate = LocalDate.now();
    }

}
