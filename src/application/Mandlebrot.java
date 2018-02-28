package application;

import java.awt.Robot;

import application.exceptions.FieldEmptyException;
import application.exceptions.FieldEquals0Exception;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;


public class Mandlebrot extends Application {
	
	private final Color DRAWING_COLOR = new Color(0.85, 0.85, 0, 1);
	private final Color DONE_COLOR = new Color(0, 0.9, 0, 1);
	private final Color ERROR_COLOR = new Color(0.9, 0, 0, 1);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root,1235,1000);
			primaryStage.setResizable(true);
			
			ImageView imgView = new ImageView();
			root.getChildren().add(imgView);
			WritableImage writableImage = new WritableImage((int)scene.getWidth() - 235, (int)scene.getHeight());
			imgView.setImage(writableImage);
			MandelbrotDrawer mbDrawer = new MandelbrotDrawer(writableImage);
			CursorTracker cursorTracker = new CursorTracker((int)writableImage.getWidth(), (int)writableImage.getHeight());
			
			
			
			//---------------------------------------------MENU---------------------------------------------------
			GridPane menuBox = new GridPane();
			root.getChildren().add(menuBox);
			menuBox.setLayoutX(writableImage.getWidth());
			menuBox.setPadding(new Insets(30));
			menuBox.getColumnConstraints().add(0, new ColumnConstraints(85));
			menuBox.getColumnConstraints().add(1, new ColumnConstraints(100));
			
			menuBox.getRowConstraints().add(0, new RowConstraints(30)); //Zoom
			menuBox.getRowConstraints().add(1, new RowConstraints(5));  //
			menuBox.getRowConstraints().add(2, new RowConstraints(35)); //Center Point
			menuBox.getRowConstraints().add(3, new RowConstraints(30)); //Real
			menuBox.getRowConstraints().add(4, new RowConstraints(30)); //Imaginary
			menuBox.getRowConstraints().add(5, new RowConstraints(10)); //
			menuBox.getRowConstraints().add(6, new RowConstraints(30)); //Cycles
			menuBox.getRowConstraints().add(7, new RowConstraints(5));  //
			menuBox.getRowConstraints().add(8, new RowConstraints(35)); //Colors
			menuBox.getRowConstraints().add(9, new RowConstraints(30)); //Inside
			menuBox.getRowConstraints().add(10,new RowConstraints(30)); //Outside
			menuBox.getRowConstraints().add(11,new RowConstraints(15)); //
			menuBox.getRowConstraints().add(12,new RowConstraints(30)); //Draw
			menuBox.getRowConstraints().add(12,new RowConstraints(30)); //Draw
			menuBox.getRowConstraints().add(12,new RowConstraints(30)); //Draw
			
			
			//Zoom
			Label zoomLabel = new Label("Zoom:");
			menuBox.getChildren().add(zoomLabel);
			GridPane.setColumnIndex(zoomLabel, 0);
			GridPane.setRowIndex(zoomLabel, 0);
			
			TextField zoomField = new TextField();
			zoomField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
			zoomField.setText("0.2");
			menuBox.getChildren().add(zoomField);
			GridPane.setColumnIndex(zoomField, 1);
			GridPane.setRowIndex(zoomField, 0);
			
			
			//Anchor
			Label anchorLabel = new Label("Center Point");
			menuBox.getChildren().add(anchorLabel);
			GridPane.setColumnIndex(anchorLabel, 0);
			GridPane.setRowIndex(anchorLabel, 2);
			
			Label anchorRealLabel = new Label("Real:");
			menuBox.getChildren().add(anchorRealLabel);
			GridPane.setColumnIndex(anchorRealLabel, 0);
			GridPane.setRowIndex(anchorRealLabel, 3);
			
			Label anchorImagLabel = new Label("Imaginary:");
			menuBox.getChildren().add(anchorImagLabel);
			GridPane.setColumnIndex(anchorImagLabel, 0);
			GridPane.setRowIndex(anchorImagLabel, 4);
			
			TextField anchorRealField = new TextField();
			anchorRealField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
			anchorRealField.setText("0.0");
			menuBox.getChildren().add(anchorRealField);
			GridPane.setColumnIndex(anchorRealField, 1);
			GridPane.setRowIndex(anchorRealField, 3);
			
			TextField anchorImagField = new TextField();
			anchorImagField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
			anchorImagField.setText("0.0");
			menuBox.getChildren().add(anchorImagField);
			GridPane.setColumnIndex(anchorImagField, 1);
			GridPane.setRowIndex(anchorImagField, 4);
			
			
			//Cycles
			Label cyclesLabel = new Label("Cycles:");
			menuBox.getChildren().add(cyclesLabel);
			GridPane.setColumnIndex(cyclesLabel, 0);
			GridPane.setRowIndex(cyclesLabel, 6);
			
			TextField cyclesField = new TextField();
			cyclesField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
			cyclesField.setText("100");
			menuBox.getChildren().add(cyclesField);
			GridPane.setColumnIndex(cyclesField, 1);
			GridPane.setRowIndex(cyclesField, 6);
			
			
			//Color
			Label colorsLabel = new Label("Colors");
			menuBox.getChildren().add(colorsLabel);
			GridPane.setColumnIndex(colorsLabel, 0);
			GridPane.setRowIndex(colorsLabel, 8);
			
			Label colorInsideLabel = new Label("Inside");
			menuBox.getChildren().add(colorInsideLabel);
			GridPane.setColumnIndex(colorInsideLabel, 0);
			GridPane.setRowIndex(colorInsideLabel, 9);
			
			Label colorOutsideLabel = new Label("Outside");
			menuBox.getChildren().add(colorOutsideLabel);
			GridPane.setColumnIndex(colorOutsideLabel, 0);
			GridPane.setRowIndex(colorOutsideLabel, 10);
			
			ColorPicker cpInside = new ColorPicker(new Color(0, 0, 0, 1)/*Black*/);
			menuBox.getChildren().add(cpInside);
			GridPane.setColumnIndex(cpInside, 1);
			GridPane.setRowIndex(cpInside, 9);
			
			ColorPicker cpOutside = new ColorPicker(new Color(1, 1, 1, 1)/*White*/);
			menuBox.getChildren().add(cpOutside);
			GridPane.setColumnIndex(cpOutside, 1);
			GridPane.setRowIndex(cpOutside, 10);
			
			
			//Draw
			Label drawStateLabel = new Label();
			drawStateLabel.textOverrunProperty().set(OverrunStyle.CLIP);
			menuBox.getChildren().add(drawStateLabel);
			GridPane.setColumnIndex(drawStateLabel, 1);
			GridPane.setRowIndex(drawStateLabel, 12);
			
			Button btnDraw = new Button("Draw");
			menuBox.getChildren().add(btnDraw);
			GridPane.setColumnIndex(btnDraw, 0);
			GridPane.setRowIndex(btnDraw, 12);
			btnDraw.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					
					try {
						checkField(zoomField, "Zoom");
						checkField(anchorRealField, "Real");
						checkField(anchorImagField, "Imaginary");
						checkField(cyclesField, "Cycles");
						
						drawStateLabel.setText("Drawing");
						drawStateLabel.setTextFill(DRAWING_COLOR);
						
						double zoom = Double.parseDouble(zoomField.getText());
						double anchorReal = Double.parseDouble(anchorRealField.getText());
						anchorReal = anchorReal - (0.5 / zoom);
						double anchorImaginary = Double.parseDouble(anchorImagField.getText());
						anchorImaginary = anchorImaginary + (0.5 / zoom);
						int numOfCycles = Integer.parseInt(cyclesField.getText());
						Color insideColor = cpInside.getValue();
						Color outsideColor = cpOutside.getValue();
						
						AnchorZoom anchorZoom = new AnchorZoom(new ComplexNumber(anchorReal, anchorImaginary), zoom);
						mbDrawer.draw(anchorZoom, numOfCycles, insideColor, outsideColor);
						cursorTracker.updateImage(anchorZoom);
						
						drawStateLabel.setText("Done");
						drawStateLabel.setTextFill(DONE_COLOR);
//					} catch (ZoomEquals0Exception e) {
//						drawStateLabel.setText("Error");
//						drawStateLabel.setTextFill(ERROR_COLOR);
					} catch (FieldEmptyException e) {
						drawStateLabel.setText(e.getMessage());
						drawStateLabel.setTextFill(ERROR_COLOR);
					}
				}
			});
			
			
			//MouseClick
			Label centerOnClickLabel = new Label("Click to center");
			menuBox.getChildren().add(centerOnClickLabel);
			GridPane.setColumnIndex(centerOnClickLabel, 0);
			GridPane.setRowIndex(centerOnClickLabel, 13);
			
			CheckBox centerOnClickCheckBox = new CheckBox();
			centerOnClickCheckBox.setSelected(true);
			menuBox.getChildren().add(centerOnClickCheckBox);
			GridPane.setColumnIndex(centerOnClickCheckBox, 1);
			GridPane.setRowIndex(centerOnClickCheckBox, 13);
			
			imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					
					drawStateLabel.setText("Drawing");
					drawStateLabel.setTextFill(DRAWING_COLOR);
					
					double zoom = Double.parseDouble(zoomField.getText());
					ComplexNumber cx = cursorTracker.getCursorComplex(new Coordinates((int)event.getSceneX(), (int)event.getSceneY()));
					double anchorReal = cx.getReal();
					anchorRealField.setText(Double.toString(anchorReal));
					anchorReal = anchorReal - (0.5 / zoom);
					double anchorImaginary = cx.getImaginary();
					anchorImagField.setText(Double.toString(anchorImaginary));
					anchorImaginary = anchorImaginary + (0.5 / zoom);
					int numOfCycles = Integer.parseInt(cyclesField.getText());
					Color insideColor = cpInside.getValue();
					Color outsideColor = cpOutside.getValue();
					
					AnchorZoom anchorZoom = new AnchorZoom(new ComplexNumber(anchorReal, anchorImaginary), zoom);
					mbDrawer.draw(anchorZoom, numOfCycles, insideColor, outsideColor);
					cursorTracker.updateImage(anchorZoom);
					
					drawStateLabel.setText("Done");
					drawStateLabel.setTextFill(DONE_COLOR);
					
				}
			});
			
			
			//Cursor
			Label trackCursorLabel = new Label("Track Cursor");
			menuBox.getChildren().add(trackCursorLabel);
			GridPane.setColumnIndex(trackCursorLabel, 0);
			GridPane.setRowIndex(trackCursorLabel, 14);
			
			CheckBox trackCursorCheckBox = new CheckBox();
			trackCursorCheckBox.setSelected(true);
			menuBox.getChildren().add(trackCursorCheckBox);
			GridPane.setColumnIndex(trackCursorCheckBox, 1);
			GridPane.setRowIndex(trackCursorCheckBox, 14);
			
			Label cursorRealText = new Label("Real:");
			menuBox.getChildren().add(cursorRealText);
			GridPane.setColumnIndex(cursorRealText, 0);
			GridPane.setRowIndex(cursorRealText, 15);
			
			Label cursorRealNum = new Label();
			menuBox.getChildren().add(cursorRealNum);
			GridPane.setColumnIndex(cursorRealNum, 1);
			GridPane.setRowIndex(cursorRealNum, 15);
			
			Label cursorImagText = new Label("Imaginary:");
			menuBox.getChildren().add(cursorImagText);
			GridPane.setColumnIndex(cursorImagText, 0);
			GridPane.setRowIndex(cursorImagText, 16);
			
			Label cursorImagNum = new Label();
			menuBox.getChildren().add(cursorImagNum);
			GridPane.setColumnIndex(cursorImagNum, 1);
			GridPane.setRowIndex(cursorImagNum, 16);

			
			
			
			
			
			//-----------------------------------------Cursor Tracking------------------------------------------
			imgView.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent eventEnter) {
					if (trackCursorCheckBox.isSelected()) {
						imgView.setOnMouseMoved(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent eventMove) {
								ComplexNumber cx = cursorTracker.getCursorComplex(new Coordinates((int)eventMove.getSceneX(), (int)eventMove.getSceneY()));
								cursorRealNum.setText(Double.toString(cx.getReal()));
								cursorImagNum.setText(Double.toString(cx.getImaginary()));
							}
						});
					}
				}
			});
			imgView.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					imgView.setOnMouseMoved(null);
					cursorRealNum.setText("");
					cursorImagNum.setText("");
				}
			});
			
			
			
			
			//----------------------------------------------Footer----------------------------------------------
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void checkField(TextField field, String name) throws FieldEmptyException {
		if (field.getText().length() == 0) {
			throw new FieldEmptyException(name + " is empty!");
		}
	}
	
	private void checkZero(TextField field) {
		if (Double.parseDouble(field.getText()) == 0.0d) {
		}
	}
	
}

