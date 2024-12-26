package org.kamsystem.leads.model;

import lombok.Getter;

@Getter
public enum LeadStatus {

    // Initial stage: Lead is newly created and hasn't been engaged yet
    NEW("New lead, not yet contacted"),

    // Lead is in the process of being contacted and worked on
    IN_PROGRESS("Lead is being worked on"),

    // Lead has shown interest and a quote/pitch has been made
    PITCHED("Lead has been pitched a proposal"),

    // Lead has placed an order or signed an agreement
    ORDER_PLACED("Lead has placed an order or signed an agreement"),

    // Lead is interested but not yet converted into an order
    CONVERTED("Lead has been converted into a customer"),

    // Lead showed interest but decided not to continue
    REJECTED("Lead rejected the offer or decided not to move forward"),

    // Lead is put on hold for further engagement at a later time
    ON_HOLD("Lead is on hold, to be revisited later"),

    // Lead has been lost or abandoned, no longer being pursued
    LOST("Lead has been lost or abandoned"),

    // Lead has been disqualified for reasons like lack of budget, not a fit, etc.
    DISQUALIFIED("Lead has been disqualified due to various reasons");

    private final String description;

    LeadStatus(String description) {
        this.description = description;
    }

}

