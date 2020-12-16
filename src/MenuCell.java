import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MenuCell extends ListCell<MyMenuItem> {
    private final GUIController controller;


    public MenuCell(GUIController c) {
        this.controller = c;
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


            System.out.println("CONTROLLO CELL : '"+ getItem().getClass() +"'");
            content.put(DataFormat.lookupMimeType("text"), getItem());//.putString(getItem());
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

            //Dragboard db = event.getDragboard();
            MyMenuItem db = (MyMenuItem) event.getSource();
            boolean success = false;

            //if (db.hasString()) {
                ObservableList<MyMenuItem> items = getListView().getItems();

                int draggedIdx = items.indexOf(db);
                //System.out.print("CONTROLLO: dradded idx"+draggedIdx);
                int thisIdx = items.indexOf(this.getItem());
                //System.out.print(" CONTROLLO: dradded idx"+thisIdx);

                /*Image temp = birdImages.get(draggedIdx);
                birdImages.set(draggedIdx, birdImages.get(thisIdx));
                birdImages.set(thisIdx, temp);*/

                items.set(draggedIdx, this.getItem());
                items.set(thisIdx, db);//.getString()

                List<MyMenuItem> itemscopy = new ArrayList<>(getListView().getItems());
                getListView().getItems().setAll(itemscopy);

                success = true;
            //}
            event.setDropCompleted(success);

            event.consume();
            controller.updateMenu();
        });

        setOnDragDone(DragEvent::consume);
        //controller.updateMenu();
    }

    @Override
    protected void updateItem(MyMenuItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            setText(item.toString());
            //controller.updateMenu();
            //setGraphic();
        }
    }
}
