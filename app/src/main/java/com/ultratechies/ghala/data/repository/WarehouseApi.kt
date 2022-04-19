package com.ultratechies.ghala.data.repository

import com.ultratechies.ghala.data.models.responses.WarehousesResponse
import retrofit2.http.HTTP

interface WarehouseApi {

    @HTTP(method = "GET", path = "api.json", hasBody = false)
    suspend fun getWarehouses(
    ) : WarehousesResponse
}