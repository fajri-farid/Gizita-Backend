package com.example.demo.Entity;
<<<<<<< HEAD

public class AhliGizi {
=======
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("AhliGizi")

public class AhliGizi extends Auser {
>>>>>>> 3a0af60490d3f1b1e3818e4644e921c013f80c15

}
