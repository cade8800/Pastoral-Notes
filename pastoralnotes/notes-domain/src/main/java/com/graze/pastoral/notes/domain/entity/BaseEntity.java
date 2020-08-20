package com.graze.pastoral.notes.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/17 17:36
 */

@MappedSuperclass
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -441912888116111675L;
    @Id
    @GenericGenerator(
            name = "uuid2",
            strategy = "com.graze.pastoral.notes.domain.util.CustomUUIDGenerator",
            parameters = {@org.hibernate.annotations.Parameter(
                    name = "uuid_gen_strategy_class",
                    value = "org.hibernate.id.uuid.CustomVersionOneStrategy")
            })
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", nullable = false, unique = true, length = 36)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "create_user_id", nullable = true, length = 36)
    @Type(type = "uuid-char")
    private UUID createUserId;

    @Column(name = "create_time", nullable = true)
    private Timestamp createTime = Timestamp.from(ZonedDateTime.now().toInstant());// new Date();

    @Column(name = "update_user_id", nullable = true, length = 36)
    @Type(type = "uuid-char")
    private UUID updateUserId;

    @Column(name = "update_time", nullable = true)
    private Timestamp updateTime = Timestamp.from(ZonedDateTime.now().toInstant());// new Date();

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}

