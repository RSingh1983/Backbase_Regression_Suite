package org.testing.framework.backend.webElements.pageElements;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testing.framework.backend.webElements.locator.WebElementLocator;
import org.testing.framework.properties.DisplayOrderType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Table extends PageObject {

    static Logger logger = LoggerFactory.getLogger(Table.class.getName());

    public Table(WebDriver driver) {
        super(driver);
    }

    /**
     *
     * @param tableName specifies the name of table
     * @param beanFileName specifies named o bean file
     * @param beanPath specifies path of bean
     * @return Integer
     * @throws Exception
     */
    public Integer getTableSize(final String tableName, final String beanFileName,
                                final String beanPath) throws Exception {
        Integer size = 0;
        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(tableName, beanFileName, beanPath, getDriver(), true);
        logger.info("found");
        List<WebElement> rowElements = element.findElements(By.xpath("./tbody/tr"));
        logger.info("Resulting rows: " + rowElements.size());

        size = rowElements.size();
        return size;
    }

//    /**
//     *
//     * @param tableName
//     * @param rowIdentifier
//     * @param beanFileName
//     * @param beanPath
//     * @return
//     * @throws Exception
//     */
//    public Integer getTableSize(final String tableName, final String rowIdentifier, final String beanFileName,
//                                final String beanPath) throws Exception {
//        Integer size = 0;
//        // Locate the passed element
//        WebElementLocator elementLocator = WebElementLocator.getInstance();
//        WebElement element = elementLocator.locateElement(tableName, beanFileName, beanPath, getDriver(), true);
//        logger.info("found");
//        List<WebElement> rowElements = element.findElements(By.xpath("./tr"));
//        logger.info("Resulting rows: " + rowElements.size());
//
//        size = rowElements.size();
//        return size;
//    }

    public ArrayList<HashMap<String,String>> getTableData(final String tableIdentifier,final String beanFileName, final String beanPath) throws Exception{
        ArrayList<String> headerList = new ArrayList();
        ArrayList<HashMap<String,String>> tableData = new ArrayList();
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement tableElement = elementLocator.locateElement(tableIdentifier, beanFileName, beanPath, getDriver(), true);
        /*List<WebElement> headerElements = tableElement.findElements(By.xpath("./thead/tr/th"));
        for(WebElement headerElement : headerElements){
            headerList.add(headerElement.getText());
        }*/
        List<WebElement> headerElements = tableElement.findElements(By.xpath(".//tr/th"));
        for(WebElement headerElement : headerElements){
            //logger.info(headerElement.getText());
            headerList.add(headerElement.getText());
        }
        Thread.sleep(3000);
        List<WebElement> rowElements = tableElement.findElements(By.xpath("./tbody/tr"));
        for(WebElement rowElement : rowElements){
            HashMap<String,String> rowData = new HashMap();
            List<WebElement> colElements = rowElement.findElements(By.xpath("./td"));
            /*for(int i = 0; i<colElements.size();i++){
                rowData.put(headerList.get(i),colElements.get(i).getText());*/
            if(null!=colElements && !colElements.isEmpty()) {
                logger.info("size of td " + colElements.size());
                for (int i = 0; i < colElements.size(); i++) {
                    rowData.put(headerList.get(i), colElements.get(i).getText());

                }
                tableData.add(rowData);
            }
        }
        return tableData;
    }

    public void isTableDataPresentedInOrder(final String field, final int colNum, final DisplayOrderType orderType, final String beanFileName,
                                            final String beanPath) throws Exception {
        boolean tableDataPresentedInOrder = true;
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        List<WebElement> trElements = element.findElements(By.xpath(".//tbody/tr"));

        for (int rowCounter = 0; rowCounter < trElements.size() - 1; rowCounter++) {
            String currentRowCellItem = trElements.get(rowCounter).findElement(By.xpath("td[" + colNum + "]")).getText().trim();
            String nextRowCellItem = trElements.get(rowCounter + 1).findElement(By.xpath("td[" + colNum + "]")).getText().trim();

            logger.info("Comparing " + currentRowCellItem + " to " + nextRowCellItem);

            if (DisplayOrderType.asc.equals(orderType)) {
                if (currentRowCellItem.compareToIgnoreCase(nextRowCellItem) > 0) {
                    tableDataPresentedInOrder = false;
                    break;
                }
            } else if (DisplayOrderType.desc.equals(orderType)) {
                if (currentRowCellItem.compareToIgnoreCase(nextRowCellItem) < 0) {
                    tableDataPresentedInOrder = false;
                    break;
                }
            }
        }

        assertTrue(tableDataPresentedInOrder);
    }

    public void isTableDataPresentedInAltTextOrder(final String field, final int colNum, final DisplayOrderType orderType, final String beanFileName,
                                                   final String beanPath) throws Exception {
        boolean tableDataPresentedInAltTextOrder = true;
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(field, beanFileName, beanPath, getDriver(), true);

        List<WebElement> trElements = element.findElements(By.xpath(".//tbody/tr"));

        for (int rowCounter = 0; rowCounter < trElements.size() - 1; rowCounter++) {
            String currentRowCellItem = trElements.get(rowCounter).findElement(By.xpath("td[" + colNum + "]/img")).getAttribute("alt").trim();
            String nextRowCellItem = trElements.get(rowCounter + 1).findElement(By.xpath("td[" + colNum + "]/img")).getAttribute("alt").trim();

            logger.info("Comparing " + currentRowCellItem + " to " + nextRowCellItem);

            if (DisplayOrderType.asc.equals(orderType)) {
                if (currentRowCellItem.compareToIgnoreCase(nextRowCellItem) > 0) {
                    tableDataPresentedInAltTextOrder = false;
                    break;
                }
            } else if (DisplayOrderType.desc.equals(orderType)) {
                if (currentRowCellItem.compareToIgnoreCase(nextRowCellItem) < 0) {
                    tableDataPresentedInAltTextOrder = false;
                    break;
                }
            }
        }

        assertTrue(tableDataPresentedInAltTextOrder);
    }


    //get Table Size
    public Integer getTableSize(final String tableName, final String rowIdentifier, final String beanFileName,
                                final String beanPath) throws Exception {
        Integer size = 0;
        // Locate the passed element
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(tableName, beanFileName, beanPath, getDriver(), true);
        List<WebElement> rowElements = element.findElements(By.xpath(elementLocator.getElementPath(rowIdentifier, beanFileName, beanPath)));
        logger.info("Resulting rows: " + rowElements.size());

        size = rowElements.size();
        return size;
    }

    //Verify Table size with given rowIdentifier
    public void isTableSizeCorrect(final String tableName, final String rowIdentifier, final int expectedRowCount, final String beanFileName,
                                   final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(tableName, beanFileName, beanPath, getDriver(), true);

        List<WebElement> rowElements = element.findElements(By.xpath(elementLocator.getElementPath(rowIdentifier, beanFileName, beanPath)));

        logger.info("Resulting rows: " + rowElements.size());
        logger.info("Expected rows: " + expectedRowCount);

        assertTrue(rowElements.size() == expectedRowCount);
    }

    //Verify Element of a particular cell with given row and column number
    public void assertTableCellElement(final String table,final int rowNumber,final int colNumber, final String expectedElement, final String beanFileName,
                                       final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(table, beanFileName, beanPath, getDriver(), true);

        WebElement CellElement = element.findElement(By.xpath(".//tr["+rowNumber+"]/td["+colNumber+"]"));
        logger.info("Cell Element - " + CellElement.getText());
        logger.info("Expected Element - "+ expectedElement);

        assertTrue(CellElement.getText().toLowerCase().contains(expectedElement.toLowerCase()));
    }

    //Verify all elements of a column with given rowIdentifier
    public void assertTableColumnElement(final String tableName,final String rowIdentifier, final int colNumber, final String expectedElement, final String beanFileName,
                                         final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(tableName, beanFileName, beanPath, getDriver(), true);

        List<WebElement> rowElements = element.findElements(By.xpath(elementLocator.getElementPath(rowIdentifier, beanFileName, beanPath)));

        logger.info("Resulting rows: " + rowElements.size());

        for (int rowNumber=1; rowNumber<= rowElements.size(); rowNumber++) {
            WebElement CellElement = element.findElement(By.xpath(".//tr[" + rowNumber + "]/td[" + colNumber + "]"));
            logger.info("Cell Element - " + CellElement.getText());
            logger.info("Expected Element - " + expectedElement);

            assertTrue(CellElement.getText().toLowerCase().contains(expectedElement.toLowerCase()));
        }
    }

    // This method will verify multiple column in a table
    public void assertTextInMultipleColumnsOfTable(final String tableName,final String rowIdentifier, final String columnNumbers, final String expectedElement, final String beanFileName,
                                                   final String beanPath) throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(tableName, beanFileName, beanPath, getDriver(), true);

        List<WebElement> rowElements = element.findElements(By.xpath(elementLocator.getElementPath(rowIdentifier, beanFileName, beanPath)));

        String[] columns = columnNumbers.split("_####_");

        logger.info("Resulting rows: " + rowElements.size());

        for (int rowNumber=1; rowNumber<= rowElements.size(); rowNumber++) {
            List<String> CellElements = new ArrayList<String>();

            for (int colNumber = 0; colNumber<columns.length;colNumber++) {
                CellElements.add(element.findElement(By.xpath(".//tr[" + rowNumber + "]/td[" + columns[colNumber] + "]")).getText());
            }
            logger.info("Elements from all Cells " + CellElements.toString());
            logger.info("Expected Element " + expectedElement);
            assertTrue(CellElements.toString().toLowerCase().contains(expectedElement.toLowerCase()));
        }
    }


    //This method will verify a date between two given dates
    public void assertDateInRange(final String tableName,final String rowIdentifier, final int colNumber,final String dateFrom,final String dateTo, final String beanFileName,
                                  final String beanPath) throws Exception {
        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(tableName, beanFileName, beanPath, getDriver(), true);

        List<WebElement> rowElements = element.findElements(By.xpath(elementLocator.getElementPath(rowIdentifier, beanFileName, beanPath)));

        logger.info("Resulting rows: " + rowElements.size());

        for (int rowNumber=1; rowNumber<= rowElements.size(); rowNumber++) {

            WebElement CellElement = element.findElement(By.xpath(".//tr[" + rowNumber + "]/td[" + colNumber + "]"));

            String orderDate = CellElement.getText();



            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

            Date order_Date = formatter.parse(orderDate);
            Date date_From = formatter.parse(dateFrom);
            Date date_To = formatter.parse(dateTo);
            assertTrue((order_Date.compareTo(date_From)>=0) && (order_Date.compareTo(date_To)<=0));

        }
    }

    // This method will verify a value between range of values
    public void assertRangeInBetween(final String tableName,final double minvalue,final double maxvalue,final String rowIdentifier, final int colNumber,final String beanFileName, final String beanPath) throws Exception {

        WebElementLocator elementLocator = WebElementLocator.getInstance();
        WebElement element = elementLocator.locateElement(tableName, beanFileName, beanPath, getDriver(), true);

        List<WebElement> rowElements = element.findElements(By.xpath(elementLocator.getElementPath(rowIdentifier, beanFileName, beanPath)));

        logger.info("Resulting rows: " + rowElements.size());

        for (int rowNumber=1; rowNumber<= rowElements.size(); rowNumber++) {
            WebElement CellElement = element.findElement(By.xpath(".//tr[" + rowNumber + "]/td[" + colNumber + "]"));

            //Remove $ from the price string
            String value = CellElement.getText().replaceAll("(?<=\\d),(?=\\d)|\\$", "");

            double actualValue = Double.parseDouble(value);

            logger.info("values in column are: " + actualValue);
            assertTrue(actualValue>=minvalue && actualValue<=maxvalue);
        }
    }

}
