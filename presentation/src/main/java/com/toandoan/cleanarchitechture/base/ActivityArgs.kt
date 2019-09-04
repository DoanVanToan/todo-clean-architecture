package com.toandoan.cleanarchitechture.base

import android.content.Context
import android.content.Intent

interface ActivityArgs {
    /**
     * @return return an intent that can be use to start activity
     */
    fun intent(context: Context): Intent

    /**
     * @param activity to start activity and init intent value
     */
    fun launch(activity: Context) = activity.startActivity(intent(activity))
}