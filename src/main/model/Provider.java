package main.model;

import main.vo.Vacancy;

import java.util.Collections;
import java.util.List;


public class Provider {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Vacancy> getJavaVacancies(String searchString) {
        if (searchString != null)
            return strategy.getVacancies(searchString);
        else
            return Collections.EMPTY_LIST;
    }
}
