package com.app.infra.repository.roles;

import com.app.infra.entity.roles.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    @Query(nativeQuery = true, //
            value = "select roles.* " +//
                    "from adm_roles roles " +//
                    "where roles.active = true ")
    Page<RoleEntity> findAllByActive(Pageable pageable);
}

