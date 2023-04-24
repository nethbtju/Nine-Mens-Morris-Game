package Actions;

import GameEngine.Intersection;

public class MoveTokenAction extends Action{
    @Override
    public void execute(Intersection selectedIntersection, boolean player) {
        System.out.println("Move Action!");
    }
}
