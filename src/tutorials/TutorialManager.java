package tutorials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** A Manager class that organises Tutorials. */
public class TutorialManager {

  private final List<TutorialState> tutorialList = new ArrayList<>();
  private int currentIndex = -1;
  public boolean isPastEnd = false;

  /** Append a new TutorialState to this TutorialManager's tutorialList. */
  public void add(TutorialState newTutorial) {
    this.tutorialList.add(newTutorial);
  }

  /** Executes the next TutorialState in the tutorialList. */
  public void executeNext() {
    System.out.println("current index");
    System.out.println(currentIndex);
    if (currentIndex < tutorialList.size() - 1) {
      currentIndex += 1;
      tutorialList.get(currentIndex).execute();
    }
    if (currentIndex == tutorialList.size() - 1) {
      this.isPastEnd = true;
    }
  }

  /** Executes the previous TutorialState in the tutorialList. */
  public void executePrevious() {
    this.isPastEnd = false;
    if (currentIndex > 0) {
      currentIndex--;
      tutorialList.get(currentIndex).execute();
    }
  }

  /**
   * Check whether the current TutorialState is at the end of the tutorialList.
   *
   * @return True if it has, False if it hasn't.
   */
  public boolean isAtEnd() {
    return (this.currentIndex == this.tutorialList.size() - 2);
  }
}
