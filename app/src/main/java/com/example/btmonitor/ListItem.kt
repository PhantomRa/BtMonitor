package com.example.btmonitor

import java.io.Serializable

data class ListItem(
    var name: String,
    var mac: String
) : Serializable