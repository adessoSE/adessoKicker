package de.adesso.kicker.notification.matchcreationrequest;

import javax.persistence.*;

@Entity
public class MatchCreationValidation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private short numVerified;

    public MatchCreationValidation() {
        this.numVerified = 0;
    }

    public long getId(){
        return this.id;
    }

    public short getNumVerified(){
        return this.numVerified;
    }

    public void increaseNumVerified(){
        this.numVerified ++;
    }
}
