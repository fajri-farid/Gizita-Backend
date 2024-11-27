package com.example.demo.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("GeneralUser")

public class GeneralUser extends Auser {

}
