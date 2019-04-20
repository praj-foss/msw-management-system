/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.model

import tornadofx.getProperty
import tornadofx.property
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Class for Collection model.
 */
class Collection(
        id: Int, time: LocalDateTime, location: Location, vehicleId: Int, amount: BigDecimal
) : Model {
    override var id: Int by property(id)
    fun idProperty() = getProperty(Collection::id)

    var time: LocalDateTime by property(time)
    fun timeProperty() = getProperty(Collection::time)

    var location: Location by property(location)
    fun locationProperty() = getProperty(Collection::location)

    var vehicleId: Int by property(vehicleId)
    fun vehicleIdProperty() = getProperty(Collection::vehicleId)

    var amount: BigDecimal by property(amount)
    fun amountProperty() = getProperty(Collection::amount)
}