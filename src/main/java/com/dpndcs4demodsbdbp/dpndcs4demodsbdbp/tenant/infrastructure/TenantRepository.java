package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.infrastructure;

import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface TenantRepository extends UserRepository<Tenant> {
}
