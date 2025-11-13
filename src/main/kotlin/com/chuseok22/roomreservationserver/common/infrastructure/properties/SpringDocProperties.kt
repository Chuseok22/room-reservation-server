package com.chuseok22.roomreservationserver.common.infrastructure.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "springdoc")
data class SpringDocProperties(
  val servers: List<Server>
) {

  data class Server(
    val url: String,
    val description: String
  )
}