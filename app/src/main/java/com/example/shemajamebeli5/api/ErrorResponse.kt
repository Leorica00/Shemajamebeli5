package com.example.shemajamebeli5.api

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "error") val error: String
)
