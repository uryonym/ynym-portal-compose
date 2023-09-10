package com.uryonym.ynymportal.data

import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.model.Confidential
import com.uryonym.ynymportal.data.model.Refueling
import com.uryonym.ynymportal.data.model.Task
import com.uryonym.ynymportal.data.model.TaskList
import com.uryonym.ynymportal.data.network.NetworkCar
import com.uryonym.ynymportal.data.network.NetworkConfidential
import com.uryonym.ynymportal.data.network.NetworkRefueling
import com.uryonym.ynymportal.data.network.NetworkTask
import com.uryonym.ynymportal.data.network.NetworkTaskList

fun TaskList.toNetwork() = NetworkTaskList(
    id = this.id,
    name = this.name,
    uid = this.uid,
    seq = this.seq,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listTaskListToNetwork")
fun List<TaskList>.toNetwork() = map {
    it.toNetwork()
}

fun NetworkTaskList.toLocal() = TaskList(
    id = this.id,
    name = this.name,
    uid = this.uid,
    seq = this.seq,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listTaskListToLocal")
fun List<NetworkTaskList>.toLocal() = map {
    it.toLocal()
}

fun Task.toNetwork() = NetworkTask(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    uid = this.uid,
    taskListId = this.taskListId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listTaskToNetwork")
fun List<Task>.toNetwork() = map {
    it.toNetwork()
}

fun NetworkTask.toLocal() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    deadLine = this.deadLine,
    isComplete = this.isComplete,
    uid = this.uid,
    taskListId = this.taskListId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listTaskToLocal")
fun List<NetworkTask>.toLocal() = map {
    it.toLocal()
}

fun Confidential.toNetwork() = NetworkConfidential(
    id = this.id,
    serviceName = this.serviceName,
    loginId = this.loginId,
    password = this.password,
    other = this.other,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listConfidentialToNetwork")
fun List<Confidential>.toNetwork() = map {
    it.toNetwork()
}

fun NetworkConfidential.toLocal() = Confidential(
    id = this.id,
    serviceName = this.serviceName,
    loginId = this.loginId,
    password = this.password,
    other = this.other,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listConfidentialToLocal")
fun List<NetworkConfidential>.toLocal() = map {
    it.toLocal()
}

fun Car.toNetwork() = NetworkCar(
    id = this.id,
    name = this.name,
    maker = this.maker,
    model = this.model,
    modelYear = this.modelYear,
    licensePlate = this.licensePlate,
    tankCapacity = this.tankCapacity,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listCarToNetwork")
fun List<Car>.toNetwork() = map {
    it.toNetwork()
}

fun NetworkCar.toLocal() = Car(
    id = this.id,
    name = this.name,
    maker = this.maker,
    model = this.model,
    modelYear = this.modelYear,
    licensePlate = this.licensePlate,
    tankCapacity = this.tankCapacity,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listCarToLocal")
fun List<NetworkCar>.toLocal() = map {
    it.toLocal()
}

fun Refueling.toNetwork() = NetworkRefueling(
    id = this.id,
    refuelDateTime = this.refuelDateTime,
    odometer = this.odometer,
    fuelType = this.fuelType,
    price = this.price,
    totalCost = this.totalCost,
    fullFlag = this.fullFlag,
    gasStand = this.gasStand,
    carId = this.carId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listRefuelingToNetwork")
fun List<Refueling>.toNetwork() = map {
    it.toNetwork()
}

fun NetworkRefueling.toLocal() = Refueling(
    id = this.id,
    refuelDateTime = this.refuelDateTime,
    odometer = this.odometer,
    fuelType = this.fuelType,
    price = this.price,
    totalCost = this.totalCost,
    fullFlag = this.fullFlag,
    gasStand = this.gasStand,
    carId = this.carId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

@JvmName("listRefuelingToLocal")
fun List<NetworkRefueling>.toLocal() = map {
    it.toLocal()
}
