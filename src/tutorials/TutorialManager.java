package tutorials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TutorialManager {

    private List<TutorialState> tutorialList = new ArrayList<>();

    private int currentIndex = -1;

    public void add(TutorialState newTutorial){
        this.tutorialList.add(newTutorial);
    }

    public TutorialState getAtIndex(int index){
        return this.tutorialList.get(index);
    }

    public void executeNext(){
        if(currentIndex < tutorialList.size() - 1){
            currentIndex+= 1;
            tutorialList.get(currentIndex).execute();

        }


    }

    public void executePrevious(){
        if(currentIndex > 0){
            currentIndex--;
            tutorialList.get(currentIndex).execute();
        }

    }

}
