/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.access

import praj.mswms.service.DatabaseService
import praj.mswms.util.AbountPerLocation
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * DAO for Location-wise waste collection data.
 */
class BarChartDAO {
    private val psGetAll: PreparedStatement

    init {
        val connection = DatabaseService.connection

        psGetAll = connection.prepareStatement(
                "SELECT T.TypeName AS Type, SUM(C.Amount) " +
                "FROM Collection C, Location L, LocationType T " +
                "WHERE C.LocationID = L.LocationID AND L.TypeID < 6 AND L.TypeID = T.TypeID " +
                "GROUP BY Type"
        )
    }

    fun getAll(): List<AbountPerLocation> {
        val locationList = ArrayList<AbountPerLocation>()
        val rs = psGetAll.executeQuery()
        while (rs.next()) {
            locationList.add(newData(rs))
        }

        return locationList
    }

    private fun newData(rs: ResultSet) = AbountPerLocation().apply {
        xValue = rs.getString(1)
        yValue = rs.getBigDecimal(2)
    }
}