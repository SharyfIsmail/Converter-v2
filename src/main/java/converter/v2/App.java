package converter.v2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{

    public static void main( String[] args )
    {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage)
	{
		try {
			 Parent root = FXMLLoader.load(getClass().getResource("Fxml.fxml"));	      
			 primaryStage.setTitle("Converter");
			 Scene scene = new Scene(root, 250,300);
			 primaryStage.setScene(scene);
			 primaryStage.setOnCloseRequest(e-> {
				 e.consume();
				 primaryStage.close();
		        });
			 primaryStage.setResizable(false);
			 primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
