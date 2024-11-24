package com.wavebank.wave.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User")

public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Retrieve users",
            description = "Fetches a list of all users.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Request successful"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized or invalid token")
            }
    )

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }
}
