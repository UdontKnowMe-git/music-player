import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class QueueView extends VBox {
    private ListView<String> queueList;

    public QueueView() {
        queueList = new ListView<>();
        getChildren().add(queueList);
    }

    public ListView<String> getQueueList() {
        return queueList;
    }

    public void updateQueueView(java.util.Queue<String> queue) {
        queueList.getItems().clear();
        queueList.getItems().addAll(queue);
    }
}
