package de.htw.mbsnw_projekt.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import de.htw.mbsnw_projekt.database.models.Spiel;

public class RoomDBTesting {

    public static AppDatabase setUpMemoryDB(Context appContext) {
        return Room.inMemoryDatabaseBuilder(appContext.getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    public static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] result = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                result[0] = t;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        System.out.println("Waiting.....");
        //noinspection ResultOfMethodCallIgnored
        latch.await(2, TimeUnit.SECONDS);
        System.out.println("Stopped waiting!");
        //noinspection unchecked
        return (T) result[0];
    }

    public static <E> E createAndInsertEntityWithId(E entity, Consumer<E> insertMethod, Supplier<LiveData<List<E>>> getAllMethod) throws InterruptedException {
        List<E> listBeforeInsert = getOrAwaitValue(getAllMethod.get());
        insertMethod.accept(entity);
        List<E> listAfterInsert = getOrAwaitValue(getAllMethod.get());
        List<E> dif = listAfterInsert.stream().filter(e -> !listBeforeInsert.contains(e)).collect(Collectors.toList());
        return dif.get(0);
    }

    public static Spiel createSpiel(Spiel spiel, AppDatabase db) throws InterruptedException {
        return createAndInsertEntityWithId(
                spiel,
                e -> db.spielDao().insert(e),
                () -> db.spielDao().getSpiele()
        );
    }
}
