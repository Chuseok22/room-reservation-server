package com.chuseok22.roomreservationserver.common.infrastructure.config

import com.chuseok22.roomreservationserver.common.infrastructure.properties.SpringDocProperties
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
  info = Info(
    title = "🎫 세종대학교 연습실 예약 🎫",
    description = """
            ### 🌐 Room Reservation 웹사이트 🌐 : https://booking.chuseok22.com
            [**웹사이트 바로가기**](https://booking.chuseok22.com)
            
            ### 💻 **GitHub 저장소**
            - **[백엔드 소스코드](https://github.com/Chuseok22/room-reservation-server)**
              백엔드 개발에 관심이 있다면 저장소를 방문해보세요.
            """,
    version = "1.0v"
  )
)
@Configuration
@EnableConfigurationProperties(SpringDocProperties::class)
class SwaggerConfig(
  private val properties: SpringDocProperties
) {

  @Bean
  fun openAPI(): OpenAPI {
    val apiKey = SecurityScheme()
      .type(SecurityScheme.Type.HTTP)
      .scheme("bearer")
      .bearerFormat("JWT")
      .`in`(SecurityScheme.In.HEADER)
      .name("Authorization")

    return OpenAPI()
      .components(Components().addSecuritySchemes("Bearer Token", apiKey))
      .addSecurityItem(SecurityRequirement().addList("Bearer Token"))
  }

  @Bean
  fun serverCustomizer(): OpenApiCustomizer {
    return OpenApiCustomizer { openApi ->
      properties.servers.forEach { server ->
        openApi.addServersItem(
          io.swagger.v3.oas.models.servers.Server()
            .url(server.url)
            .description(server.description)
        )
      }
    }
  }
}