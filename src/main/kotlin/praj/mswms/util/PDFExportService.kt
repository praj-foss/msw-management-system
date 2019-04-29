/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.util

import javafx.concurrent.Service
import javafx.scene.image.WritableImage
import java.io.File

/**
 * JavaFX Service to generate PDF reports.
 */
class PDFExportService : Service<Boolean>() {
    private var firstRun = true

    private lateinit var path: File
    private lateinit var charts: WritableImage

    fun generate(path: File, charts: WritableImage) {
        this.path = path
        this.charts = charts

        if (firstRun) {
            firstRun = false
            start()
        } else
            restart()
    }

    override fun createTask() = PDFGenerateTask(path, charts)
}