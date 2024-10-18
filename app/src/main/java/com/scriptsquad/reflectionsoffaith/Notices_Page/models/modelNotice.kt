package com.scriptsquad.reflectionsoffaith.Notices_Page.models


data class modelNotice(
    val uid: String,
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val timestamp: Long
) {
    constructor() : this("", "", "", "", "", "", 0) // Initialize with empty values
}

