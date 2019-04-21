/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.service

import praj.mswms.data.CollectionRepository
import praj.mswms.data.LineChartRepository
import praj.mswms.data.LocationRepository
import praj.mswms.data.VehicleRepository

/**
 * Service to provide access to repositories.
 */
object RepositoryService {
    val lineChartRepository = LineChartRepository()
    val locationRepository = LocationRepository()
    val vehicleRepository  = VehicleRepository()
    val collectionRepository = CollectionRepository()

    fun start() = println("Repository service running")
}