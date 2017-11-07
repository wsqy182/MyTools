package com.xq.tool.core.utils;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StringType;

import java.sql.Types;

/**
 * url:http://blog.csdn.net/xd195666916/article/details/5419316
 */
public class MySQLServerDialect extends SQLServer2012Dialect {
    public MySQLServerDialect() {
        registerHibernateType(Types.NVARCHAR, StringType.INSTANCE.getName());
    }
}