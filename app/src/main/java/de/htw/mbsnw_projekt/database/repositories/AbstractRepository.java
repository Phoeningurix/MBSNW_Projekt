package de.htw.mbsnw_projekt.database.repositories;

public abstract class AbstractRepository implements Repository {

    protected void doInBackground(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

}