//----------------------------------------------------Mandelbrot Drawer---------------------------------------------------------------

class MandelbrotDrawer {
	
	private WritableImage writableImage;
	
	public MandelbrotDrawer(WritableImage writableImage) {
		super();
		this.writableImage = writableImage;
	}
	
	
	public void draw(AnchorZoom anchorZoom, int numOfCycles, Color insideColor, Color outsideColor) {
		PixelWriter pWriter = writableImage.getPixelWriter();
		for (int i = 0; i < writableImage.getWidth(); i++) {
			for (int j = 0; j < writableImage.getHeight(); j++) {					
				ComplexNumber c = ComplexNumber.coordsToComplex(new Coordinates(i, j), anchorZoom, (int)writableImage.getWidth(), (int)writableImage.getHeight());
				if (calcInside(c, numOfCycles)) {
					pWriter.setColor(i, j, insideColor);
				} else {
					pWriter.setColor(i, j, outsideColor);
				}
			}
		}
		
	}
	
	private boolean calcInside(ComplexNumber cxNum, int repetitions) {
		boolean isInside = true;
		int curRep = 1;
		ComplexNumber currentCX = new ComplexNumber(0, 0);
		while (isInside && curRep <= repetitions) {
			curRep++;
			currentCX = ComplexNumber.addComplex(ComplexNumber.squareComplex(currentCX), cxNum);
			double num1 = Math.pow(Math.abs(currentCX.getReal()), 2);
			double num2 = Math.pow(Math.abs(currentCX.getImaginary()), 2);
			if (Math.sqrt(num1 + num2) > 2) {
				isInside = false;
			}
		}
		return isInside;
	}
	
}

