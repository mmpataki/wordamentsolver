package wordament;

import sun.misc.Queue;

class ObservableQueue extends Queue<int[][]> {

    IQueueChanged iqc;
    private boolean beingused = false;

    public ObservableQueue(IQueueChanged iqc) {
        this.iqc = iqc;
    }

    @Override
    public synchronized void enqueue(int[][] t) {
        super.enqueue(t);
        if (!beingused) {
            iqc.queueChanged();
        }
    }

    @Override
    public int[][] dequeue() throws InterruptedException {
        beingused = true;
        return super.dequeue();
    }
    
    public synchronized void unlock() {
        beingused = false;
    }    
}
