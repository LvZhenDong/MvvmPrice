package com.kklv.common.domain.message

import com.kunminx.architecture.domain.dispatch.MviDispatcher

/**
 * Author:lvzhendong
 * Created:2023/8/11
 * Desc:
 */
class PageMessenger : MviDispatcher<Messages>() {
    override fun onHandle(event: Messages) {
        sendResult(event)
    }
}