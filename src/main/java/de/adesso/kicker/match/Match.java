package de.adesso.kicker.match;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.thymeleaf.expression.Objects;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long matchId;

    @NotNull(message = "Bitte ein Datum wählen.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull(message = "Bitte eine Uhrzeit wählen.")
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    @Nullable
    private Date time;

    private String kicker;

    public Match() {
    }

    public String getGermanDate() {
        DateFormat df = new SimpleDateFormat("EEEEE, dd. MMMMM yyyy");
        String germanDate = df.format(date);
        return germanDate;
    }

    public Match(Date date, Date time, String kicker) {

        this.date = date;
        this.time = time;
        this.kicker = kicker;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Match{" + "matchId=" + matchId + ", date=" + date + ", time=" + time + ", kicker='" + kicker + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return matchId == match.matchId && Objects.equals(date, match.date) && Objects.equals(time, match.time)
                && Objects.equals(kicker, match.kicker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, date, time, kicker);
    }
}

