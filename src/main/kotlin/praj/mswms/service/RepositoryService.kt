/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.service

import praj.mswms.data.*

/**
 * Service to provide access to repositories.
 */
object RepositoryService {
    val locationRepository = LocationRepository()
    val vehicleRepository  = VehicleRepository()
    val collectionRepository = CollectionRepository()

    val lineChartRepository = LineChartRepository()
    val barChartRepository = BarChartRepository()

    fun start() = println("Repository service running")
}