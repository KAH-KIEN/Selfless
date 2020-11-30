package com.example.eventdetails.ui.Firebase

data class EventRead(
        val eventID: String? = null,
        val eventTitle: String? = null,
        val eventDate: String? = null,
        val eventTime: String? = null,
        val eventLocation: String? = null,
        val eventContact: String? = null,
        val eventDescription: String? = null,
        val eventWhatsapp: String? = null,
        val eventSlot: Int? = null,
        val eventRegister: Int? = null
)
