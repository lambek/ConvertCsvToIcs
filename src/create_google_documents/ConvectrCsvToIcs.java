package create_google_documents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConvectrCsvToIcs implements ConvectrCsv {
    protected ArrayList<String> event = new ArrayList<>();

    /**
     * @param DTSTART
     * @param DTEND
     * @param DESCRIPTION
     * @param LOCATION
     * @param SEQUENCE
     * @param STATUS
     * @param SUMMARY
     * @param TRANSP
     */
    public void setEvent(
            String DTSTART,
            String DTEND,
            String DESCRIPTION,
            String LOCATION,
            String SEQUENCE,
            String STATUS,
            String SUMMARY,
            String TRANSP
    ) {
        this.event.add("BEGIN:VEVENT\n" +
                "DTSTART:" + DTSTART + "\n" +
                "DTEND:" + DTEND + "\n" +
                "DESCRIPTION:" + DESCRIPTION + "\n" +
                "LOCATION:" + LOCATION + "\n" +
                "SEQUENCE:" + SEQUENCE + "\n" +
                "STATUS:" + STATUS + "\n" +
                "SUMMARY:" + SUMMARY + "\n" +
                "TRANSP:" + TRANSP + "\n" +
                "END:VEVENT\n");
    }

    /**
     * @return
     */
    public String preparedData() {
        String start = "BEGIN:VCALENDAR\n" +
                "PRODID:-//Google Inc//Google Calendar 70.9054//EN\n" +
                "VERSION:2.0\n" +
                "CALSCALE:GREGORIAN\n" +
                "METHOD:PUBLISH\n" +
                "X-WR-TIMEZONE:Europe/Moscow\n" +
                "BEGIN:VTIMEZONE\n" +
                "TZID:Europe/Moscow\n" +
                "X-LIC-LOCATION:Europe/Moscow\n" +
                "BEGIN:STANDARD\n" +
                "TZOFFSETFROM:+0300\n" +
                "TZOFFSETTO:+0300\n" +
                "TZNAME:MSK\n" +
                "DTSTART:19700101T000000\n" +
                "END:STANDARD\n" +
                "END:VTIMEZONE\n";
        String end = "END:VCALENDAR\n";
        String body = "";

        for (String s : this.event) {
            body = body.concat(s);
        }

        return start + body + end;
    }

    /**
     * @param path
     */
    public void parserCsv(String path) {
        try (BufferedReader data = new BufferedReader(new FileReader(path))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
            String DTSTART, DTEND, DESCRIPTION, LOCATION, SUMMARY;
            for (int i = 0; i <= data.read(); i++) {
                DTSTART = DTEND = DESCRIPTION = LOCATION = SUMMARY = "";
                String[] arStr = (data.readLine()).split(",");
                if (i == 0) continue;
                DESCRIPTION = arStr[4];
                LOCATION = arStr[2];
                SUMMARY = arStr[3];

                if (arStr[0].indexOf("-") == -1) {
                    String[] d = arStr[0].split("\\.");
                    int[] arD = new int[3];
                    for (int z = 0; z < d.length; z++) {
                        arD[z] = Integer.valueOf(d[z]);
                    }
                    if (arStr[1].contains("чт") || arStr[1].contains("вт")) {
                        LocalDateTime dateTime = LocalDateTime.of(arD[2], arD[1], arD[0], 19, 30, 00);
                        DTSTART = formatter.format(dateTime.minusHours(3));
                        DTEND = formatter.format(dateTime);
                    } else {
                        LocalDateTime dateTime = LocalDateTime.of(arD[2], arD[1], arD[0], 8, 00, 00);
                        DTSTART = formatter.format(dateTime.minusHours(3));
                        DTEND = formatter.format(dateTime.plusHours(5));
                    }
                } else {
                    int[] arD = new int[4];
                    String[] d = arStr[0].split("\\.");
                    String[] dd = d[0].split("-");
                    arD[0] = Integer.valueOf(dd[0]);
                    arD[1] = Integer.valueOf(dd[1]);
                    arD[2] = Integer.valueOf(d[1]);
                    arD[3] = Integer.valueOf(d[2]);
                    LocalDateTime dateTimeStart = LocalDateTime.of(arD[3], arD[2], arD[0], 8, 00, 00);
                    LocalDateTime dateTimeEnd = LocalDateTime.of(arD[3], arD[2], arD[1], 20, 00, 00);
                    DTSTART = formatter.format(dateTimeStart.minusHours(3));
                    DTEND = formatter.format(dateTimeEnd.minusHours(3));
                }
                this.setEvent(DTSTART, DTEND, DESCRIPTION, LOCATION, "0", "CONFIRMED", SUMMARY, "OPAQUE");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
