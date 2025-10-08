package com.app.infra.application.controller.roles;

import com.app.core.domain.role.Role;
import com.app.core.usecases.roles.RoleUseCase;
import com.app.infra.application.dto.roles.RoleDTO;
import com.app.infra.application.mapper.roles.RoleMapper;
import com.app.infra.application.request.roles.RoleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("adm/roles")
public class RoleController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    private final RoleMapper roleMapper;

    private final RoleUseCase roleUseCase;

    public RoleController(final RoleMapper roleMapper, final RoleUseCase roleUseCase) {
        this.roleMapper = roleMapper;
        this.roleUseCase = roleUseCase;
    }

    @PostMapping
    public ResponseEntity<RoleDTO> created(@RequestBody final RoleRequest json) {

        log.info("POST -> /adm/roles -> {}", json);

        final Role role = roleMapper.mapToRole(json);

        final Role roleCreated = roleUseCase.save(role);

        final RoleDTO dto = roleMapper.mapToDTO(roleCreated);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(@PathVariable final Long id, @RequestBody final RoleRequest json) {

        log.info("PUT -> /adm/roles -> {}, {}", id, json);

        final Role role = roleMapper.mapToRole(json, id);

        final Role useCreated = roleUseCase.save(role);

        final RoleDTO dto = roleMapper.mapToDTO(useCreated);

        return ResponseEntity.ok(dto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> get(@PathVariable final Long id) {

        log.info("GET -> /adm/roles -> {}", id);

        final RoleDTO dto = roleUseCase.findById(id)
                .map(roleMapper::mapToDTO)
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        return ResponseEntity.ok(dto);

    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAll(@RequestParam final int page, @RequestParam final int size) {

        log.info("GET -> /adm/roles -> {}, {}", page, size);

        final List<Role> roles = roleUseCase.findAllActive(page, size);

        final List<RoleDTO> dtos = roles
                .stream()
                .map(roleMapper::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);

    }

}
