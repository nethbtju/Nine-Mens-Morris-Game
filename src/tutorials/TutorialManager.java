package tutorials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Tutorial manager class that manages tutorial as doubly linked list. */
public class TutorialManager {

    private List<TutorialState> tutorialList = new ArrayList<>();

    private int currentIndex = -1;

    public boolean isPastEnd = false;

    /**
     * Append tutorial to tutorial list
     * @param newTutorial
     */
    public void add(TutorialState newTutorial){
        this.tutorialList.add(newTutorial);
    }

    /**
     * Get tutorial at specific index
     * @param index index of interest
     * @return TutorialState tutorial instance
     */
    public TutorialState getAtIndex(int index){
        return this.tutorialList.get(index);
    }

    /**
     * Retrieve next tutorial in list and execute the tutorial
     */
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

    /**
     * Retrieve previous tutorial in the list execute the tutorial
     */
    public void executePrevious(){
        this.isPastEnd = false;
        if(currentIndex > 0){
            currentIndex--;
            tutorialList.get(currentIndex).execute();
        }

    }

    /**
     * Check if current index is pointing at last tutorial
     * @return boolean if index is pointing at last tutorial
     */
    public boolean isAtEnd(){
        return (this.currentIndex == this.tutorialList.size() - 2);
    }

    /**
     * Check if current index is pointing past the last tutorial
     * @return boolean if index is pointing past last tutorial
     */
    public boolean isPastEnd(){
        return  this.isPastEnd;
    }





}
