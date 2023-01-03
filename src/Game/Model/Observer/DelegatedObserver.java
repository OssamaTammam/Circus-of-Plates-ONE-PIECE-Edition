package Game.Model.Observer;

import java.util.Observable;

/**
 * This class is responsible for delegating the update method to the observers
 */
public class DelegatedObserver extends Observable {
    public void setChanged() {
        super.setChanged();
    }

    public void clearChanged() {
        super.clearChanged();
    }
}
