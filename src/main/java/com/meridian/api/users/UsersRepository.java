package com.meridian.api.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Users, Long> {
}
