package com.toandoan.cleanarchitechture.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface AppScheduler {
    /**
     * @return Scheduler to perform action In Out
     */
    fun io(): Scheduler

    /**
     * @return Schedule to perform action on main thread, ui thread
     */
    fun androidMainThread(): Scheduler

    /**
     * @return Scheduler to create a new thread.
     */
    fun newThread(): Scheduler
}

class AppSchedulerImpl : AppScheduler {

    override fun io() = Schedulers.io()

    override fun androidMainThread() = AndroidSchedulers.mainThread()

    override fun newThread() = Schedulers.newThread()
}