package com.kklv.mytest.ui.view

import android.content.Context
import android.graphics.Typeface
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * Author:lvzhendong
 * Created:7/19/23
 * Desc:
 */
class CustomPagerTitleView(context: Context) : SimplePagerTitleView(context) {

    override fun onSelected(index: Int, totalCount: Int) {
        super.onSelected(index, totalCount)
        setTextColor(mSelectedColor)
        setTypeface(null, Typeface.BOLD)
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        super.onDeselected(index, totalCount)
        setTextColor(mNormalColor)
        setTypeface(null, Typeface.NORMAL)
    }
}

