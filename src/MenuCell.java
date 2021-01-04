import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MenuCell extends ListCell<MyMenuItem> {
    private final GUIController controller;


    public MenuCell(GUIController c) {
        this.controller = c;
        ArrayList<MyMenuItem> dragged = new ArrayList<>();
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
            dragged.add(getItem());


            System.out.println("CONTROLLO CELL : '"+ getItem().getClass() +"'"+ " elem in content:"+content.size()+"\t dragged:"+ dragged.get(0).toString());
            content.put(DataFormat.PLAIN_TEXT, getItem());//.putString(getItem());
            /*dragboard.setDragView(
                    birdImages.get(
                            items.indexOf(
                                    getItem()
                            )
                    )
            );*/
            System.out.println("CONTROLLO CELL: content nelem:"+content.size()+"\t =["+content.toString()+"\n] dragged:"+ dragged.get(0).toString());
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
            System.out.println("CONTROLLO CELL: new dragged:"+ dragged.get(0).toString());

            //Dragboard db = event.getDragboard();
            //MyMenuItem db = (MyMenuItem) event.getSource();
            //MyMenuItem db = (MyMenuItem) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
            //boolean success = false;

            //if (db.hasString()) {
                ObservableList<MyMenuItem> items = getListView().getItems();
            System.out.println("CONTROLLO CELL: dragged: '"+ dragged.get(0).toString()+"'");

                int draggedIdx = -1;
                for(MyMenuItem mi : items) {
                    if (mi.toString().equals(dragged.get(0).toString()))
                        draggedIdx = items.indexOf(mi);
                }
                System.out.print("CONTROLLO CELL: dragged idx"+draggedIdx);
                int thisIdx = items.indexOf(this.getItem());
                System.out.println(" CONTROLLO CELL: dropped idx"+thisIdx);

                /*Image temp = birdImages.get(draggedIdx);
                birdImages.set(draggedIdx, birdImages.get(thisIdx));
                birdImages.set(thisIdx, temp);*/

                items.set(draggedIdx, this.getItem());
                items.set(thisIdx, dragged.get(0));//.getString()

                List<MyMenuItem> itemscopy = new ArrayList<>(getListView().getItems());
                getListView().getItems().setAll(itemscopy);

                //success = true;
            //}
            event.setDropCompleted(true);//success

            event.consume();
            //controller.updateMenu();
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
