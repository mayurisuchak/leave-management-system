/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import data.DataAccess;
import data.DataObject;

/**
 *
 * @author uSeR
 */
class ReportService {
    DataAccess da;
    public ReportService() {
        da = DataAccess.getInstance();
    }

    /**
     * view report about subordinate according to year
     */
    public DataObject viewReport(int userID, int year){
        return new DataObject(da.viewSubordinateReport(userID, year));
    }
}
