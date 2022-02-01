package pl.pwr.dissertation.logic.domain.enums;

public enum TopicStatus {
    DRAFT,
    CONFIRMED,
    REJECTED_PB,
    ACCEPTED_PB,


    // only as return, cannot serach by this
    IN_PROGRESS,
    APPLICATION_REJECTED,
    APPLICATION_IN_PROGRESS
}
