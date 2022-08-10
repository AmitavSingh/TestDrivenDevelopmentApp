package com.demo.amitav.testdrivendevelopmentapp.data.remote.responses



data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)