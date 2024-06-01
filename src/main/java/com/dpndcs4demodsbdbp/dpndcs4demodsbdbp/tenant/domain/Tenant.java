package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.tenant.domain;



import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.ride.domain.Ride;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.transaction.domain.Transaction;
import com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.user.domain.User;
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



    //Relación de un tenant a varias transacciones
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    //Relación de un tenant a muchos viajes
    @OneToMany(mappedBy = "tenant")
    private List<Ride> rides = new ArrayList<>();

}
