package com.wearewaes.hashcomparator.domain;

import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "hash")
public class Hash  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 300)
    @Column(length = 300, nullable = false)
    private String hashVersion;

    @NotNull
    @Size(max = 300)
    @Column(length = 300, nullable = false)
    private String hash;

    @NotNull
    @Column(length = 300, nullable = false)
    private String position;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashVersion() {
        return hashVersion;
    }

    public Hash setHashVersion(String hashVersion) {
        this.hashVersion = hashVersion;
        return this;
    }

    public String getHash() {
        return hash;
    }

    public Hash setHash(String hash) {
        this.hash = hash;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public Hash setPosition(String position) {
        this.position = position;
        return this;
    }
}
