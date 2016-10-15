package com.starksoftware.library.abstracts.model.enumeration;

public enum FormatoDataEnum {

	HHmm("HH:mm"),

    DDMMYYYY("dd/MM/yyyy"),
    DD_MM_YYYY("dd-MM-yyyy"),
    ddMMyyyy_HHmm ("dd/MM/yyyy HH:mm"),
    ddMMyyyy_HHmmss ("dd/MM/yyyy HH:mm:ss"),

    MMYYYY("MM/yyyy"),
    MM_YYYY("MM-yyyy"),

    YYYYMMDD("yyyy-MM-dd"),
    YYYYMMDDHHMMSS("yyyy-MM-dd HH:mm:ss");

    private String formato;

    FormatoDataEnum(String formato) {
        this.formato = formato;
    }

    @Override
    public String toString() {
        return formato;
    }
}
