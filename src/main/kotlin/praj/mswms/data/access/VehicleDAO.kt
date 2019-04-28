/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.access

import praj.mswms.data.model.Location
import praj.mswms.service.DatabaseService
import praj.mswms.data.model.Vehicle
import praj.mswms.service.RepositoryService
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * DAO for Vehicle objects.
 */
class VehicleDAO : DAO<Vehicle> {
    private val psGetById: PreparedStatement
    private val psGetAll: PreparedStatement

    init {
        val connection = DatabaseService.connection

        psGetById = connection.prepareStatement(
                "SELECT V.VehicleID, M.ModelName, M.Capacity, V.LocationID, S.StatusValue " +
                "FROM Vehicle V, VehicleModel M, VehicleStatus S " +
                "WHERE V.ModelID = M.ModelID AND V.StatusID = S.StatusID AND V.VehicleID = ?"
        )

        psGetAll = connection.prepareStatement(
                "SELECT V.VehicleID, M.ModelName, M.Capacity, V.LocationID, S.StatusValue " +
                "FROM Vehicle V, VehicleModel M, VehicleStatus S " +
                "WHERE V.ModelID = M.ModelID AND V.StatusID = S.StatusID"
        )
    }

    override fun getById(id: Int): Vehicle? {
        psGetById.clearParameters()
        psGetById.setInt(1, id)

        val rs = psGetById.executeQuery()
        while (rs.next())
            return newVehicle(rs)

        return null
    }

    override fun getAll(): List<Vehicle> {
        val vehicleList = ArrayList<Vehicle>()
        val rs = psGetAll.executeQuery()
        while (rs.next())
            vehicleList.add(newVehicle(rs))

        return vehicleList
    }

    private fun newVehicle(rs: ResultSet) = Vehicle(
            id           = rs.getInt(1),
            model        = rs.getString(2),
            capacity     = rs.getInt(3),
            lastLocation = RepositoryService.locationRepository.get(rs.getInt(4)) ?: Location.UNAVAILABLE,
            status       = rs.getString(5) ?: Vehicle.UNAVAILABLE.status
    )
}