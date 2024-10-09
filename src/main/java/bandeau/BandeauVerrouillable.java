package bandeau;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BandeauVerrouillable extends Bandeau implements Runnable {
    private final Lock lock = new ReentrantLock();
    private boolean isPlaying = false;
    private final Scenario scenario;

    public BandeauVerrouillable(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            if (isPlaying) {
                return;
            }
            isPlaying = true;
        } finally {
            lock.unlock();
        }

        try {
            scenario.playOn(this);
        } finally {
            lock.lock();
            try {
                isPlaying = false;
            } finally {
                lock.unlock();
            }
        }
    }
}