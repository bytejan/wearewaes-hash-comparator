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
}
