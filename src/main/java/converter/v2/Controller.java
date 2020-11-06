package converter.v2;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable
{
	@FXML
	Button converButton ;
	@FXML
	Button exitButton ;
	@FXML
	ProgressBar progressBar ;
	@FXML
	Label percentLabel;
	@FXML
	Label labelText;
	File file = null;
	Serv service = new Serv();
	Alert alertError = new Alert(AlertType.ERROR);
	Alert alertDone = new Alert(AlertType.CONFIRMATION);
	public void convertButtonHandler()
	{
		FileBuilder.fileBuilder = (FileBuilder) service.createTask();
		
		progressBar.progressProperty().bind(FileBuilder.fileBuilder.progressProperty());
		percentLabel.textProperty().bind(FileBuilder.fileBuilder.messageProperty());
		labelText.textProperty().bind(FileBuilder.fileBuilder.textProperty);
		
		FileChooser fileOpen = new FileChooser();
		file = fileOpen.showOpenDialog(new Stage());
		FileBuilder.fileBuilder.setFile(file);
		
		service.restart();
	}
	
	public void exitButtonHandler()
	{
		Platform.exit();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		FileBuilder.sw[0]= new Sw(1,1,1,2,6,"K1O1_");
		FileBuilder.sw[1]= new Sw(1,2,1,3,7,"K1R1_");
		FileBuilder.sw[2]= new Sw(1,1,2,4,8,"K1O2_");
		FileBuilder.sw[3]= new Sw(1,2,2,5,9,"K1R2_");
		FileBuilder.sw[4]= new Sw(2,1,1,10,14,"K2O1_");
		FileBuilder.sw[5]= new Sw(2,2,1,11,15,"K2R1_");
		FileBuilder.sw[6]= new Sw(2,1,2,12,16,"K2O2_");
		FileBuilder.sw[7]= new Sw(2,2,2,13,17,"K2R2_");	
		
		alertDone.setTitle("Done");
		alertDone.setContentText("Convertation is done successfully");
		service.setOnSucceeded(e -> alertDone.showAndWait());
		
		alertError.setTitle("Error");
		alertError.setContentText("Wrong file, choose the right one!!!\nPlease convert again with the right file!!!");
		service.setOnFailed(e->alertError.showAndWait());
	}
}
class Serv extends Service<FileBuilder>
{
	@Override
	protected Task createTask()
	{
		return new FileBuilder();
	}
	
}
