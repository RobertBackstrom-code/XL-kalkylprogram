package gui.menu;

import gui.XL;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class XLMenu extends MenuBar {
  public XLMenu(XL xl, Stage stage) {
    Menu fileMenu = new Menu("File");
    MenuItem save = new SaveMenuItem(xl, stage);
    save.setOnAction(event -> {
      xl.getModel().saveFile(new File("test/egetTest.xl"));
    });
    MenuItem load = new LoadMenuItem(xl, stage);
    load.setOnAction(event -> {
      try {
        xl.getModel().loadFile(new File("test/egetTest.xl"));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction(event -> stage.close());
    fileMenu.getItems().addAll(save, load, exit);

    Menu editMenu = new Menu("Edit");
    MenuItem clear = new MenuItem("Clear");
    clear.setOnAction(event -> {
      if(xl.getSelectedCell() != null) {
        xl.getModel().clearSelected(xl.getSelectedCell());
      }
    });
    MenuItem clearAll = new MenuItem("ClearAll");
    clearAll.setOnAction(event -> {
      xl.getModel().clearAll();
    });
    editMenu.getItems().addAll(clear, clearAll);
    getMenus().addAll(fileMenu, editMenu);
  }
}
