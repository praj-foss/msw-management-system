/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.access

import praj.mswms.service.DatabaseService
import praj.mswms.data.model.Location
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * DAO for Location objects.
 */
class LocationDAO : DAO<Location> {
    private val psGetById: PreparedStatement
    private val psGetAll: PreparedStatement

    init {
        val connection = DatabaseService.connection

        psGetById = connection.prepareStatement(
                "SELECT L.LocationID, L.Name, T.TypeName " +
                "FROM Location L, LocationType T " +
                "WHERE L.TypeID = T.TypeID AND L.LocationID = ?"
        )

        psGetAll = connection.prepareStatement(
                "SELECT L.LocationID, L.Name, T.TypeName " +
                "FROM Location L, LocationType T " +
                "WHERE L.TypeID = T.TypeID"
        )
    }

    override fun getById(id: Int): Location? {
        psGetById.clearParameters()
        psGetById.setInt(1, id)

        val rs = psGetById.executeQuery()
        while (rs.next())
            return newLocation(rs)

        return null
    }

    override fun getAll(): List<Location> {
        val locationList = ArrayList<Location>()
        val rs = psGetAll.executeQuery()
        while (rs.next())
            locationList.add(newLocation(rs))

        return locationList
    }

    private fun newLocation(rs: ResultSet) = Location(
            id   = rs.getInt(1),
            name = rs.getString(2),
            type = rs.getString(3)
    )
}