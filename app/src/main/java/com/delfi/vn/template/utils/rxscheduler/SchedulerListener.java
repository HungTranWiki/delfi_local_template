package com.delfi.vn.template.utils.rxscheduler;

import io.reactivex.Scheduler;

public interface SchedulerListener {
    Scheduler io();

    Scheduler ui();

    Scheduler computation();
}
