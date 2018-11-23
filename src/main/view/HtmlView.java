package main.view;

import main.Controller;
import main.vo.Vacancy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private final String filePath = "C:/java_projects/sources/JavaVacancyParser/src/main/view/vacancies.html";
    private Controller controller;

    @Override
    public void update(List<Vacancy> vacancies) {
        String data = getUpdatedFileContent(vacancies);
        updateFile(data);
        System.out.println("it's ok!");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        String data = "<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Вакансии</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table width=\"100%\">\n" +
                "    <tr>\n" +
                "        <th>Title</th>\n" +
                "        <th>City</th>\n" +
                "        <th>Company Name</th>\n" +
                "        <th>Salary</th>\n" +
                "    </tr>";

        for (Vacancy vacancy : vacancies) {
            data +=
                    "      <tr class=\"vacancy\">\n" +
                            "        <td class=\"title\"><a href=\" " + vacancy.getUrl() + "\">" + vacancy.getTitle() + "</a></td>\n" +
                            "        <td class=\"city\">" + vacancy.getCity() + "</td>\n" +
                            "        <td class=\"companyName\">" + vacancy.getCompanyName() + "</td>\n" +
                            "        <td class=\"salary\">" + vacancy.getSalary() + "</td>\n </tr>\n";
        }

        data += " </table> \n" +
                "</body>\n" +
                "</html>";
        return data;
    }

    private void updateFile(String data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Витебск");
    }
}
