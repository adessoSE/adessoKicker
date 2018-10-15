package de.adesso.kicker.tournament.singleelimination;

import de.adesso.kicker.team.Team;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BracketRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany
    private List<Team> row = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Team> getRow() {
        return row;
    }

    public void setRow(List<Team> row) {
        this.row = row;
    }
}
