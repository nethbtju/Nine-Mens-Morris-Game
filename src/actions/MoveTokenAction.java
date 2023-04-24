package actions;

import gameengine.Intersection;

public class MoveTokenAction extends Action{
    @Override
    public void execute(Intersection selectedIntersection, boolean player) {
        System.out.println("Move Action!");
    }
}
