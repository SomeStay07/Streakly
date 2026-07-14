package com.example.streakly

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform