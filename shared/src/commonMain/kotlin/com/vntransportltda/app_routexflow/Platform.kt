package com.vntransportltda.app_routexflow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
