package de.adesso.kicker.tournament;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.adesso.kicker.match.Match;
import de.adesso.kicker.user.User;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tournamentId;

    @NotNull
    @Size(min = 2, max = 30)
    private String tournamentName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    private Date endDate;
    private String description;
    private String format;
    private boolean finished;

    @ManyToOne(targetEntity = Match.class)
    private List<Match> matches;

    @ManyToMany(targetEntity = User.class)
    private List<User> players;

    public Tournament() {
    }

    public Tournament(String tournamentName) {

        this.tournamentName = tournamentName;
        this.startDate = null;
        this.matches = new ArrayList<>();
        this.players = new ArrayList<>();
        this.finished = false;
        this.description = null;
    }

    public String getGermanDate() {
        DateFormat df = new SimpleDateFormat("EEEEE, dd. MMMMM yyyy");
        String germanDate = df.format(startDate);
        return germanDate;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Tournament{" + "tournamentId=" + tournamentId + ", tournamentName='" + tournamentName + '\''
                + ", startDate=" + startDate + ", endDate=" + endDate + ", description='" + description + '\''
                + ", format='" + format + '\'' + ", finished=" + finished + ", matches=" + matches + ", players="
                + players + '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
