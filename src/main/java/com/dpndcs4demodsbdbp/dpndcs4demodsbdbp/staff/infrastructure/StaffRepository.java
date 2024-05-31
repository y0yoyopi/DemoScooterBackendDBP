package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.infrastructure;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.staff.domain.Staff;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Driver;
import java.util.List;

@Transactional
@Repository
public interface StaffRepository extends UserRepository<Staff> {
}