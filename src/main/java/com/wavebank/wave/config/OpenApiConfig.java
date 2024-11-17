package com.wavebank.wave.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(
        name="Bartosz",
        url = "https://github.com/Bulb1"
        ),
        description = "OpenApi documentation for Wave",
        title = "OpenApi specification",
        version = "1.0",
        license = @License(
                name = "License name",
                url =""
        ),
        termsOfService = "Terms of service"
    ),
        servers = {
                @Server(
                description = "Local ENV",
                url = "http://localhost:9191"
                )
//uncomment when JWT added
//        }//,
//        //global security scheme
//        security = {
//                @SecurityRequirement(
//                        name = "bearerAuth"
//                )
        }
)
/*
To use a security scheme for each controller, add @SecurityRequirement(name = "bearerAuth") annotation to a controller
 */
//uncomment when JWT added
//@SecurityScheme(
//        name = "bearerAuth",
//        description = "JWT auth description",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
public class OpenApiConfig {


}
