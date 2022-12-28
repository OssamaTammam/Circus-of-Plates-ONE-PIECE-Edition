package Game.Utils;

import Game.MyWorld;

import java.util.LinkedList;
import java.util.List;

public class Caretaker {
    private int it = 0;
    private List<MyWorld.Memento> lis;
    private MyWorld world;

    public Caretaker(MyWorld world) {
        lis = new LinkedList<>();
        this.world = world;
    }

    public void addMemento(MyWorld.Memento m) {
        lis.add(m);
    }

    public boolean replay() {
        if (lis.isEmpty() || it == lis.size()) {
            it = 0;
            return false;
        }
        world.restore(lis.get(it));
        it++;
        return true;
    }


}
