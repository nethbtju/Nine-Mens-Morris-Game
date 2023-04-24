package actions;
import gameengine.Intersection;


public abstract class Action {

    public abstract void execute(Intersection selectedIntersection, boolean player);

}
