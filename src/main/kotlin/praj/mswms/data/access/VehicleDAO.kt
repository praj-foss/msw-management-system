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
    private val psGetModel: PreparedStatement
    private val psGetStatus: PreparedStatement
    private val psInsert: PreparedStatement
    private val psUpdate: PreparedStatement
    private val psDelete: PreparedStatement

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

        psGetModel = connection.prepareStatement(
                "SELECT ModelID " +
                "FROM VehicleModel " +
                "WHERE ModelName = ?"
        )

        psGetStatus = connection.prepareStatement(
                "SELECT StatusID " +
                "FROM VehicleStatus " +
                "WHERE StatusValue = ?"
        )

        psInsert = connection.prepareStatement(
                "INSERT INTO Vehicle " +
                "VALUES (?, ?, ?, ?)"
        )

        psUpdate = connection.prepareStatement(
                "UPDATE Vehicle " +
                "SET VehicleID = ?, ModelID = ?, LocationID = ?, StatusID = ? " +
                "WHERE VehicleID = ?"
        )

        psDelete = connection.prepareStatement(
                "DELETE FROM Vehicle " +
                "WHERE LocationID = ?"
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

    override fun insert(element: Vehicle) {
        psInsert.apply {
            clearParameters()
            setInt(1, element.id)
            setInt(2, element.getModelId()!!)
            setInt(3, element.lastLocation.id)
            setInt(4, element.getStatusId()!!)
            executeUpdate()
        }
    }

    override fun update(id: Int, element: Vehicle) {
        psUpdate.apply {
            clearParameters()
            setInt(1, element.id)
            setInt(2, element.getModelId()!!)
            setInt(3, element.lastLocation.id)
            setInt(4, element.getStatusId()!!)
            setInt(5, id)
            executeUpdate()
        }
    }

    override fun delete(id: Int) {
        psDelete.apply {
            clearParameters()
            setInt(1, id)
            executeUpdate()
        }
    }

    private fun Vehicle.getModelId(): Int? = psGetModel.run {
        clearParameters()
        setString(1, model)
        executeQuery().let { if (it.next()) it.getInt(1) else null }
    }

    private fun Vehicle.getStatusId(): Int? = psGetStatus.run {
        clearParameters()
        setString(1, status)
        executeQuery().let { if (it.next()) it.getInt(1) else null }
    }

    private fun newVehicle(rs: ResultSet) = Vehicle(
            id           = rs.getInt(1),
            model        = rs.getString(2),
            capacity     = rs.getInt(3),
            lastLocation = RepositoryService.locationRepository.get(rs.getInt(4)) ?: Location.UNAVAILABLE,
            status       = rs.getString(5) ?: Vehicle.UNAVAILABLE.status
    )
}