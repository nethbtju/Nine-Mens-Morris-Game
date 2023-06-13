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

    public boolean isPastEnd = false;

    public void executeNext(){
        System.out.println("current idnex");
        System.out.println(currentIndex);
        if(currentIndex < tutorialList.size() - 1){
            currentIndex+= 1;
            tutorialList.get(currentIndex).execute();

        }
        if (currentIndex == tutorialList.size() - 1){
            this.isPastEnd = true;

        }



    }

    public void executePrevious(){
        this.isPastEnd = false;
        if(currentIndex > 0){
            currentIndex--;
            tutorialList.get(currentIndex).execute();
        }

    }

    public boolean isAtEnd(){
        return (this.currentIndex == this.tutorialList.size() - 2);
    }

    public boolean isPastEnd(){
        return  this.isPastEnd;
    }





}
