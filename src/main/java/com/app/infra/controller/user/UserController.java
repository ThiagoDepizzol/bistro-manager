package com.app.infra.controller.user;

import com.app.core.domain.user.User;
import com.app.core.usecases.user.UserUseCase;
import com.app.infra.controller.user.dto.UserDTO;
import com.app.infra.controller.user.json.UserJson;
import com.app.infra.controller.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("usr/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserMapper userMapper;

    private final UserUseCase userUseCase;

    public UserController(final UserMapper userMapper, final UserUseCase userUseCase) {
        this.userMapper = userMapper;
        this.userUseCase = userUseCase;
    }

    @PostMapping
    public ResponseEntity<UserDTO> created(@RequestBody final UserJson json) {

        log.info("POST -> /usr/users -> {}", json);

        final User user = userMapper.mapToUser(json);

        final User useCreated = userUseCase.save(user);

        final UserDTO dto = userMapper.mapToDTO(useCreated);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable final Long id, @RequestBody final UserJson json) {

        log.info("PUT -> /usr/users -> {}, {}", id, json);

        final User user = userMapper.mapToUser(json, id);

        final User useCreated = userUseCase.save(user);

        final UserDTO dto = userMapper.mapToDTO(useCreated);

        return ResponseEntity.ok(dto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable final Long id) {

        log.info("GET -> /usr/users -> {}", id);

        final UserDTO dto = userUseCase.findById(id)
                .map(userMapper::mapToDTO)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        return ResponseEntity.ok(dto);

    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(@RequestParam final int page, @RequestParam final int size) {

        log.info("GET -> /usr/users -> {}, {}", page, size);

        final List<User> users = userUseCase.findAllActive(page, size);

        final List<UserDTO> dtos = users
                .stream()
                .map(userMapper::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);

    }

}
