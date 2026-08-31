package io.github.mfaisalkhatri.listener;

import io.github.mfaisalkhatri.logging.FrameworkLifeCycleLogger;
import org.testng.IExecutionListener;

public class TestExecutionListener implements IExecutionListener {

    @Override
    public void onExecutionStart () {
        FrameworkLifeCycleLogger.executionStarted ();
    }

    @Override
    public void onExecutionFinish () {
        FrameworkLifeCycleLogger.executionCompleted ();
    }
}
