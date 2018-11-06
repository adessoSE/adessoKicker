package de.adesso.kicker.tournament.singleelimination;

import de.adesso.kicker.tournament.SubClassInterface;

public class SingleEliminationSubclass implements SubClassInterface {

    @Override
    public Class<SingleElimination> appliesTo() {
        return SingleElimination.class;
    }
}
