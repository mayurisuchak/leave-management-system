/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import data.DataAccess;

/**
 *
 * @author uSeR
 */
public class RequestService {

    public RequestService() {
    }

    public boolean checkSuperior(int userID){
        DataAccess da = DataAccess.getInstance();
        return da.checkSuperior(userID);
    }
}
