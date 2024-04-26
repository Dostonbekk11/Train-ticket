package org.example.railwayticketbooking.repository;

import org.example.railwayticketbooking.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByName(String name);
}