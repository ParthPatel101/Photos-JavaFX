package stages.primary.photoslist;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Album;
import model.Model;
import photos.Photos;

import java.text.SimpleDateFormat;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;
//    @FXML
//    private TilePane photosPane;
    @FXML
    private Text albumName;
    @FXML
    private Button delete;
    @FXML
    private Button promptAdd;
    @FXML
    private Text caption;
    @FXML
    private Text dateTaken;
    @FXML
    private Button edit;
    @FXML
    private Button display;
    @FXML
    private Text photoPathLabel;
    @FXML
    private TextField photoPath;
    @FXML
    private Text warning;
    @FXML
    private Button sendAdd;
    public void initialize() {
        sendAdd.setDisable(true);
        photoPath.setDisable(true);
        this.setAlbumName();

        // ADD TilePane STUFF SO IT CAN CALL updateDetailDisplay when selecting a tile

        this.back.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/albums/albums.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.delete.setOnAction(actionEvent -> deletePhoto());
        this.promptAdd.setOnAction(actionEvent -> promptAdd());
        this.edit.setOnAction(actionEvent -> editPhoto());
        this.display.setOnAction(actionEvent -> displayPhoto());
        this.sendAdd.setOnAction(actionEvent -> addPhoto());
    }

    public void setAlbumName() {
        try {
            albumName.setText(((Album) Model.data.get(0)).name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDetailDisplay() {
        // NEEDS TO GET SELECTED Photo AND DISPLAY
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.caption.setText(""); // photo.caption
        this.dateTaken.setText(""); // formatter.format(photo.dateTaken)
    }

    public void deletePhoto() {
        // photosPane delete stuff
        System.out.println("Incomplete");
    }

    public void promptAdd() {
        if (promptAdd.getText().equals("Add")) {
            promptAdd.setText("Close");
            sendAdd.setDisable(false);
            photoPathLabel.setOpacity(1);
            photoPath.setOpacity(1);
            photoPath.setDisable(false);
            sendAdd.setOpacity(1);
        } else {
            promptAdd.setText("Add");
            sendAdd.setDisable(true);
            photoPathLabel.setOpacity(0);
            photoPath.setOpacity(0);
            photoPath.setDisable(true);
            sendAdd.setOpacity(0);
        }
    }

    public void editPhoto() {
        // SAVE SELECTED PHOTO IN DATA SO WE CAN USE IT IN NEXT SCENE BUT KEEP ALBUM IN THERE IN CASE WE GO BACK WE STILL NEED ALBUM
//        Model.data.add(1, selectedPhoto);
        Photos.changeScene("primary", "/stages/primary/edit/edit.fxml");
    }

    public void displayPhoto() {
        // SAVE SELECTED PHOTO IN DATA AS WELL SO WE CAN USE IT IN NEXT SCENE AND ALBUM TO CAROUSEL
//        Model.data.add(1, selectedPhoto);
        Photos.changeScene("viewphoto", "/stages/viewphoto/main/main.fxml");
    }


    public void addPhoto() {
        if (photoPath.getText().isEmpty()) {
            return;
        }

        try {
            ((Album) Model.data.get(0)).addPhoto(photoPath.getText());
            promptAdd.setText("Add");
            photoPathLabel.setOpacity(0);
            photoPath.clear();
            photoPath.setOpacity(0);
            photoPath.setDisable(true);
            warning.setOpacity(0);
            sendAdd.setOpacity(0);
            sendAdd.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            warning.setOpacity(0.69);
        }
    }
}
