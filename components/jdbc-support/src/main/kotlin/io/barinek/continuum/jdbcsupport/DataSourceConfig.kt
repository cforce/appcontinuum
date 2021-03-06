package io.barinek.continuum.jdbcsupport

import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariDataSource

open class DataSourceConfig {
    fun createDataSource(name: String): HikariDataSource {
        val json = System.getenv("VCAP_SERVICES")
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = from(name, json)
        return dataSource
    }

    fun from(name: String, json: String): String? {
        val mapper = ObjectMapper()
        val root = mapper.readTree(json)
        val mysql = root.findValue(name)
        val credentials = mysql.findValue("credentials")
        return credentials.findValue("jdbcUrl").textValue()
    }
}