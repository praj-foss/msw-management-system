/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.ui

import javafx.collections.FXCollections
import javafx.scene.SnapshotParameters
import javafx.scene.chart.BarChart
import javafx.scene.chart.LineChart
import javafx.scene.chart.XYChart
import javafx.scene.image.WritableImage
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.scene.transform.Transform
import praj.mswms.service.RepositoryService
import tornadofx.View
import java.math.BigDecimal

/**
 * UI for Overview.
 */
class OverviewView : View("Overview") {
    override val root: AnchorPane by fxml("/fxml/overview-view.fxml")

    private val contentBox: VBox                                     by fxid()
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

    fun getSnapshot(): WritableImage {
        // TODO: Remove hardcoded values
        val scale = 4.0
        val image = WritableImage(
                Math.round(contentBox.layoutBounds.width  * scale).toInt(),
                Math.round(contentBox.layoutBounds.height * scale).toInt()
        )
//        println(Math.round(contentBox.layoutBounds.width * SCALE).toInt())
//        val BoxWidth = 520F
//        val t = BoxWidth / contentBox.width
//        println(contentBox.width)

        //return contentBox.snapshot(SnapshotParameters(), null)
        return contentBox.snapshot(SnapshotParameters().apply { transform = Transform.scale(scale, scale) }, image)
    }
}