class Coordinates {
	private int x;
	private int y;
	
	public Coordinates(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}

class ComplexNumber {
	private double real;
	private double imaginary;
	
	public ComplexNumber(double real, double imaginary) {
		super();
		this.real = real;
		this.imaginary = imaginary;
	}
	
	
	public double getReal() {
		return real;
	}

	public double getImaginary() {
		return imaginary;
	}
	
	public static ComplexNumber coordsToComplex(Coordinates coords, AnchorZoom az, int imageWidth, int imageHeight) {
		double real = az.getAnchor().getReal() + (coords.getX() / (az.getZoom() * (imageWidth - 1)));
		double imaginary = az.getAnchor().getImaginary() - (coords.getY() / (az.getZoom() * (imageHeight - 1)));
		return new ComplexNumber(real, imaginary);
	}
	
	public static ComplexNumber squareComplex(ComplexNumber cx) {
		double real = Math.pow(cx.getReal(), 2) - Math.pow(cx.getImaginary(), 2);
		double imaginary = 2 * cx.getReal() * cx.getImaginary();
		return new ComplexNumber(real, imaginary);
	}
	
	public static ComplexNumber addComplex(ComplexNumber cx1, ComplexNumber cx2) {
		double real = cx1.getReal() + cx2.getReal();
		double imaginary = cx1.getImaginary() + cx2.getImaginary();
		return new ComplexNumber(real, imaginary);
	}
	
}

class AnchorZoom {
	private ComplexNumber anchor;
	private double zoom;
	
	public AnchorZoom(ComplexNumber anchor, double zoom) {
		super();
		this.anchor = anchor;
		this.zoom = zoom;
	}
	
	
	public ComplexNumber getAnchor() {
		return anchor;
	}
	public double getZoom() {
		return zoom;
	}
	
}

class CursorTracker {
	
	private AnchorZoom anchorZoom;
	private int imageWidth;
	private int imageHeight;
	
	public CursorTracker(int imageWidth, int imageHeight) {
		super();
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}
	

	public void updateImage(AnchorZoom anchorZoom) {
		this.anchorZoom = anchorZoom;
	}
	
	public void updateImage(AnchorZoom anchorZoom, int imageWidth, int imageHeight) {
		this.anchorZoom = anchorZoom;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}
	
	public ComplexNumber getCursorComplex(Coordinates coords) {
		return ComplexNumber.coordsToComplex(coords, anchorZoom, imageWidth, imageHeight);
	}
	
}
