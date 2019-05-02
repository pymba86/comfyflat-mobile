package com.gitlab.pymba86.comfyflat.mobile.entity.websocket

import com.google.gson.JsonElement

data class WebSocketMessage(
    val action: String,
    val data: JsonElement?,
    val error: JsonElement?
)