/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms

import praj.mswms.service.DatabaseService
import praj.mswms.service.RepositoryService
import praj.mswms.ui.MainView
import tornadofx.App

/**
 * Main application class.
 */

class MainApp : App(MainView::class) {
    init {
        // Load the services
        RepositoryService.start()
        DatabaseService.start()

        // Do not change this sequence
        RepositoryService.locationRepository.load()
        RepositoryService.vehicleRepository.load()
        RepositoryService.collectionRepository.load()
        RepositoryService.lineChartRepository.load()
        // TODO: Create triggers for graphs
    }

    override fun stop() {
        DatabaseService.stop()
        println("Closed DB connection")
    }
}