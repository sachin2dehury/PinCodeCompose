package github.sachin2dehury.pincodecompose

class PinRepository(private val service: PinService) {

    suspend fun getPinData(pin: String) = service.getPinData(pin)
}
