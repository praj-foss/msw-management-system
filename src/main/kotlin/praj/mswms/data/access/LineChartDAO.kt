/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.access

import praj.mswms.service.DatabaseService
import praj.mswms.util.AmountPerDate
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.format.DateTimeFormatter

/**
 * DAO for amount-vs-date data.
 */


class LineChartDAO {
    private val psGetAll: PreparedStatement
    private val dateFormatter = DateTimeFormatter.ofPattern("MMM dd")

    init {
        val connection = DatabaseService.connection

        psGetAll = connection.prepareStatement(
                "SELECT CAST(CollectionTime AS DATE) AS Date, SUM(Amount) " +
                "FROM Collection " +
                "GROUP BY Date"
        )
    }

    fun getAll(): List<AmountPerDate> {
        val locationList = ArrayList<AmountPerDate>()
        val rs = psGetAll.executeQuery()
        while (rs.next()) {
            locationList.add(newData(rs))
        }

        return locationList
    }

    private fun newData(rs: ResultSet) = AmountPerDate().apply {
        xValue = rs.getDate(1).toLocalDate().format(dateFormatter)
        yValue = rs.getBigDecimal(2)
    }
}