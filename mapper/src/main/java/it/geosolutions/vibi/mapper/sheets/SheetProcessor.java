package it.geosolutions.vibi.mapper.sheets;

import it.geosolutions.vibi.mapper.store.Store;
import org.apache.poi.ss.usermodel.Sheet;

public interface SheetProcessor {

    void process(Sheet sheet, Store store);
}
