package io.kakaopay.house.finance.repository;

import io.kakaopay.house.finance.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Byungwook lee on 2019-08-11
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(final String id);
}
