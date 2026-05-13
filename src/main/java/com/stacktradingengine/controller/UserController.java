package com.stacktradingengine.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.stacktradingengine.dto.UserRequestDTO;
import com.stacktradingengine.dto.UserResponseDTO;
import com.stacktradingengine.entity.User;
import com.stacktradingengine.service.UserService;

@RestController
@RequestMapping("/users")

public class UserController {

	private final UserService service;

	public UserController(UserService service) {

		this.service = service;
	}

	@PostMapping("/register")
	public UserResponseDTO register(@RequestBody UserRequestDTO dto) {

		return service.register(dto);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllUsers")
	public List<User> getAll() {

		return service.getAll();
	}
}