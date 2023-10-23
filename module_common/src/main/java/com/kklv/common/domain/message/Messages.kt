package com.kklv.common.domain.message

/**
 * Author lvzhendong
 * Created 2023/8/11
 * Desc:
 */
class Messages(eventId: Int) {
    val eventId: Int

    init {
        this.eventId = eventId
    }

    companion object {
        const val EVENT_CLOSE_SLIDE_PANEL_IF_EXPANDED = 1
        const val EVENT_CLOSE_ACTIVITY_IF_ALLOWED = 2
        const val EVENT_OPEN_DRAWER = 3
        const val EVENT_ADD_SLIDE_LISTENER = 4
    }
}