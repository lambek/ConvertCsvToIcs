import create_ics.HandlerCsvToIcs;

public class Main {
    public static void main(String[] args) {
        HandlerCsvToIcs handlerCsvToIcs = new HandlerCsvToIcs();
        handlerCsvToIcs.parserCsv("upload/school_Westra.csv");
//        handlerCsvToIcs.setEvent("20221201T150000Z",
//                "20221201T160000Z",
//                "Личное и бивачное снаряжение для горных походов 1-2 к.с.",
//                "Волочаевская улица\\, 38Ас1\\, Москва",
//                "0",
//                "CONFIRMED",
//                "Лекция Вестра",
//                "OPAQUE");
        handlerCsvToIcs.creatFileIcs("upload/school_Westra.ics");
    }
}
