package com.discordsrv.sponge.impl;

import com.discordsrv.common.abstracted.Scheduler;
import com.discordsrv.sponge.SpongePlugin;

import java.util.concurrent.TimeUnit;

public class SchedulerImpl implements Scheduler {

    @Override
    public void runTask(Runnable runnable) {
        SpongePlugin.get().getSyncExecutor().execute(runnable);
    }

    @Override
    public void runTaskLater(Runnable runnable, long delay) {
        SpongePlugin.get().getSyncExecutor().schedule(runnable, delay * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runTaskRepeating(Runnable runnable, long delay, long period) {
        SpongePlugin.get().getSyncExecutor().scheduleAtFixedRate(runnable, delay * 50, period * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runTaskAsync(Runnable runnable) {
        SpongePlugin.get().getAsyncExecutor().execute(runnable);
    }

    @Override
    public void runTaskAsyncLater(Runnable runnable, long delay) {
        SpongePlugin.get().getAsyncExecutor().schedule(runnable, delay * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runTaskAsyncRepeating(Runnable runnable, long delay, long period) {
        SpongePlugin.get().getAsyncExecutor().scheduleAtFixedRate(runnable, delay * 50, period * 50, TimeUnit.MILLISECONDS);
    }
}
