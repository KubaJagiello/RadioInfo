package Model;

import javax.swing.*;

/**
 * SwingWorker to do all the heavy work on other thread.
 */
public class Worker extends SwingWorker<Information, Void> {

    /**
     * Interface to let user know that job is finished.
     */
    @FunctionalInterface
    public interface FinishedInterface {
        void done();
    }

    private FinishedInterface finished;

    /**
     * Saves interface of listener.
     * @param finished interface that will be called when worker is done
     */
    public Worker(FinishedInterface finished) {
        this.finished = finished;
    }

    /**
     * Create new Information, if Information could not be created
     * Worker will return null.
     * @return instance of Class Information.
     * @throws Exception exception
     */
    @Override
    protected Information doInBackground() {
        try {
            return new Information();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Informs listener that the job is done.
     */
    @Override
    protected void done() {
       finished.done();
    }
}
