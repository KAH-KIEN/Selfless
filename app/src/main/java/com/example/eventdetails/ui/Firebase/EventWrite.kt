package com.example.eventdetails.ui.Firebase

class EventWrite (val eventID: String,
                  val eventTitle: String,
                  val eventDate: String,
                  val eventTime: String,
                  val eventDuration: Int,
                  val eventLocation: String,
                  val eventContact: String,
                  val eventDescription: String,
                  val eventWhatsapp: String,
                  val eventSlot: Int,
                  val eventRegister: Int){

    constructor() : this("","","", "", 0,
    "", "", "", "", 0, 0){

    }
}