package bandeau;

public class BandeauVerrouillable extends Bandeau implements Runnable {
    private final Object lock = new Object();
    private boolean isPlaying = false;


    @Override
    public void run() {

        synchronized (lock) {
            if(isPlaying){
                return;
            }
            isPlaying = true;
        }
        try {
            String message = "Démonstration du bandeau";

            Scenario s = new Scenario();
            s.addEffect(new RandomEffect(message, 700), 1);
            s.addEffect(new TeleType("Je m'affiche caractère par caractère", 100), 1);
            s.addEffect(new Blink("Je clignote 10x", 100), 10);
            s.addEffect(new Zoom("Je zoome", 50), 1);
            s.addEffect(new FontEnumerator(10), 1);
            s.addEffect(new Rainbow("Comme c'est joli !", 30), 1);
            s.addEffect(new Rotate("2 tours à droite", 180, 400, true), 2);

            s.playOn(this);
        }
        finally {
            synchronized (lock) {
                isPlaying = false;
            }
        }
    }
}