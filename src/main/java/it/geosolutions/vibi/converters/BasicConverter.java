package it.geosolutions.vibi.converters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public final class BasicConverter implements Converter {

    public static BasicConverter BASIC_CONVERTER = new BasicConverter();

    private BasicConverter() {
    }

    @Override
    public Object convert(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                return DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue();
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            default:
                throw new RuntimeException(String.format("Unknown cell type '%d'.", cell.getCellType()));
        }
    }
}
