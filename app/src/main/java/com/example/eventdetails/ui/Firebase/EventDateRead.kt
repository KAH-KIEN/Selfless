package com.example.eventdetails.ui.Firebase

import com.google.firebase.database.Exclude


class EventDateRead {
    var eventDate: String? = null

    constructor() {}

    fun EventDateRead(eventDate: String) {
        this.eventDate = eventDate
    }

    @JvmName("getEventDate1")
    public fun getEventDate(): String? {
        return eventDate
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        val result: HashMap<String, Any?> = HashMap()
        result["EventDate"] = eventDate
        return result
    }
}