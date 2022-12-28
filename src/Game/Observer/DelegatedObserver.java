package Game.Observer;

import java.util.Observable;

public class DelegatedObserver extends Observable {
    public void setChanged(){
        super.setChanged();
    }
    public void clearChanged(){
        super.clearChanged();
    }
}
