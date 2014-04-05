dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "root"
    password = ""
    dialect = org.hibernate.dialect.MySQL5InnoDBDialect
    properties { // like http://grails.org/doc/2.0.3/guide/conf.html#dataSource
        maxActive = 60
        maxIdle = 40
        minIdle = 10
        initialSize = 10
        minEvictableIdleTimeMillis = 60000
        timeBetweenEvictionRunsMillis = 60000
        testOnBorrow = true
        testOnReturn = true
        testWhileIdle = true
        maxWait = 10000
        validationQuery = "SELECT 1"
        //validationInterval = 30000
        //numTestsPerEvictionRun = 3
    }
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost/slow?autoReconnect=true"
            //dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            //url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
    }
}
