package com.stacktradingengine.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stacktradingengine.dto.UserRequestDTO;
import com.stacktradingengine.dto.UserResponseDTO;
import com.stacktradingengine.entity.User;
import com.stacktradingengine.enums.Role;
import com.stacktradingengine.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository repository;
	private final PasswordEncoder encoder;

	public UserService(UserRepository repository, PasswordEncoder encoder) {

		this.repository = repository;
		this.encoder = encoder;
	}

	public UserResponseDTO register(UserRequestDTO dto) {

		User user = new User();

		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(encoder.encode(dto.getPassword()));

		user.setBalance(dto.getBalance());

		user.setRole(Role.USER);

		User savedUser = repository.save(user);

        UserResponseDTO response =
                new UserResponseDTO();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setBalance(savedUser.getBalance());
        response.setRole(
                savedUser.getRole().name()
        );

        return response;
    }

	public List<User> getAll() {

		return repository.findAll();
	}
}
