package com.udacity.project4
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource

class FakeDataSource(var reminders: MutableList<ReminderDTO> = mutableListOf()) : ReminderDataSource {

//    DONE: Create a fake data source to act as a double to the real data source

    private var shouldReturnError = false

    fun setShouldReturnError(shouldReturn: Boolean) {
        this.shouldReturnError = shouldReturn
    }

    override suspend fun getReminders(): Result<List<ReminderDTO>> =
            if (shouldReturnError) {
                Result.Error("Error occurred")
            } else {
                Result.Success(reminders)
            }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders += reminder
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> =
            if (shouldReturnError) {
                Result.Error("Error occurred")
            } else {
                val reminder = reminders.find { it.id == id }

                if (reminder == null) {
                    Result.Error("Not found")
                } else {
                    Result.Success(reminder)
                }
            }

    override suspend fun deleteAllReminders() {
        reminders.clear()
    }

}