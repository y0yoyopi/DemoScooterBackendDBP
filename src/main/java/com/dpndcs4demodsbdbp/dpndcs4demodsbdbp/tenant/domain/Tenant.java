package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain;


import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain.Transaction;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain.User;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user_locations.domain.UserLocation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tenant extends User {

    //Relación de un tenat a varias coordenadas
    @OneToMany(mappedBy = "tenant",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserLocation> coordinates = new ArrayList<>();

    //Relación de un tenant a varias transacciones
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

}
