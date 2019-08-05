package com.toandoan.cleanarchitechture.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface AppScheduler {
    fun io(): Scheduler

    fun androidMainThread(): Scheduler

    fun newThread(): Scheduler
}

class AppSchedulerImpl : AppScheduler {

    override fun io() = Schedulers.io()

    override fun androidMainThread() = AndroidSchedulers.mainThread()

    override fun newThread() = Schedulers.newThread()
}