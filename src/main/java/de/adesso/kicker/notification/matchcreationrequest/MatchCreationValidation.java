package de.adesso.kicker.notification.matchcreationrequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MatchCreationValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private short numVerified;


    public MatchCreationValidation() {
        this.numVerified = 0;
    }
}
