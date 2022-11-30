package create_google_documents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConverctrCsvToVcf implements ConvectrCsv {
    protected ArrayList<String> event = new ArrayList<>();

    @Override
    public void parserCsv(String path) {
        try (BufferedReader data = new BufferedReader(new FileReader(path))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String BDAY, FN, N;
            for (int i = 0; i <= data.read(); i++) {
                BDAY = FN = N = "";
                String[] arStr = (data.readLine()).split(",");
                String[] d = arStr[4].split("\\.");
                int[] arD = new int[3];
                for (int z = 0; z < d.length; z++) {
                    arD[z] = Integer.valueOf(d[z]);
                }
                LocalDate date = LocalDate.of(arD[2], arD[1], arD[0]);
                BDAY = formatter.format(date);
                FN = arStr[1] + " " + arStr[2] + " " + arStr[3];
                N = arStr[1] + ";" + arStr[2] + ";" + arStr[3]+";;";
                this.setEvent(FN, N, arStr[0], arStr[5], BDAY);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setEvent(
            String FN,
            String N,
            String EMAIL,
            String TEL,
            String BDAY
    ) {
        this.event.add("BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                "FN:" + FN + "\n" +
                "N:" + N + "\n" +
                "item1.EMAIL;TYPE=INTERNET:" + EMAIL + "\n" +
                "item1.X-ABLabel:\n" +
                "item2.TEL:" + TEL + "\n" +
                "item2.X-ABLabel:Мобильные устройства\n" +
                "item3.ORG:Вестра\n" +
                "item3.X-ABLabel:\n" +
                "BDAY:" + BDAY + "\n" +
                "NOTE:Участник Гвандра 2023\n" +
                "CATEGORIES:Вестра,myContacts\n" +
                "END:VCARD\n");
    }

    @Override
    public String preparedData() {
        String body = "";

        for (String s : this.event) {
            body = body.concat(s);
        }
        return body;
    }
}
