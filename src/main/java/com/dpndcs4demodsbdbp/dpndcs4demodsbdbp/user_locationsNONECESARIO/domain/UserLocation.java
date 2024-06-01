package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user_locationsNONECESARIO.domain;

/*
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.coordinateNONECESARIO.domain.Coordinate;
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

    public UserLocation(Tenant tenant, Coordinate coordinateNONECESARIO, String description) {
        this.tenant =  tenant;
        this.coordinateNONECESARIO = coordinateNONECESARIO;
        this.description = description;
        this.id = new TenantCoordinateId(tenant.getId(), coordinateNONECESARIO.getId());
    }

    public UserLocation() {}


    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tenantId")
    private Tenant tenant;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinate_id", nullable = false)
    private Coordinate coordinateNONECESARIO;
}
*/