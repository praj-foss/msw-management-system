/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import praj.mswms.data.access.BarChartDAO
import praj.mswms.util.AmountPerLocation

/**
 * Purpose: Magical
 */
class BarChartRepository {
    val listData: ObservableList<AmountPerLocation> = FXCollections.observableArrayList()
    val dao = BarChartDAO()

    fun load() = listData.addAll(dao.getAll())

    fun refresh() {
        listData.clear()
        load()
    }
}