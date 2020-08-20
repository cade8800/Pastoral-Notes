package com.graze.pastoral.notes.domain.util;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-07-19 1:59
 */

import com.graze.pastoral.notes.domain.entity.BaseEntity;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/**
 * 自定义UUID生成器，使得保存实体类时可以在保留主键生成策略的情况下自定义表的主键
 */
public class CustomUUIDGenerator extends UUIDGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (object == null) {
            throw new HibernateException(new NullPointerException());
        }
        if ((((BaseEntity) object).getId()) == null) {
            return super.generate(session, object);
        } else {
            return ((BaseEntity) object).getId();
        }
    }

}
