package com.example.lincridetechnicalassessment.data.repository

import app.cash.turbine.test
import com.example.lincridetechnicalassessment.data.local.dao.RideDao
import com.example.lincridetechnicalassessment.data.local.entity.RideEntity
import com.example.lincridetechnicalassessment.util.FareConfig
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RideRepositoryImplTest {

    private lateinit var rideDao: RideDao
    private lateinit var repository: RideRepositoryImpl

    @Before
    fun setUp() {
        rideDao = mockk(relaxed = true)
        repository = RideRepositoryImpl(rideDao)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `estimateFare returns valid result for 5km, normal demand`() = runTest {
        val pickup = "Origin"
        val destination = "Destination"
        val distance = 5.0
        val trafficLevel = 1 //low traffic
        val hour = 13 //non peak hour

        val expectedBaseFare = FareConfig.BASE_FARE
        val expectedDistanceFare = FareConfig.PER_KM_RATE * distance
        val expectedDemandMultiplier = FareConfig.getDemandMultiplier(hour)
        val expectedTrafficMultiplier = FareConfig.getTrafficMultiplier(trafficLevel)
        val expectedTotalFare = (expectedBaseFare + expectedDistanceFare) * expectedDemandMultiplier * expectedTrafficMultiplier

        val ride = repository.estimateFare(
            pickupAddress = pickup,
            destinationAddress = destination,
            distance = distance,
            trafficLevel = trafficLevel,
            currentHour = hour
        )

        println("expectedTotalFare: $expectedTotalFare, actualTotalFare: ${ride.totalFare}")
        assertEquals(expectedTotalFare, ride.totalFare)
    }

    @Test
    fun `estimateFare returns valid result for 8km, peak hour`() = runTest {
        val pickup = "Origin"
        val destination = "Destination"
        val distance = 8.0
        val trafficLevel = 1 //low traffic
        val hour = 18 //peak hour - Evening Rush

        val expectedBaseFare = FareConfig.BASE_FARE
        val expectedDistanceFare = FareConfig.PER_KM_RATE * distance
        val expectedDemandMultiplier = FareConfig.getDemandMultiplier(hour)
        val expectedTrafficMultiplier = FareConfig.getTrafficMultiplier(trafficLevel)
        val expectedTotalFare = (expectedBaseFare + expectedDistanceFare) * expectedDemandMultiplier * expectedTrafficMultiplier

        val ride = repository.estimateFare(
            pickupAddress = pickup,
            destinationAddress = destination,
            distance = distance,
            trafficLevel = trafficLevel,
            currentHour = hour
        )

        println("expectedTotalFare: $expectedTotalFare, actualTotalFare: ${ride.totalFare}")
        assertEquals(expectedTotalFare, ride.totalFare)
    }

    @Test
    fun `estimateFare returns valid result for 6km, heavy traffic`() = runTest {
        val pickup = "Origin"
        val destination = "Destination"
        val distance = 6.0
        val trafficLevel = 5 //heavy traffic
        val hour = 13 //non peak hour

        val expectedBaseFare = FareConfig.BASE_FARE
        val expectedDistanceFare = FareConfig.PER_KM_RATE * distance
        val expectedDemandMultiplier = FareConfig.getDemandMultiplier(hour)
        val expectedTrafficMultiplier = FareConfig.getTrafficMultiplier(trafficLevel)
        val expectedTotalFare = (expectedBaseFare + expectedDistanceFare) * expectedDemandMultiplier * expectedTrafficMultiplier

        val ride = repository.estimateFare(
            pickupAddress = pickup,
            destinationAddress = destination,
            distance = distance,
            trafficLevel = trafficLevel,
            currentHour = hour
        )

        println("expectedTotalFare: $expectedTotalFare, actualTotalFare: ${ride.totalFare}")
        assertEquals(expectedTotalFare, ride.totalFare)
    }

    @Test
    fun `requestRide returns same RideEntity`() = runTest {
        val ride = RideEntity(
            pickupLocation = "From",
            destinationLocation = "To",
            baseFare = 400.0,
            distanceFare = 600.0,
            demandMultiplier = 1.2,
            trafficMultiplier = 1.1,
            totalFare = 1056.0,
            driverName = "John Doe",
            car = "Toyota Prius",
            plateNumber = "XYZ-1234",
            estimatedArrival = "4 min",
            timestamp = System.currentTimeMillis()
        )

        val result = repository.requestRide(ride)

        assertEquals(ride, result)
    }

    @Test
    fun `saveRide calls dao insertRide`() = runTest {
        val ride = RideEntity(
            pickupLocation = "Start",
            destinationLocation = "End",
            baseFare = 500.0,
            distanceFare = 900.0,
            demandMultiplier = 1.3,
            trafficMultiplier = 1.2,
            totalFare = 2184.0,
            driverName = "Driver Z",
            car = "Car X",
            plateNumber = "XYZ-000",
            estimatedArrival = "6 min",
            timestamp = System.currentTimeMillis()
        )

        repository.saveRide(ride)

        coVerify(exactly = 1) { rideDao.insertRide(ride) }
    }

    @Test
    fun `getRideHistory emits correct flow`() = runTest {
        val rideHistory = listOf(
            RideEntity(
                pickupLocation = "Loc A",
                destinationLocation = "Loc B",
                baseFare = 300.0,
                distanceFare = 600.0,
                demandMultiplier = 1.0,
                trafficMultiplier = 1.0,
                totalFare = 900.0,
                driverName = "Driver A",
                car = "Car A",
                plateNumber = "ABC-123",
                estimatedArrival = "5 min",
                timestamp = System.currentTimeMillis()
            )
        )

        every { rideDao.getAllRides() } returns flowOf(rideHistory)

        repository.getRideHistory().test {
            val emission = awaitItem()
            assertEquals(rideHistory, emission)
            assertTrue(emission.isNotEmpty())
            cancelAndIgnoreRemainingEvents()
        }

        verify(exactly = 1) { rideDao.getAllRides() }
    }
}