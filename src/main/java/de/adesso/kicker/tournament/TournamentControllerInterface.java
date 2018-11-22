package de.adesso.kicker.tournament;

import org.springframework.web.servlet.ModelAndView;

public interface TournamentControllerInterface<T> {

    Class appliesTo();

    ModelAndView getPage(T tournament);

    ModelAndView postJoinTournament(T tournament, String id);

    ModelAndView getBracket(T tournament);
}
