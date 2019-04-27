/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.data.model

import tornadofx.getProperty
import tornadofx.property

/**
 * Class for Vehicle model.
 */
class Vehicle(
        id: Int, model: String, capacity: Int, lastLocation: Location?, status: String?
) : Model {
    override var id: Int by property(id)
    fun idProperty() = getProperty(Vehicle::id)

    var model: String by property(model)
    fun modelProperty() = getProperty(Vehicle::model)

    var capacity: Int by property(capacity)
    fun capacityProperty() = getProperty(Vehicle::capacity)

    var lastLocation: Location? by property(lastLocation)
    fun lastLocationProperty() = getProperty(Vehicle::lastLocation)

    var status: String? by property(status)
    fun statusProperty() = getProperty(Vehicle::status)

    override fun toString(): String {
        return "Vehicle[$id, $model, $capacity, ${lastLocation?.name}, $status]"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Vehicle)
            id == other.id && model == other.model && capacity == other.capacity && lastLocation == other.lastLocation && status == other.status
        else
            false
    }
}