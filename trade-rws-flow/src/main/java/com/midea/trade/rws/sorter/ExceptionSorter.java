package com.midea.trade.rws.sorter;
import java.sql.SQLException;

/**
 * The ExceptionSorter interface allows for <code>java.sql.SQLException</code>
 * evaluation to determine if an error is fatal. 
 */

public interface ExceptionSorter
{


   /**
    * Evaluates a <code>java.sql.SQLException</code> to determine if
    * the error was fatal
    * 
    * @param e the <code>java.sql.SQLException</code>
    * 
    * @return whether or not the exception is vatal.
    */
   boolean isExceptionFatal(SQLException e);
}
