
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import com.sun.javafx.collections.ImmutableObservableList;

import java.util.ArrayList;
import java.util.List;

public class MenuCell2 extends ListCell<MyMenuItem> {
    Controller contr;
    public MenuCell2(Controller c) {
        this.contr = c;
        //ListCell thisCell = this;

        setContentDisplay(ContentDisplay.TEXT_ONLY);
        setAlignment(Pos.CENTER_LEFT);

        setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }

            //ObservableList<String> items = getListView().getItems();

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(getItem().toString());
            System.out.println("CONTROLLO CELL: drag:"+content);
            /*dragboard.setDragView(
                    birdImages.get(
                            items.indexOf(
                                    getItem()
                            )
                    )
            );*/
            dragboard.setContent(content);

            event.consume();
        });

        setOnDragOver(event -> {
            if (event.getGestureSource() != this &&
                    event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() != this &&
                    event.getDragboard().hasString()) {
                setOpacity(0.3);
            }
        });

        setOnDragExited(event -> {
            if (event.getGestureSource() != this &&
                    event.getDragboard().hasString()) {
                setOpacity(1);
            }
        });

        setOnDragDropped(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard db = event.getDragboard();
            System.out.println("CONTROLLO CELL: dragged:"+db.getString());
            boolean success = false;

            if (db.hasString()) {
                ObservableList<MyMenuItem> m_list = getListView().getItems();
                System.out.println("CONTROLLO CELL: listaMI: ["+m_list.toString()+"\n");
                ObservableList<String> items = FXCollections.observableArrayList();
                for(MyMenuItem mi : m_list)
                    items.add(mi.toString());
                int draggedIdx = items.indexOf(db.getString());
                int dropIdx = items.indexOf(getItem().toString());
                System.out.println("CONTROLLO CELL: dragidx:"+draggedIdx+"\t dropidx:"+ dropIdx);

                /*Image temp = birdImages.get(draggedIdx);
                birdImages.set(draggedIdx, birdImages.get(thisIdx));
                birdImages.set(thisIdx, temp);*/

                MyMenuItem dragIt = m_list.get(draggedIdx), dropIt = m_list.get(dropIdx);

                System.out.println("CONTROLLO CELL: lista prima: ["+items+"\n]\n listaMI prima: ["+m_list.toString()+"\n]\n");
                m_list.set(draggedIdx, dropIt);
                items.set(draggedIdx, getItem().toString());
                m_list.set(dropIdx, dragIt);
                items.set(dropIdx, db.getString());
                System.out.println("CONTROLLO CELL: lista dopo: ["+items+"\n]\n listaMI dopo: ["+m_list.toString()+"\n]\n");

                //List<String> itemscopy = new ArrayList<>(getListView().getItems());
                //getListView().getItems().setAll(itemscopy);

                contr.updateFile(m_list);
                success = true;
            }
            event.setDropCompleted(success);

            event.consume();
        });

        setOnDragDone(DragEvent::consume);
    }

    @Override
    protected void updateItem(MyMenuItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            setText(item.toString());
            //setGraphic();
        }
    }
}

