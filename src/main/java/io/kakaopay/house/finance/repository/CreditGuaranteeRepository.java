package io.kakaopay.house.finance.repository;

import io.kakaopay.house.finance.model.CreditGuarantee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Byungwook lee on 2019-08-10
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public interface CreditGuaranteeRepository extends JpaRepository<CreditGuarantee, Long> {
	List<CreditGuarantee> findAllByYearGreaterThanEqual(final Integer from);

	List<CreditGuarantee> findAllByYearLessThanEqual(final Integer to);

	List<CreditGuarantee> findAllByYearGreaterThanEqualAndYearLessThanEqual(final Integer from, final Integer to);

	List<CreditGuarantee> findAllByYear(final Integer year);
}
