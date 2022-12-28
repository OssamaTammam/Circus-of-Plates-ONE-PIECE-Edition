package eg.edu.alexu.csd.oop.Circus.Utils;

import eg.edu.alexu.csd.oop.Circus.MyWorld;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Caretaker {
    private int it = 0;
    private List<MyWorld.Memento> lis;
    private MyWorld world;
    public Caretaker(MyWorld world){
        lis = new LinkedList<>();
        this.world = world;
    }
    public void addMemento(MyWorld.Memento m){
        lis.add(m);
    }
    public boolean replay(){
        if(lis.isEmpty() || it == lis.size()){
            it = 0;
            return false;
        }
        world.restore(lis.get(it));
        it++;
        return true;
    }


}
