package main;

import main.model.HHStrategy;
import main.model.Model;
import main.model.Provider;
import main.view.HtmlView;

public class Parser {
    public static void main(String[] args) {
        HtmlView view = new HtmlView();
        Provider hhProvider = new Provider(new HHStrategy());
        Model model = new Model(view, hhProvider);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
