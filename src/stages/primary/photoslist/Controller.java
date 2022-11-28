package stages.primary.photoslist;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Album;
import model.Model;
import model.Photo;
import photos.Photos;

import java.text.SimpleDateFormat;

public class Controller {
//    @FXML
//    private TilePane photosPane;
    @FXML
    private Button back;
    @FXML
    private Button logout;
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
    private Album currentAlbum;
    private Photo selectedPhoto;

    public void initialize() {
        sendAdd.setDisable(true);
        photoPath.setDisable(true);
        currentAlbum = (Album) Model.dataTransfer.get(0);
        albumName.setText("Album: " + currentAlbum.name);

        // Select first photo by default
        if (!currentAlbum.photos.isEmpty()) selectedPhoto = currentAlbum.photos.get(0);
        // ADD TilePane STUFF SO IT CAN CALL updateDetailDisplay when selecting a tile

        this.back.setOnAction(actionEvent -> {
            Model.initPreviousScene();
            Photos.changeScene("primary", "/stages/primary/albums/albums.fxml");
        });
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.delete.setOnAction(actionEvent -> deletePhoto());
        this.promptAdd.setOnAction(actionEvent -> promptAdd());
        this.edit.setOnAction(actionEvent -> editPhoto());
        this.display.setOnAction(actionEvent -> displayPhoto());
        this.sendAdd.setOnAction(actionEvent -> addPhoto());
    }

    public void updateDetailDisplay() {
        // NEEDS TO GET SELECTED Photo AND DISPLAY
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.caption.setText(""); // photo.caption
        this.dateTaken.setText(""); // formatter.format(photo.dateTaken.getTime())
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
            return;
        }
        promptAdd.setText("Add");
        sendAdd.setDisable(true);
        photoPathLabel.setOpacity(0);
        photoPath.setOpacity(0);
        photoPath.setDisable(true);
        sendAdd.setOpacity(0);
        warning.setOpacity(0);
    }

    public void editPhoto() {
        Model.initNextScene(true);
//        SAVE SELECTED PHOTO AND CURRENT ALBUM IN DATA SO WE CAN USE IT IN NEXT SCENE
        Model.dataTransfer.add(Model.currentUser.albums.get(0));
        Model.dataTransfer.add(Model.currentUser.albums.get(0).photos.get(0));
        Photos.changeScene("primary", "/stages/primary/edit/edit.fxml");
    }

    public void displayPhoto() {
        Model.initNextScene(false);
//        SAVE SELECTED PHOTO IN dataTransfer SO WE CAN USE IT IN NEXT SCENE AND ALBUM TO CAROUSEL
        Model.dataTransfer.add(Model.currentUser.albums.get(0));
        Model.dataTransfer.add(Model.currentUser.albums.get(0).photos.get(0));
        Photos.changeScene("viewphoto", "/stages/viewphoto/main/main.fxml");
    }


    public void addPhoto() {
        if (photoPath.getText().isEmpty()) {
            return;
        }
        try {
            currentAlbum.addPhoto(photoPath.getText());
            promptAdd.setText("Add");
            photoPathLabel.setOpacity(0);
            photoPath.clear();
            photoPath.setOpacity(0);
            photoPath.setDisable(true);
            warning.setOpacity(0);
            sendAdd.setOpacity(0);
            sendAdd.setDisable(true);
        } catch (Exception e) {
            warning.setOpacity(0.69);
            throw new RuntimeException("error adding photo to album.");
        }
    }
}
