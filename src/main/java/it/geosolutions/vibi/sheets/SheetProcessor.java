package it.geosolutions.vibi.sheets;

import it.geosolutions.vibi.store.Store;
import org.apache.poi.ss.usermodel.Sheet;

public interface SheetProcessor {

    void process(Sheet sheet, Store store);
}
