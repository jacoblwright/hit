package reports;

import java.util.List;

public interface Record {
    
    /**
     * @return a List of Strings, where the order and format of the Strings
     * correspond to the order and desired String representation of
     * the values in this TableRecord.
     */
    public List<String> getValuesAsStrings();

}
