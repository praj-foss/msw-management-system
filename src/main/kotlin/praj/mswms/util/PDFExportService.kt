/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.util

import javafx.concurrent.Service
import java.io.File

/**
 * JavaFX Service to generate PDF reports.
 */
class PDFExportService : Service<Boolean>() {
    private var firstRun = true

    private var path = File("/tmp/Report.pdf")

    fun generate(path: File) {
        this.path = path
        if (firstRun) {
            firstRun = false
            start()
        } else
            restart()
    }

    override fun createTask() = PDFGenerateTask(path)
}