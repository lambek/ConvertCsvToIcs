import create_google_documents.ConvectrCsvToIcs;
import create_google_documents.ConverctrCsvToVcf;

public class Main {
    public static void main(String[] args) {
        convectrCsvToIcs(); //создание задание в календарь
        converctrCsvToVcf(); // создание сконтактав
    }

    static void convectrCsvToIcs(){
        ConvectrCsvToIcs convectrCsvToIcs = new ConvectrCsvToIcs();
        convectrCsvToIcs.parserCsv("upload/school_Westra.csv");
//        convectrCsvToIcs.setEvent("20221201T150000Z",
//                "20221201T160000Z",
//                "Личное и бивачное снаряжение для горных походов 1-2 к.с.",
//                "Волочаевская улица\\, 38Ас1\\, Москва",
//                "0",
//                "CONFIRMED",
//                "Лекция Вестра",
//                "OPAQUE");
        convectrCsvToIcs.creatFile("upload/school_Westra.ics");
    }

    static void converctrCsvToVcf(){
        ConverctrCsvToVcf converctrCsvToVcf = new ConverctrCsvToVcf();
        converctrCsvToVcf.parserCsv("upload/contact_by2023.csv");
        converctrCsvToVcf.creatFile("upload/contact_by2023.vcf");
    }
}
