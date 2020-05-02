package crossway.log;

public enum LogCodeNames {

    //01003 ext
    ERROR_METRIC_REPORT_ERROR("010030001"), ERROR_TRACER_INIT("010030002"), ERROR_FILTER_CONSTRUCT("010030003"), ERROR_CREATE_EXT_INSTANCE("010030004"),
    ERROR_EXTENSION_CLASS_NULL("010030005"), ERROR_EXTENSION_NOT_FOUND("010030006"), ERROR_LOAD_EXT("010030007");

    private String code;

    LogCodeNames(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
