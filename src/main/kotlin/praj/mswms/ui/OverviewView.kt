/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.collections.FXCollections
import javafx.scene.chart.BarChart
import javafx.scene.chart.LineChart
import javafx.scene.chart.XYChart
import javafx.scene.layout.AnchorPane
import praj.mswms.service.RepositoryService
import tornadofx.View
import java.math.BigDecimal

/**
 * UI for Overview.
 */
class OverviewView : View("Overview") {
    override val root: AnchorPane by fxml("/fxml/overview-view.fxml")

    private val collectionPerDate: LineChart<String, BigDecimal>     by fxid()
    private val collectionPerLocation: BarChart<String, BigDecimal>  by fxid()

    override fun onDock() {
        collectionPerDate.data = FXCollections.observableArrayList(
                XYChart.Series<String, BigDecimal>().apply {
                    name = "2019"
                    data = RepositoryService.lineChartRepository.listData
                }
        )
        collectionPerLocation.data = FXCollections.observableArrayList(
                XYChart.Series<String, BigDecimal>().apply {
                    name = "2019"
                    data = RepositoryService.barChartRepository.listData
                }
        )
    }
}