package com.nafisulbari.eib.Model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;


@Entity
@Getter
@Setter
@ToString
public class PoliceStation extends User{

    private String address;



}
