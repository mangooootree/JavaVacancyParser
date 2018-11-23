package main.model;

import main.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";

    protected Document getDocument(String searchString, int page) {
        String s = String.format(URL_FORMAT, searchString, page);
        Document doc = null;
        try {
            doc = Jsoup.connect(s)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 YaBrowser/18.9.1.954 Yowser/2.5 Safari/537.36")
                    .referrer("google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        int page = 0;
        List<Vacancy> result = new ArrayList<>();

        while (true) {
            Document document = getDocument(searchString, page);
            Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");

            if (elements.isEmpty())
                break;

            for (Element element : elements) {
                if (element != null) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValueContaining("data-qa", "title").text());
                    vacancy.setCity(element.getElementsByAttributeValueContaining("data-qa", "address").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setSiteName(URL_FORMAT);
                    String urlPage = element.getElementsByAttributeValueContaining("data-qa", "title").attr("href");
                    vacancy.setUrl(urlPage);
                    String salary = element.getElementsByAttributeValueContaining("data-qa", "compensation").text();
                    vacancy.setSalary(salary.length() == 0 ? "" : salary);
                    result.add(vacancy);
                }
            }
            page++;
        }
        return result;
    }

}
