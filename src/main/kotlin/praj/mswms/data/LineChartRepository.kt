/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import praj.mswms.data.access.LineChartDAO
import praj.mswms.util.AmountPerDate

/**
 * Repository for amount-vs-date data.
 */
class LineChartRepository {
    val listData: ObservableList<AmountPerDate> = FXCollections.observableArrayList()
    val dao = LineChartDAO()

    fun load() = listData.addAll(dao.getAll())

    fun refresh() {
        listData.clear()
        load()
    }
}