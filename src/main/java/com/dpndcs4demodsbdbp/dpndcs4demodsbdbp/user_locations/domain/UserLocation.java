package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user_locations.domain;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.coordinate.domain.Coordinate;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain.Tenant;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
public class UserLocation {
    @EmbeddedId
    private TenantCoordinateId id;

    public UserLocation(Tenant tenant, Coordinate coordinate, String description) {
        this.tenant =  tenant;
        this.coordinate = coordinate;
        this.description = description;
        this.id = new TenantCoordinateId(tenant.getId(), coordinate.getId());
    }

    public UserLocation() {}


    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tenantId")
    private Tenant tenant;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinate_id", nullable = false)
    private Coordinate coordinate;
}
